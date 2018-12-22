from rest_framework import serializers
from users.models import User, Clothes, Match, Collection
import re


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
        fields = ('url', 'id', 'user_id', 'owner', 'type', 'color', 'season', 'pattern', 'photo', 'note')



