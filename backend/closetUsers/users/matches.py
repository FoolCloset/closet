from .models import Match
import requests
import cv2
import numpy
import pandas
from django.conf import settings

# define if the color is deep color

# class MatchSamples(object):
#
#     def __init__(self):
#
#
#     def get_samples(self, sample_name):
#         if sample_name == 'suit':
# #             return a sample pic url? or return a clothes list? or return the content in the match?
#             pic_url: str = ''
#             return pic_url

default_top = {
    'shirt': '',
    'sweater': '',
    'T-shirt': '',
    'other': '',
}

default_shoes = {
    'casual': '',
    'sports': '',
    'leather': '',
    'other': '',
}

default_trousers = {
    'jeans': '',
    'sports': '',
    'suit': '',
    'other': '',
}

default_coat = {
    'suit': '',
    'down_jacket': '',
    'jeans': '',
    'sports': '',
    'other': '',
}

default_match = {
    'winter': '',
    'summer': '',
    'spring': '',
}


# 根据给定的photo—urls获取对应的图片并进行拼接,拼接后的图片写进test.png里
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
        return False
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


# 上传图片到tomcat服务器并返回一个图片的url

