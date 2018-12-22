from rest_framework import serializers
from users.models import User, Clothes, Match, Collection
import re


class UserInfoSerializer(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = User
        fields = ('url', 'username', 'id', 'email', 'phone', 'style', 'profile')


class UserSerializer(serializers.HyperlinkedModelSerializer):
    clothes = serializers.PrimaryKeyRelatedField(many=True, queryset=Clothes.objects.all())

    class Meta:
        model = User
        fields = ('id', 'username', 'clothes')


class ClothesSerializer(serializers.HyperlinkedModelSerializer):
    owner = serializers.ReadOnlyField(source='owner.username')

    class Meta:
        model = Clothes
        fields = ('url', 'id', 'user_id', 'owner', 'type', 'color', 'season', 'pattern', 'photo', 'note')



