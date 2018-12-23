from django.shortcuts import render
from closetModel.models import User, Clothes, Match, Collection
from closetModel.serializers import UserSerializer, ClothesSerializer
from rest_framework import generics
from django_filters.rest_framework import DjangoFilterBackend


# Create your views here.
# user list shows the list of all user or the user in the search field
class UserList(generics.ListAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer
    filter_backends = (DjangoFilterBackend, )
    filter_fields = ('name', 'phone', 'email')


# UserDetail returns the information of user which id matches request para id
class UserDetail(generics.RetrieveAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer


class UserSignIn(generics.CreateAPIView):
    serializer_class = UserSerializer


class UserSignOff(generics.DestroyAPIView):
    serializer_class = UserSerializer


# ClothesList returns a type or all clothes info of one user
class ClothesList(generics.ListAPIView):
    # queryset = Clothes.objects.all()
    serializer_class = ClothesSerializer
    filter_backends = (DjangoFilterBackend, )
    # enable filter para list
    filter_fields = ('user', 'type', 'color', 'season', 'pattern')

    def get_queryset(self):
        """
        Optionally restricts the returned purchases to a given user,
        by filtering against a `userID` query parameter in the URL.
        """
        queryset = Clothes.objects.all()
        user = self.request.query_params.get('userID', None)
        if user is not None:
            queryset = queryset.filter(user=user)
        return queryset


# get specific clothes info, identified by id
class ClothesDetail(generics.RetrieveAPIView):
    queryset = Clothes.objects.all()
    serializer_class = ClothesSerializer

