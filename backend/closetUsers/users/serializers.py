from rest_framework import serializers
from users.models import User, Clothes, Match, Collection
import re
import random


class SignUpUserInfoSerializer(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = User
        fields = ('url', 'username', 'id', 'email', 'phone', 'style', 'profile', 'password')

    def validate_username(self, username):
        if not username:
            username = 'user' + str(random.randint(1, 999999))
        try:
            user = User.objects.filter(username=username)
        except TypeError:
            raise serializers.ValidationError("参数类型错误")
        if user:
            raise serializers.ValidationError("该用户名已存在")
        return username

    def validate_email(self, email):
        email_pat = re.compile(r'^[0-9a-zA-Z_]{0,19}@[0-9a-zA-Z]{1,13}\.[com,cn,net]{1,3}$')
        if not re.match(email_pat, email):
             raise serializers.ValidationError("邮箱地址无效")
        else:
            try:
                email_users = User.objects.filter(email=email)
            except TypeError:
                raise serializers.ValidationError("邮箱格式错误")
            if email_users:
                raise serializers.ValidationError("该邮箱已注册过")
        return email

    def validate_phone(self, phone):
        # check if phone is valid
        phone_pat = re.compile(r'^(13\d|14[5|7]|15\d|166|17[3|6|7]|18\d)\d{8}$')
        if not re.match(phone_pat, phone):
            raise serializers.ValidationError("手机格式错误")
        else:
            try:
                phone_users = User.objects.filter(phone=phone)
            except TypeError:
                raise serializers.ValidationError("手机格式错误")
            if phone_users:
                raise serializers.ValidationError("该手机号已注册过")
        return phone

    def validate_password(self, password):
        if not password:
            raise serializers.ValidationError('密码不能为空')
        return password


# 用户信息序列
class UserInfoSerializer(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = User
        fields = ('url', 'username', 'id', 'email', 'phone', 'style', 'profile')


# 用户相关信息序列，目前主要是用户的衣物
class UserSerializer(serializers.HyperlinkedModelSerializer):
    clothes = serializers.PrimaryKeyRelatedField(many=True, queryset=Clothes.objects.all())

    class Meta:
        model = User
        fields = ('id', 'username', 'clothes')


# 衣物具体信息序列
class ClothesSerializer(serializers.HyperlinkedModelSerializer):
    owner = serializers.ReadOnlyField(source='owner.username')

    class Meta:
        model = Clothes
        fields = ('url', 'id', 'user_id', 'owner', 'type', 'subtype', 'color', 'season', 'pattern', 'photo', 'note')


# 收藏信息序列
class CollectionSerializer(serializers.ModelSerializer):
    owner = serializers.ReadOnlyField(source='owner.username')

    class Meta:
        model = Collection
        # fields = ('id', 'user', 'owner', 'match', 'snapshot')
        fields="__all__"

#搭配信息序列
class MatchSerializer(serializers.HyperlinkedModelSerializer):
    owner = serializers.ReadOnlyField(source='owner.username')


    class Meta:
        model = Match
        fields = ('id', 'user', 'clothes_list', 'like', 'occasion', 'owner')




