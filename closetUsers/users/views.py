from django.shortcuts import render
from django.http import JsonResponse, HttpResponse
from rest_framework import status
from .models import User, Clothes, Match, Collection
from django.views.decorators.csrf import csrf_exempt
import json
from django.contrib.auth import login, authenticate, logout
import re
import random
from .serializers import UserInfoSerializer, ClothesSerializer, UserSerializer
from rest_framework import generics
from django_filters.rest_framework import DjangoFilterBackend
from .permissions import IsOwnerOrReadOnly
from rest_framework import permissions


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


# 返回用户的个人信息总列表或带有查询参数的列表
# user list shows the list of all user or the user in the search field
class UserInfoList(generics.ListAPIView):
    queryset = User.objects.all()
    serializer_class = UserInfoSerializer
    filter_backends = (DjangoFilterBackend, )
    filter_fields = ('username', 'phone', 'email')


# 返回具体某个用户的个人信息
# UserDetail returns the information of user which id matches request para id
class UserInfoDetail(generics.RetrieveAPIView):
    queryset = User.objects.all()
    serializer_class = UserInfoSerializer


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


