
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


#生成搭配
@csrf_exempt
def get_match(request):
    #发出get请求认为是返回当前用户的已经生成好的搭配
    if request.method=='GET':
        # data=json.loads(request.body)
        username=request.GET.get('username')
        if username:
            #获得对用用户的photo列表，转成url,match_photo是一个字典数组
            #filter.value[index]直接就是一个字典
            match_photo=Match.objects.filter(user=User.objects.get(username=username)).values('clothes_list')
            #是一个对象数组,photo_urls[i]代表一组搭配的图片
            photo_urls={}
            for i in range(len(match_photo)):
                photo_urls[i]=string_to_urls(username,match_photo[i]['clothes_list'])
            return JsonResponse(data={"msg":"OK",'photo':photo_urls},status=status.HTTP_200_OK)
        else:
            return JsonResponse(data={"msg": "生成搭配失败，参数有误"}, status=status.HTTP_400_BAD_REQUEST)

    #发出post请求，认为是创建新的搭配
    if request.method=='POST':
        data=json.loads(request.body)
        if 'username'in data and 'password'in data:
            username = data['username']
            password = data['password']
            user=authenticate(username=username, password=password)
            #这里认为match的图片区域还是字符串的形式 不是url数组
            if user:
                #验证成功
                match=create_match(data)
        else:
            #未提供身份验证的参数
            return JsonResponse(data={"msg": "生成搭配失败，参数有误"}, status=status.HTTP_400_BAD_REQUEST)

        #将match数据返回并且插入数据库
        if match:
            #photo是一个map,key为photo1,photo2,photo3
            photo_urls=string_to_urls(username,match.get('clothes_list'))

            #还需要判断数据库中是否已有该条搭配
            #已有搭配

            user=User.objects.get(username=username)
            ifMatch=Match.objects.filter(user=user,clothes_list=match['clothes_list'])
            if ifMatch:
                return JsonResponse(data={"msg": "OK",'photo':photo_urls}, status=status.HTTP_200_OK)

            #不存在搭配,插入

            Match.objects.create(user=user,clothes_list=match['clothes_list'],like=match['like'],
                                 occasion=match['occasion'])

            return JsonResponse(data={"msg": "OK", 'photo':photo_urls}, status=status.HTTP_200_OK)
        else:
            return JsonResponse(data={"msg": "生成搭配失败，参数有误"}, status=status.HTTP_400_BAD_REQUEST)


'''
默认情况下，生成搭配时默认都参考温度，场合可选
'''


def create_match(type):
    if 'temperature' in type and 'occasion' in type:
        return match_by_tem_and_occa(type)

    elif 'temperature' in type:
        return match_by_tem(type)

    else:
        return False


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
    match = {'user': user, 'clothes_list': '1,2,3,', 'like': False, 'occasion': 'daily'}
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
    coat_list = Clothes.objects.filter(user='error')
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
    # if top_list:
    #     top_id = top_list[0].id
    #     top_url = top_list[0].photo
    # else:
    #     top_url = default_top['shirt']
    # if trousers_list:
    #     trousers_id = trousers_list[0].id
    #     trousers_url = trousers_list[0].photo
    # if shoes_list:
    #     shoes_id = shoes_list[0].id
    #     shoes_url = shoes_list[0].photo
    # if coat_list & season == 'winter':
    #     coat = coat_list[0]
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
    clothes=Clothes.objects.filter(user=User.objects.get(username=username))
    clothes_photo={}
    for i in range(3):
        clothes_photo[i]=clothes.filter(id=int(id_list[i])).values('photo')[0]['photo']
        # photo[i]=json.loads(serializers.serialize('json',clothes_photo[i]))[0].get('fields').get('photo')

    return clothes_photo


# 将photo_urls中的图片整合成一张
def photo_urls_integration(photo_urls):
    # download the photo
    photos = {}
    for i in range(len(photo_urls)):
        try:
            photos[i] = requests.get(photo_urls[i], timeout=10)  # max request time: 10 seconds
        except requests.exceptions.ConnectionError:
            print('invalid url address')
            continue
