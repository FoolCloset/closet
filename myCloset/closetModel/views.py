from .models import Clothes,Collection,Match,User
from rest_framework import mixins
from rest_framework.views import APIView
from rest_framework import generics
from rest_framework.response import Response
from rest_framework.request import Request
from django.contrib.auth.models import User
from django.http import HttpResponse,HttpRequest
from .serializers import ClothesSerializer,CollectionSerializer,UserSerializer,MatchSerializer
from rest_framework import permissions

#最简洁的形式，为自己的snippetList声明成类，继承后，指明queryset和serializer_class
'''
关于User model的查看列表和细节类
'''
class UserList(generics.ListAPIView):

    # def perform_create(self, serializer):
    #     serializer.save(owner=self.request.user)
    queryset = User.objects.all()
    serializer_class =UserSerializer
    # permission_classes = (permissions.IsAuthenticatedOrReadOnly,)


class UserDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer
    # permission_classes = (permissions.IsAuthenticatedOrReadOnly,)


'''
关于clothes model的查看列表和细节类
'''
class ClothesList(generics.ListAPIView):

    # def perform_create(self, serializer):
    #     serializer.save(owner=self.request.user)
    queryset = Clothes.objects.all()
    serializer_class = ClothesSerializer
    # permission_classes = (permissions.IsAuthenticatedOrReadOnly,)


class ClothesDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Collection.objects.all()
    serializer_class = ClothesSerializer
    # permission_classes = (permissions.IsAuthenticatedOrReadOnly,)


'''
关于Match model的查看列表和细节类
'''
class MatchList(generics.ListAPIView):

    # def perform_create(self, serializer):
    #     serializer.save(owner=self.request.user)
    queryset = Match.objects.all()
    serializer_class = MatchSerializer
    # permission_classes = (permissions.IsAuthenticatedOrReadOnly,)


class MatchDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Match.objects.all()
    serializer_class =MatchSerializer
    # permission_classes = (permissions.IsAuthenticatedOrReadOnly,)



'''
关于collection model的查看列表和细节类
'''
class CollectionList(generics.ListAPIView):

    # def perform_create(self, serializer):
    #     serializer.save(owner=self.request.user)
    queryset = Collection.objects.all()
    serializer_class = CollectionSerializer
    # permission_classes = (permissions.IsAuthenticatedOrReadOnly,)


class CollectionDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Collection.objects.all()
    serializer_class = CollectionSerializer
    # permission_classes = (permissions.IsAuthenticatedOrReadOnly,)







