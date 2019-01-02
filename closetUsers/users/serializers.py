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
        fields = ('url', 'id', 'user_id', 'owner', 'type', 'subtype', 'color', 'season', 'pattern', 'photo', 'note')


#收藏信息序列
class CollectionSerializer(serializers.ModelSerializer):
    # owner = serializers.ReadOnlyField(source='owner.username')

    class Meta:
        model = Collection
        fields = ('user', 'match', 'snapshot')

        def create(self, validated_data):
            """
            Create and return a new `Snippet` instance, given the validated data.
            """
            return Collection.objects.create(**validated_data)

        def update(self, instance, validated_data):
            """
            Update and return an existing `Snippet` instance, given the validated data.
            """
            instance.user = validated_data.get('user', instance.user)
            instance.match = validated_data.get('match', instance.match)
            instance.snapshot = validated_data.get('snapshot', instance.snapshot)
            instance.save()
            return instance

