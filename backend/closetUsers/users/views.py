from django.shortcuts import render
from django.http import JsonResponse, HttpResponse
from rest_framework import status
from .models import User, Clothes, Match, Collection
from django.views.decorators.csrf import csrf_exempt
from django.core import  serializers
import json
from django.contrib.auth import login, authenticate, logout
import re
import random
from .serializers import UserInfoSerializer, ClothesSerializer, UserSerializer, CollectionSerializer
# from .serializers import SignUpUserInfoSerializer
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
from rest_framework.views import APIView
from rest_framework.response import Response


# # 一个尝试
# class SignUpTest(generics.CreateAPIView):
#     queryset = User.objects.all()
#     serializer_class = SignUpUserInfoSerializer
#     # filter_backends = (DjangoFilterBackend,)
#     # # enable filter para list
#     # fields = ('url', 'username', 'id', 'email', 'phone', 'style', 'profile', 'password')
#
#     def perform_create(self, serializer):
#         data = serializer.data
#         try:
#             user = User.objects.create_user(username=data['username'], password=data['password'],
#                                             email=data['email'], phone=data['phone'],
#                                             style=data['style'], profile=data['profile'])
#         except ValueError:
#             raise ValueError("无效数据")
#         # serializer.save(user=self.request.user)


class SignUp(APIView):


    def post(self, request, format=None):
        post_body = request.body.decode('utf-8')
        try:
            data = json.loads(post_body)
        except ValueError:
            return Response({"msg": "post格式错误，请发送json格式参数"}, status=status.HTTP_400_BAD_REQUEST)
        data = SignUpData(data)
        if data.is_valid():
            user = User.objects.create_user(username=data['username'], password=data['password'],
                                            email=data['email'],phone=data['phone'],
                                            style=data['style'], profile=data['profile'])
            return Response({"results": data}, status=status.HTTP_200_OK)
        else:
            msg = data.msg
            return Response({"msg": msg}, status=status.HTTP_400_BAD_REQUEST)


class SignUpData(object):

    def __init__(self, data):
        self.data = data
        self.msg = 'ok'

    def is_valid(self):
        data = self.data
        if 'username' not in data or 'password' not in data or 'phone' not in data or 'email' not in data \
                or 'profile' not in data or 'style' not in data:
            return False
        for value in data:
            msg = is_valid_attr(value, data[value])
            if msg != "ok":
                return False
        return True


def is_valid_attr(attr_name, value):
    if attr_name == 'username':
        return is_valid_user(value)
    elif attr_name == 'password':
        return is_valid_password(value)
    elif attr_name == 'phone':
        return is_valid_phone(value)
    elif attr_name == 'email':
        return is_valid_email(value)
    else:
        return "ok"




def is_valid_user(username):
    # 我们约定用户名的条件为：3-16个字符，只能包含字母、数字和下划线，且不能为纯数字
    msg = "ok"
    name_pat = re.compile(r'^[a-zA-Z0-9_]{3,16}$')
    pure_num_pat = re.compile(r'^[0-9]{3,16}$')
    if re.match(pure_num_pat, username):
        msg = "用户名不可为纯数字"
    elif len(username) < 3:
        msg = "用户名需超过3个字符"
    elif len(username) > 16:
        msg = "用户名不应超过16个字符"
    elif not re.match(name_pat, username):
        msg = "用户名包含非法字符"
    return msg


def is_valid_password(password):
    msg = "ok"
    return msg





# Create your views here.
# to do: 这个前缀具体发布的时候记得去掉
# 用户注册
@csrf_exempt
def sign_up(request):

    if request.method == 'POST':
        post_body = request.body.decode('utf-8')
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
            if email:
                email_pat = re.compile(r'^[0-9a-zA-Z_]{0,19}@[0-9a-zA-Z]{1,13}\.[com,cn,net]{1,3}$')
                if not re.match(email_pat, email):
                    return JsonResponse(data={"msg": "请输入正确的邮箱名"}, status=status.HTTP_400_BAD_REQUEST)
            else:
                email = 'error'
        else:
            email = 'error'
        if 'phone' in data:
            phone = data['phone']
            if phone:
                # check if phone is valid
                phone_pat = re.compile(r'^(13\d|14[5|7]|15\d|166|17[3|6|7]|18\d)\d{8}$')
                if not re.match(phone_pat, phone):
                    return JsonResponse(data={"msg": "请输入正确的手机号"}, status=status.HTTP_400_BAD_REQUEST)
            else:
                phone = 'error'
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
        data['id'] = user.id
        return JsonResponse(data={"msg": "创建成功，登录？", "data": data}, status=status.HTTP_200_OK)
    else:
        return JsonResponse(data={"msg": "this method is not allowed"}, status=status.HTTP_405_METHOD_NOT_ALLOWED)


# 用户登录
@csrf_exempt
def sign_in(request):

    if request.method == 'POST':
        post_body = request.body.decode('utf-8')
        try:
            data = json.loads(post_body)
        except ValueError:
            return JsonResponse(data={"msg": "post格式错误"}, status=status.HTTP_400_BAD_REQUEST)
        username = data['username']
        password = data['password']
        # print(username)
        user = authenticate(username=username, password=password)
        if user:
            if user.is_active:
                login(request, user)
                # sys.stderr.write(request.user)
                return JsonResponse(data={"msg": "OK", "username": username, "id": user.id}, status=status.HTTP_200_OK)
            else:
                return JsonResponse(data={"msg": "用户不存在"}, status=status.HTTP_400_BAD_REQUEST)
        else:
            return JsonResponse(data={"msg": "用户或密码错误"}, status=status.HTTP_400_BAD_REQUEST)
    else:
        return JsonResponse(data={"msg": "this method is not allowed"}, status=status.HTTP_405_METHOD_NOT_ALLOWED)


# 用户登出
@csrf_exempt
def sign_out(request):

    if request.method == 'GET':
        logout(request)
        return JsonResponse(data={"msg": "登出成功"}, status=status.HTTP_200_OK)
    else:
        return JsonResponse(data={"msg": "this method is not allowed"}, status=status.HTTP_405_METHOD_NOT_ALLOWED)


# 用户注销
@csrf_exempt
def sign_off(request):

    if request.method == 'POST':
        post_body = request.body.decode('utf-8')
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



