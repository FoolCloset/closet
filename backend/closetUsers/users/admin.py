from django.contrib import admin
from .models import User, Clothes, Match, Collection

# Register your models here.
# 注册后admin界面将会出现以下类，可通过后台管理界面添加具体的数据库实例
admin.site.register([User, Clothes, Match, Collection])
