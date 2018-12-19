from rest_framework import serializers
from closetModel.models import User, Clothes, Match, Collection


class UserSerializer(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = User
        fields = ('url', 'id', 'name', 'style', 'phone', 'email', 'profile', )


class ClothesSerializer(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = Clothes
        fields = ('url', 'id', 'user_id', 'type', 'color', 'season', 'pattern', 'photo', 'note')


