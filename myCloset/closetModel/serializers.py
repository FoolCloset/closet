from rest_framework import serializers
from .models import Clothes,Collection,Match,User
# from django.contrib.auth.models import User
#user的序列化
class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model=User
        fields=('id','password','name','style','profile','email','phone')

    def create(self, validated_data):
        """
        Create and return a new `User` instance, given the validated data.
        """
        return User.objects.create(**validated_data)

    def update(self, instance, validated_data):
        """
        Update and return an existing `User` instance, given the validated data.
        """
        instance.id = validated_data.get('id', instance.id)
        instance.password = validated_data.get('password', instance.password)
        instance.name = validated_data.get('match', instance.match)
        instance.style = validated_data.get('style', instance.style)
        instance.profile = validated_data.get('profile', instance.profile)
        instance.email = validated_data.get('email', instance.email)
        instance.phone = validated_data.get('phone', instance.phone)

        instance.save()
        return instance

#clothes的序列化
class ClothesSerializer(serializers.ModelSerializer):
    class Meta:
        model=Clothes
        fields=('id','user','type','color','note','season','pattern','photo')

    def create(self, validated_data):
        """
        Create and return a new `Clothes` instance, given the validated data.
        """
        return Clothes.objects.create(**validated_data)

    def update(self, instance, validated_data):
        """
        Update and return an existing `Clothes` instance, given the validated data.
        """
        instance.id = validated_data.get('id', instance.id)
        instance.user = validated_data.get('user', instance.user)
        instance.type = validated_data.get('type', instance.type)
        instance.color = validated_data.get('color', instance.color)
        instance.note = validated_data.get('note', instance.note)
        instance.season = validated_data.get('season', instance.season)
        instance.pattern = validated_data.get('pattern', instance.pattern)
        instance.photo = validated_data.get('photo', instance.photo)
        instance.save()
        return instance


#Match的序列化
class MatchSerializer(serializers.ModelSerializer):
    class Meta:
        model=Match
        fields=('id','user','clothes_list','like','occasion')

    def create(self, validated_data):
        """
        Create and return a new `Match` instance, given the validated data.
        """
        return Match.objects.create(**validated_data)

    def update(self, instance, validated_data):
        """
        Update and return an existing `Match` instance, given the validated data.
        """
        instance.id = validated_data.get('id', instance.id)
        instance.user = validated_data.get('user', instance.user)
        instance.clothes_list = validated_data.get('clothes_list', instance.clothes_list)
        instance.like = validated_data.get('like', instance.like)
        instance.occasion = validated_data.get('occasion', instance.occasion)
        instance.save()
        return instance



#collection的序列化
class CollectionSerializer(serializers.ModelSerializer):
    class Meta:
        model=Collection
        fields=('user','match')

    def create(self, validated_data):
        """
        Create and return a new `Collection` instance, given the validated data.
        """
        return Collection.objects.create(**validated_data)

    def update(self, instance, validated_data):
        """
        Update and return an existing `Collection` instance, given the validated data.
        """
        instance.user = validated_data.get('user', instance.user)
        instance.match = validated_data.get('match', instance.match)
        instance.save()
        return instance




