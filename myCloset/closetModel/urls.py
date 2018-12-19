
from django.contrib import admin
from django.urls import register_converter,path
from .views import ClothesDetail,ClothesList,CollectionDetail,CollectionList,UserDetail,UserList,MatchgetID,MatchList,MatchDetail
urlpatterns = [
    path('Clothes/', ClothesList.as_view()),
    path('Clothes/<int:pk>/', ClothesDetail.as_view()),
    path('Collection/', CollectionList.as_view()),
    path('Collection/<int:pk>/', CollectionDetail.as_view()),
    path('User/', UserList.as_view()),
    path('User/<int:pk>/', UserDetail.as_view()),

]
