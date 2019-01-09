from django.shortcuts import render
from django.http import JsonResponse, HttpResponse
from rest_framework import status
from .models import User, Clothes, Match, Collection
from django.views.decorators.csrf import csrf_exempt
from django.core import  serializers
from rest_framework import mixins
import json
from django.contrib.auth import login, authenticate, logout
import re
import random
from .serializers import UserInfoSerializer, ClothesSerializer, UserSerializer, CollectionSerializer, MatchSerializer
from rest_framework import generics
from django_filters.rest_framework import DjangoFilterBackend
from .permissions import IsOwnerOrReadOnly, SelfOrReadOnly
from rest_framework import permissions
from django.shortcuts import get_object_or_404
from .matches import *
from urllib import request
import requests
import cv2
import numpy
import pandas
from django.conf import settings
import os


# Create your views here.
# to do: 这个前缀具体发布的时候记得去掉
# 用户注册
@csrf_exempt
def sign_up(request):

    if request.method == 'POST':
        post_body = request.body
        data = json.loads(post_body)
        # 我们约定用户名的条件为：3-16个字符，只能包含字母、数字和下划线，且不能为纯数字
        if 'username' in data:
            username = data['username']
            name_pat = re.compile(r'^[a-zA-Z0-9_]{3,16}$')
            pure_num_pat = re.compile(r'^[0-9]{3,16}$')
            if re.match(pure_num_pat, username):
                return JsonResponse(data={"msg": "用户名不可为纯数字"}, status=status.HTTP_400_BAD_REQUEST)
            elif len(username) < 3:
                return JsonResponse(data={"msg": "用户名需超过3个字符"}, status=status.HTTP_400_BAD_REQUEST)
            elif len(username) > 16:
                return JsonResponse(data={"msg": "用户名需不超过16个字符"}, status=status.HTTP_400_BAD_REQUEST)
            elif not re.match(name_pat, username):
                return JsonResponse(data={"msg": "用户名包含非法字符"}, status=status.HTTP_400_BAD_REQUEST)
        else:
            username = 'user' + str(random.randint(1, 999999))
        password = data['password']
        if 'email' in data:
            email = data['email']
            # check if email is valid
            email_pat = re.compile(r'^[0-9a-zA-Z_]{0,19}@[0-9a-zA-Z]{1,13}\.[com,cn,net]{1,3}$')
            if not re.match(email_pat, email):
                return JsonResponse(data={"msg": "请输入正确的邮箱名"}, status=status.HTTP_400_BAD_REQUEST)
        else:
            email = 'error'
        if 'phone' in data:
            phone = data['phone']
            # check if phone is valid
            phone_pat = re.compile(r'^(13\d|14[5|7]|15\d|166|17[3|6|7]|18\d)\d{8}$')
            if not re.match(phone_pat, phone):
                return JsonResponse(data={"msg": "请输入正确的手机号"}, status=status.HTTP_400_BAD_REQUEST)
        else:
            phone = 'error'
        if 'profile' in data:
            profile = data['profile']
        else:
            profile = 'http://120.76.62.132:8080/photos/default.jpg'
        if 'style' in data:
            style = data['style']
        else:
            style = 'casual'
        # users = User.objects.filter(username=username)
        email_users = User.objects.filter(email=email)
        phone_users = User.objects.filter(phone=phone)
        name_users = User.objects.filter(username=username)
        if email_users:
            return JsonResponse(data={"msg": "该邮箱已注册过"}, status=status.HTTP_400_BAD_REQUEST)
        if phone_users:
            return JsonResponse(data={"msg": "该手机号已注册过"}, status=status.HTTP_400_BAD_REQUEST)
        if name_users:
            return JsonResponse(data={"msg": "该用户名已存在"}, status=status.HTTP_400_BAD_REQUEST)
        if email == 'error':
            email = ''
        if phone == 'error':
            phone = ''
        user = User.objects.create_user(username=username, password=password, email=email,
                                        phone=phone, style=style, profile=profile)
        return JsonResponse(data={"msg": "创建成功，登录？"}, status=status.HTTP_200_OK)


# 用户登录
@csrf_exempt
def sign_in(request):

    if request.method == 'POST':
        post_body = request.body
        data = json.loads(post_body)
        username = data['username']
        password = data['password']
        print(username)
        user = authenticate(username=username, password=password)
        if user:
            if user.is_active:
                login(request, user)
                print(request.user)
                return JsonResponse(data={"msg": "OK"}, status=status.HTTP_200_OK)
            else:
                return JsonResponse(data={"msg": "用户不存在"}, status=status.HTTP_400_BAD_REQUEST)
        else:
            return JsonResponse(data={"msg": "用户或密码错误"}, status=status.HTTP_400_BAD_REQUEST)


# 用户登出
@csrf_exempt
def sign_out(request):

    if request.method == 'GET':
        logout(request)
        return JsonResponse(data={"msg": "登出成功"}, status=status.HTTP_200_OK)


# 用户注销
@csrf_exempt
def sign_off(request):

    if request.method == 'POST':
        post_body = request.body
        data = json.loads(post_body)
        username = data['username']
        password = data['password']
        user = authenticate(username=username, password=password)
        if user:
            email_pat = re.compile(r'^[0-9a-zA-Z_]{0,19}@[0-9a-zA-Z]{1,13}\.[com,cn,net]{1,3}$')
            phone_pat = re.compile(r'^(13\d|14[5|7]|15\d|166|17[3|6|7]|18\d)\d{8}$')
            if re.match(email_pat, username):
                delete_result = User.objects.filter(email=username).delete()
            elif re.match(phone_pat, username):
                delete_result = User.objects.filter(phone=username).delete()
            else:
                delete_result = User.objects.filter(username=username).delete()
            if delete_result:
                return JsonResponse(data={"msg": "注销成功"}, status=status.HTTP_200_OK)
        return JsonResponse(data={"msg": "用户不存在"}, status=status.HTTP_400_BAD_REQUEST)


# 上传图片到静态文件的文件夹，返回图片的url
@csrf_exempt
def upload_img(request):

    if request.method == 'POST':
        img = request.FILES.get('file', None)
        if not img:
            return JsonResponse(data={"msg": "没有上传的图片文件"}, status=status.HTTP_400_BAD_REQUEST)
        # 验证用户权限
        user = User.objects.filter(username=request.user.username)
        if not user:
            return JsonResponse(data={"msg": "无权限访问该操作"}, status=status.HTTP_401_UNAUTHORIZED)
        # if 'name' not in img:
        #     return JsonResponse(data={"msg": "无名文件无法上传"}, status=status.HTTP_400_BAD_REQUEST)
        img_id = Clothes.objects.all().order_by('-id').first().id
        file_name = str(user.id) + '_' + str(img_id) + '.png'
        file_path = os.path.join(settings.IMG_ROOT, file_name)
        while os.path.exists(file_path):
            img_id = Clothes.objects.all().order_by('-id').first().id + 1
            file_name = str(user.id) + '_' + str(img_id) + '.png'
            file_path = os.path.join(settings.IMG_ROOT, file_name)
        dest_img_path = open(file_path, 'wb+')
        for chunk in img.chunks():
            dest_img_path.write(chunk)
        dest_img_path.close()
        file_url = 'http://' + settings.PHOTO_HOST + '/img/' + img.name
        return JsonResponse(data={"msg": "图片上传成功", "url": file_url}, status=status.HTTP_202_ACCEPTED)
    else:
        return JsonResponse(data={"msg": "this method is not allowed"}, status=status.HTTP_405_METHOD_NOT_ALLOWED)


# 返回用户的个人信息总列表或带有查询参数的列表
# user list shows the list of all user or the user in the search field
class UserInfoList(generics.ListAPIView):
    queryset = User.objects.all()
    serializer_class = UserInfoSerializer
    filter_backends = (DjangoFilterBackend, )
    filter_fields = ('username', 'phone', 'email')


# 返回具体某个用户的个人信息
# UserDetail returns the information of user which id matches request para id
class UserInfoDetail(generics.RetrieveUpdateAPIView):
    queryset = User.objects.all()
    serializer_class = UserInfoSerializer
    permission_classes = (permissions.IsAuthenticatedOrReadOnly, SelfOrReadOnly)

    # 修改用户信息，邮箱手机号昵称等唯一标示的用户信息不可通过这个方式更改
    def perform_update(self, serializer):
        user_id = self.request.user.id
        user = get_object_or_404(User, id=user_id)
        data = serializer.validated_data
        # 用户id、username、email、phone不允许通过该方法更改
        data['id'] = user.id
        data['username'] = user.username
        data['phone'] = user.phone
        data['email'] = user.email
        instance = self.get_object()
        serializer = self.get_serializer(instance, data=data)
        serializer.is_valid(raise_exception=True)
        serializer.save()


# 返回用户的相关信息，目前为用户的衣物列表【总】
# user list shows the list of all user or the user in the search field
class UserList(generics.ListAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer
    filter_backends = (DjangoFilterBackend, )
    filter_fields = ('username', 'id')


# 返回某个用户的相关信息，目前为衣物列表
# UserDetail returns the information of user which id matches request para id
class UserDetail(generics.RetrieveAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer


# 返回衣物的总的列表或创建某个衣物
# ClothesList returns a type or all clothes info of one user
class ClothesList(generics.ListCreateAPIView):
    queryset = Clothes.objects.all()
    serializer_class = ClothesSerializer
    filter_backends = (DjangoFilterBackend, )
    permission_classes = (permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly)
    # enable filter para list
    filter_fields = ('user', 'type', 'color', 'season', 'pattern')

    def perform_create(self, serializer):
        serializer.save(user=self.request.user)


# 返回、修改或删除某件衣物的具体信息
# get specific clothes info, identified by id
class ClothesDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Clothes.objects.all()
    serializer_class = ClothesSerializer
    permission_classes = (permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly)


# 返回收藏
# CollectionList returns a type or all clothes info of one user
class CollectionList(generics.ListCreateAPIView):
    queryset = Collection.objects.all()
    serializer_class = CollectionSerializer
    filter_backends = (DjangoFilterBackend, )
    permission_classes = (permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly)
    # enable filter para list
    filter_fields = ('user', 'match', 'snapshot')

    def perform_create(self, serializer):
        serializer.save(user=self.request.user)


# 返回、修改或删除收藏的具体信息
# get specific collection info, identified by id
class CollectionDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Collection.objects.all()
    serializer_class = CollectionSerializer
    permission_classes = (permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly)




 #返回搭配
# MatchList returns a type or all clothes info of one user
class MatchList(generics.ListCreateAPIView):
    queryset = Match.objects.all()
    serializer_class = MatchSerializer
    filter_backends = (DjangoFilterBackend,)
    permission_classes = (permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly)
    # enable filter para list
    filter_fields = ('id', 'user', 'clothes_list', 'like', 'occasion')

    def perform_create(self, serializer):
        serializer.save(user=self.request.user)

# 返回、修改或删除搭配的具体信息
# get specific Match info, identified by id
class MatchDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Match.objects.all()
    serializer_class = MatchSerializer
    permission_classes = (permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly)




