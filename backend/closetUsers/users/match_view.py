
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
from .serializers import UserInfoSerializer, ClothesSerializer, UserSerializer,CollectionSerializer
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
from users import matches


# 生成搭配
@csrf_exempt
def get_match(request):
    # 发出get请求认为是返回当前用户的已经生成好的搭配
    if request.method=='GET':
        # data=json.loads(request.body)
        username = request.GET.get('username', None)
        if username:
            # 获得对用用户的photo列表，转成url,match_photo是一个字典数组
            # filter.value[index]直接就是一个字典
            match_photo=Match.objects.filter(user=User.objects.get(username=username)).values('clothes_list')
            # 是一个对象数组,photo_urls[i]代表一组搭配的图片
            photo_urls = {}
            for i in range(len(match_photo)):
                photo_urls[i] = string_to_urls(username, match_photo[i]['clothes_list'])
            return JsonResponse(data={"msg": "OK", 'photo': photo_urls}, status=status.HTTP_200_OK)
        else:
            return JsonResponse(data={"msg": "生成搭配失败，参数有误"}, status=status.HTTP_400_BAD_REQUEST)

    # 发出post请求，认为是创建新的搭配
    elif request.method == 'POST':
        if not request.body:
            return JsonResponse(status=status.HTTP_400_BAD_REQUEST)
        data = json.loads(request.body)
        if 'username'in data and 'password'in data:
            username = data['username']
            password = data['password']
            user = authenticate(username=username, password=password)
            # 这里认为match的图片区域还是字符串的形式 不是url数组
            if user:
                # 验证成功
                match = create_match(data)
        else:
            # 未提供身份验证的参数
            return JsonResponse(data={"msg": "生成搭配失败，参数有误"}, status=status.HTTP_400_BAD_REQUEST)

        # 将match数据返回并且插入数据库
        if match:
            # photo是一个map,key为photo1,photo2,photo3
            photo_urls = string_to_urls(username, match.get('clothes_list'))

            # 还需要判断数据库中是否已有该条搭配
            if not photo_urls:
                return JsonResponse(data={"msg": "无有效搭配"}, status=status.HTTP_404_NOT_FOUND)
            # 已有搭配

            user = User.objects.get(username=username)
            ifMatch = Match.objects.filter(user=user, clothes_list=match['clothes_list'])
            if ifMatch:
                return JsonResponse(data={"msg": "OK", 'photo': photo_urls}, status=status.HTTP_200_OK)

            # 不存在搭配,插入

            Match.objects.create(user=user, clothes_list=match['clothes_list'], like=match['like'],
                                 occasion=match['occasion'])

            return JsonResponse(data={"msg": "OK", 'photo': photo_urls}, status=status.HTTP_200_OK)
        else:
            return JsonResponse(data={"msg": "生成搭配失败，参数有误"}, status=status.HTTP_400_BAD_REQUEST)
    else:
        return JsonResponse(data={"msg": "this method is not allowed"}, status=status.HTTP_405_METHOD_NOT_ALLOWED)


'''
默认情况下，生成搭配时默认都参考温度，场合可选
'''


def create_match(type):
    if 'temperature' not in type:
        return
    tem = type.get('temperature')  # 温度
    if tem > 23:
        season = 'summer'
    elif tem < 10:
        season = 'winter'
    else:
        season = 'spring'
    if 'occasion' in type:
        occa = type['occasion']
    else:
        occa = 'daily'
    user = User.objects.get(username=type['username'])
    # clothes_list = Clothes.objects.filter(user=user, season=season)  # 获取该用户的衣服列表
    # top_list = clothes_list
    top_list = Clothes.objects.filter(user=user, season=season, type='top')
    trousers_list = Clothes.objects.filter(user=user, season=season, type='trousers')
    shoes_list = Clothes.objects.filter(user=user, season=season, type='shoes')
    coat_list = Clothes.objects.filter(user=user)
    if season == 'summer':
        top_list = top_list.filter(subtype='T-shirt')
        trousers_list = trousers_list.filter(subtype='jeans')
        shoes_list = shoes_list.filter(subtype='casual')
        coat_list = coat_list.filter(user=-1)
    elif season == 'winter':
        top_list = top_list.filter(subtype='sweater')
        trousers_list = trousers_list.filter(subtype='jeans')
        shoes_list = shoes_list.filter(subtype='casual')
        coat_list = Clothes.objects.filter(user=user, season=season, type='coat', subtype='jeans')
    else:
        top_list = top_list.filter(subtype='shirt')
        trousers_list = trousers_list.filter(subtype='jeans')
        shoes_list = shoes_list.filter(subtype='casual')
        coat_list = coat_list.filter(user=-1)
    if top_list & trousers_list & shoes_list:
        if season == 'winter':
            if coat_list:
                coat_id = coat_list[0].id
                clothes_list = '%d,%d,%d,%d' % (top_list[0].id, trousers_list[0].id, shoes_list[0].id, coat_id)
            else:
                clothes_list = '-1'
        else:
            clothes_list = '%d,%d,%d' % (top_list[0].id, trousers_list[0].id, shoes_list[0].id)
    else:
        clothes_list = '-1'
    '''
    根据算法，来产生相应的match
    '''
    match = {'user': user, 'clothes_list': clothes_list, 'like': False, 'occasion': occa}
    return match


'''
具体算法待实现,返回的类型应该是match(map结构)
'''


def match_by_tem_and_occa(type):
    tem = type.get('temperature')  # 温度
    occa = type.get('occasion')  # 场合
    # userid=User.objects.get(username=type['username']).values('id')#获取对应用户名的id
    #clothes_list是一个query_set,遍历然后clothes_list[index]就是个对象可以取其中的属性
    user=User.objects.get(username=type['username'])
    clothes_list=Clothes.objects.filter(user=user)#获取该用户的衣服列表
    '''
    根据算法，来产生相应的match
    '''
    match = {'user': user, 'clothes_list': '1,2,3', 'like': False, 'occasion': 'daily'}
    return match


def match_by_tem(type):
    tem = type.get('temperature')  # 温度
    if tem > 23:
        season = 'summer'
    elif tem < 10:
        season = 'winter'
    else:
        season = 'spring'
    user = User.objects.get(username=type['username'])
    # clothes_list = Clothes.objects.filter(user=user, season=season)  # 获取该用户的衣服列表
    # top_list = clothes_list
    top_list = Clothes.objects.filter(user=user, season=season, type='top')
    trousers_list = Clothes.objects.filter(user=user, season=season, type='trousers')
    shoes_list = Clothes.objects.filter(user=user, season=season, type='shoes')
    coat_list = Clothes.objects.filter(user=-1)
    if season == 'summer':
        top_list = top_list.filter(subtype='T-shirt')
        trousers_list = trousers_list.filter(subtype='jeans')
        shoes_list = shoes_list.filter(subtype='casual')
        # trousers_list = Clothes.objects.filter(user=user, season=season, type='trousers', subtype='jeans')
        # shoes_list = Clothes.objects.filter(user=user, season=season, type='shoes', subtype='casual')
    elif season == 'winter':
        top_list = top_list.filter(subtype='sweater')
        trousers_list = trousers_list.filter(subtype='jeans')
        shoes_list = shoes_list.filter(subtype='casual')
        coat_list = Clothes.objects.filter(user=user, season=season, type='coat', subtype='jeans')

        # top_list = Clothes.objects.filter(user=user, season=season, type='top', subtype='sweater')
        # coat_list = Clothes.objects.filter(user=user, season=season, type='coat', subtype='jeans')
        # trousers_list = Clothes.objects.filter(user=user, setopason=season, type='trousers', subtype='jeans')
        # shoes_list = Clothes.objects.filter(user=user, season=season, type='shoes', subtype='casual')
    else:
        top_list = top_list.filter(subtype='shirt')
        trousers_list = trousers_list.filter(subtype='jeans')
        shoes_list = shoes_list.filter(subtype='casual')
        # top_list = Clothes.objects.filter(user=user, season=season, type='top')
    if top_list & trousers_list & shoes_list:
        if season == 'winter':
            if coat_list:
                coat_id = coat_list[0].id
                clothes_list = '%d,%d,%d,%d' % (top_list[0].id, trousers_list[0].id, shoes_list[0].id, coat_id)
            else:
                clothes_list = '-1'
        else:
            clothes_list = '%d,%d,%d' % (top_list[0].id, trousers_list[0].id, shoes_list[0].id)
    else:
        clothes_list = '-1'
    '''
    根据算法，来产生相应的match
    '''
    match = {'user': user, 'clothes_list': clothes_list, 'like': False, 'occasion': 'daily'}
    return match


'''
将clothes_list的字符串信息转化为对应照片的urls信息
'''


def string_to_urls(username, list_string):
    id_list=list_string.split(',')
    # filter之后取[index],有Objects就是对象，没有就是一个数组
    clothes = Clothes.objects.filter(user=User.objects.get(username=username))
    # clothes_photo = {}
    # for i in range(3):
    #     clothes_photo[i]=clothes.filter(id=int(id_list[i])).values('photo')[0]['photo']
    #     # photo[i]=json.loads(serializers.serialize('json',clothes_photo[i]))[0].get('fields').get('photo')
    clothes_photos = []
    for clothes_id in id_list:
        if clothes_id.isdigit():
            photo = clothes.filter(id=int(clothes_id))
            if photo:
                clothes_photos.append(photo.values('photo')[0]['photo'])
            elif abs(clothes_id) < len(default_match_id):
                clothes_photos.append(default_match_id[abs(clothes_id)])
            else:
                print('invalid clothes id')
    return clothes_photos


# 根据给定的photo—urls获取对应的图片并进行拼接,拼接后的图片写进test.png里, 返回处理后的图片的url
def photos_url_integration(photo_urls):
    # urls = ['http://120.76.62.132:8080/photos/40padded.jpg', 'http://120.76.62.132:8080/photos/40padded.jpg',
    #         'http://120.76.62.132:8080/photos/40padded.jpg']
    photos = []
    print(len(photo_urls))
    for i in range(len(photo_urls)):
        try:
            photo_bytes = requests.get(photo_urls[i], timeout=10)
            # photos.append(photo_bytes)
            photo_np_array = cv2.imdecode(numpy.frombuffer(photo_bytes.content, numpy.uint8), 1)
            photos.append(photo_np_array)
        except requests.exceptions.ConnectionError:
            print('invalid url address')
            continue
    i = len(photo_urls)
    if i < 3:
        # return
        print('not enough input for pic url')
        return
    elif i == 3:
        photos[0] = cv2.resize(photos[0], (288, 512))
        photos[1] = cv2.resize(photos[1], (288, 1024))
        photos[2] = cv2.resize(photos[2], (288, 512))
        img = numpy.concatenate((photos[0], photos[2]))
        img = numpy.concatenate([img, photos[1]], axis=1)
        # f = open(BASE_DIR+'/test.jpg', 'w')
        # f.write(img)
        # f.close()
        cv2.imwrite(settings.BASE_DIR+'/closetUsers/tempPhotos/test.png', img)
        # cv2.imshow('img', img)
        return True


# color supposed to be a list
def is_light_color(color):
    if not isinstance(color, 'list'):
        return
    if len(color) == 3 | len(color) == 4:
        if (color[0]*0.299 + color[1]*0.578 + color[2]*0.114) >= 192:
            return True
        else:
            return False
