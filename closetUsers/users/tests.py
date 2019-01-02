from django.test import TestCase

# Create your tests here.

import requests
import cv2
import numpy
import pandas
from settings import BASE_DIR


urls = ['http://120.76.62.132:8080/photos/40padded.jpg', 'http://120.76.62.132:8080/photos/40padded.jpg',
        'http://120.76.62.132:8080/photos/40padded.jpg']
photos = []
print(len(urls))
for i in range(len(urls)):
    try:
        photo_bytes = requests.get(urls[i], timeout=10)
        # photos.append(photo_bytes)
        photo_np_array = cv2.imdecode(numpy.frombuffer(photo_bytes.content, numpy.uint8), 1)
        photos.append(photo_np_array)
    except requests.exceptions.ConnectionError:
        print('invalid url address')
        continue
i = len(urls)
if i < 3:
    # return
    print('not enough input for pic url')
elif i == 3:
    photos[0] = cv2.resize(photos[0], (288, 512))
    photos[1] = cv2.resize(photos[1], (288, 1024))
    photos[2] = cv2.resize(photos[2], (288, 512))
    img = numpy.concatenate((photos[0], photos[2]))
    img = numpy.concatenate([img, photos[1]], axis=1)
    # f = open(BASE_DIR+'/test.jpg', 'w')
    # f.write(img)
    # f.close()
    cv2.imwrite('test.png', img)
    cv2.imshow('img', img)


