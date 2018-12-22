from django.db import models
from django.contrib.auth.models import AbstractUser
from django.conf import settings


# Create your models here.
class User(AbstractUser):
    """
    save the user info
    """
    style = models.CharField(max_length=30, default='casual', null=True, blank=True)
    profile = models.URLField(default='http://120.76.62.132:8080/photos/40padded.jpg', blank=True)
    phone = models.CharField(max_length=11,  null=True, blank=True)

    def __str__(self):
        return self.username


"""
clothes表
user：外键取自User
type：种类，如上衣、裤子、外套
color：衣物主色调，以RGB值表示
season：季节，衣物主要适合在哪个季节穿，备选项：春秋、夏、冬
pattern：款式：花色、纯色
photo：存衣物照片的url路径，照片存放在服务器上
note：备注
"""


class Clothes(models.Model):
    id = models.AutoField(primary_key=True)
    user = models.ForeignKey(settings.AUTH_USER_MODEL, related_name='clothes', default=1, on_delete=models.CASCADE)
    type = models.CharField(max_length=20, default='Tshirt' )
    color = models.CharField(max_length=20, default='black')
    note = models.TextField(max_length=100, null=True, blank=True)
    season = models.CharField(max_length=20, null=True, blank=True, default='summer')
    pattern = models.CharField(max_length=20, default='pure')
    photo = models.URLField(default='http://120.76.62.132:8080/photos/default.jpg')
    # class Meta:
    #     unique_together=("id", "user")


"""
match表【搭配】
user：外键
clothes_list：一个字符串，存组成搭配的衣物的id值，以某个分隔符隔开（约定用，隔开）
like：用户对搭配的评价
occasion：搭配适合在哪些场合，如dating、daily
"""


class Match(models.Model):
    id = models.AutoField(primary_key=True)
    user = models.ForeignKey(settings.AUTH_USER_MODEL, default=1, on_delete=models.CASCADE)
    clothes_list = models.CharField(max_length=50, default='1')
    like = models.BooleanField(default=True)
    occasion = models.CharField(max_length=30, default='daily')


"""
collection表【收藏夹】
snapshot：一张快照，存搭配的照片，以url形式存储，照片存放在服务器上
"""


class Collection(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    match = models.ForeignKey(Match, on_delete=models.CASCADE)

    class Meta:
        unique_together = ("user", "match")
    snapshot = models.URLField(default='http://120.76.62.132:8080/photos/default.jpg')

