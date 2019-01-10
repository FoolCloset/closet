from imageai.Detection import ObjectDetection
import os
from PIL import Image
import datetime
import cv2
import numpy as np
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
import pandas
from django.conf import settings
import os
import random
import haishoku
from users import matches
from haishoku.haishoku import Haishoku


# 上传图片到静态文件的文件夹，返回图片的url
@csrf_exempt
def upload_img(request):

    if request.method == 'POST':
        img = request.FILES.get('file', None)
        if not img:
            return JsonResponse(data={"msg": "没有上传的图片文件"}, status=status.HTTP_400_BAD_REQUEST)
        # 验证用户权限
        # if 'username' in request & 'password' in request:
        # if request.REQUEST.has_key('username') & request.REQUEST.has_key('password'):
        #     user = request.POST['username']
        #     password = request.POST['password']
        #     user = authenticate(request)
        # user = User.objects.filter(username=request.user.username)
        username = request.POST.get('username', None)
        password = request.POST.get('password', None)
        user = authenticate(username=username, password=password)
        if not user:
            return JsonResponse(data={"msg": "无权限访问该操作"}, status=status.HTTP_401_UNAUTHORIZED)
        # if 'name' not in img:
        #     return JsonResponse(data={"msg": "无名文件无法上传"}, status=status.HTTP_400_BAD_REQUEST)
        user = User.objects.get(id=6)
        img_id = Clothes.objects.all().count() + 1
        file_name = str(user.id) + '_' + str(img_id) + '.png'
        file_path = os.path.join(settings.IMG_ROOT, file_name)
        while os.path.exists(file_path):
            img_id = img_id + 1
            file_name = str(user.id) + '_' + str(img_id) + '.png'
            file_path = os.path.join(settings.IMG_ROOT, file_name)
        dest_img_path = open(file_path, 'wb+')
        for chunk in img.chunks():
            dest_img_path.write(chunk)
        dest_img_path.close()
        file_url = 'http://' + settings.PHOTO_HOST + '/img/' + file_name
        return JsonResponse(data={"msg": "图片上传成功", "url": file_url}, status=status.HTTP_202_ACCEPTED)
    else:
        return JsonResponse(data={"msg": "this method is not allowed"}, status=status.HTTP_405_METHOD_NOT_ALLOWED)


# 衣型识别接口，接收图片，返回处理后的衣服初始化参数
@csrf_exempt
def clothes_recognize(request):

    if request.method == 'POST':
        img = request.FILES.get('file', None)
        if not img:
            return JsonResponse(data={"msg": "没有上传的图片文件"}, status=status.HTTP_400_BAD_REQUEST)
        # 验证用户权限
        user = User.objects.filter(username=request.user.username)
        if not user:
            return JsonResponse(data={"msg": "无权限访问该操作"}, status=status.HTTP_401_UNAUTHORIZED)
        img_id = random.randint(1, 99999999)
        file_name = str(img_id) + '.png'
        file_path = os.path.join(settings.RECOGNITION_ROOT, file_name)
        while os.path.exists(file_path):
            img_id = img_id + 1
            file_name = str(img_id) + '.png'
            file_path = os.path.join(settings.RECOGNITION_ROOT, file_name)
        dest_img_path = open(file_path, 'wb+')
        for chunk in img.chunks():
            dest_img_path.write(chunk)
        dest_img_path.close()
        # 提取物体
        # 识别背景，若背景为纯色，则直接处理， 若不是，则先检测目标
        type = "trousers"
        subtype = "jeans"
        color = "#ffffff"
        season = "winter"
        pattern = "pure"
        if is_pure_bg(dest_img_path):
            haishoku_img = Haishoku.loadHaishoku(dest_img_path)
            dominant = haishoku_img.dominant
            color = str(dominant)
            color = rgb2hex(color)
        else:
            img = img_person_detection(dest_img_path)
        file_url = 'http://' + settings.PHOTO_HOST + '/recognition/' + file_name
        return JsonResponse(data={"msg": "图片上传成功", "url": file_url, "type": "", "subtype": subtype,
                                  "color": color, "note": "", "season": season, "pattern": pattern,
                                  "photo": file_url}, status=status.HTTP_202_ACCEPTED)
    else:
        return JsonResponse(data={"msg": "this method is not allowed"}, status=status.HTTP_405_METHOD_NOT_ALLOWED)


def img_person_detection(img_name, detection_speed="normal"):
    start_time = datetime.datetime.now()
    os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
    execution_path = os.getcwd()
    detector = ObjectDetection()
    detector.setModelTypeAsRetinaNet()
    # detector.setModelTypeAsTinyYOLOv3()
    detector.setModelPath(os.path.join('/Users/luomei/Downloads', "resnet50_coco_best_v2.0.1.h5"))
    detector.loadModel(detection_speed=detection_speed)
    img_path = os.path.join('/Users/luomei/Desktop', "image2.jpeg")
    # detections = detector.detectObjectsFromImage(input_image=img_path,
    #                                              output_image_path=os.path.join(execution_path, "image1_new.jpg"))
    custom_objects = detector.CustomObjects(person=True)
    detections = detector.detectCustomObjectsFromImage(custom_objects=custom_objects,
                                                       input_image=img_path,
                                                       output_image_path=os.path.join(execution_path, "image1_custom.jpeg"))
    # 获取物体位置
    boxs = []
    for eachObject in detections:
        # sys.stderr.write(detection_speed, " : ", eachObject["name"], " : ", eachObject["percentage_probability"])
        # sys.stderr.write("\t pos : ", eachObject["box_points"])
        boxs.append(eachObject["box_points"])
    # 裁剪物体
    img_url = None
    for box in boxs:
        img = Image.open(img_path)
        crop_img = img.crop(box)
        crop_img.save(os.path.join(execution_path, 'img_crop.png'))
    # 求解边缘
    # 通过mask过滤图片
    # 获取主要配色和款式
    end_time = datetime.datetime.now()
    # sys.stderr.write(detection_speed, 'time: ', (end_time - start_time).seconds)
    # cv2.destroyAllWindows()
    return img_url


# rgb suppose to be string
def rgb2hex(rgb):
    if isinstance(rgb, str):
        result = '#'
        if len(rgb):
            if rgb[0] == '(' or rgb[0] == '{':
                colors = rgb.split(',')
                for color in colors:
                    temp = color.lstrip('(').rstrip(')')
                    temp = temp.lstrip('{').rstrip('}')
                    temp = int(temp)
                    if temp >= 0 & temp <= 255:
                        temp = str(hex(temp))
                    else:
                        return
                    result = result + temp.lstrip('0x')
                return result
    return


def hex2rgb(rgb_hex):
    if len(rgb_hex) == 7:
        if rgb_hex[0] == '#':
            r = int(rgb_hex[1:3], 16)
            g = int(rgb_hex[3:5], 16)
            b = int(rgb_hex[5:7], 16)
            if (r >= 0) & (r < 256) & (g >= 0) & (g < 256) & (b >= 0) & (b < 256):
                r = str(r)
                g = str(g)
                b = str(b)
            else:
                return
            return '(' + ','.join((r, g, b)) + ')'
    return


# img type: string
def is_pure_bg(img_path, test_r=10):
    img = cv2.imread(img_path)
    w, h, d = img.shape
    if (w < test_r) | (h < test_r):
        return False
    if img.dtype != 'uint8':
        return False
    img_hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    block_upright = img_hsv[0:test_r, 0:test_r, :]
    block_upleft = img_hsv[w-test_r:w, h-test_r:h, :]
    block_downright = img_hsv[0:test_r, h-test_r:h, :]
    block_downleft = img_hsv[w-test_r:w, 0:test_r, :]
    color_dict = matches.getColorList()
    for key, color_list in color_dict.items():
        low = color_list[0]
        upper = color_list[1]
        if (block_downleft >= low ).all() & (block_downleft <= upper).all() & (block_downright >= low).all() & \
                (block_downright <= upper).all() &(block_upleft >= low).all() & (block_upleft <= upper).all() & \
                (block_upright >= low).all() & (block_upright <= upper).all():
            return True
    return False










