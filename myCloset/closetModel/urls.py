from django.urls import path
from rest_framework.urlpatterns import format_suffix_patterns
from closetModel import views

# API endpoints
urlpatterns = format_suffix_patterns([
    path('users/', views.UserList.as_view(), name='user-list'),
    path('users/<int:pk>/', views.UserDetail.as_view(), name='user-detail'),
    path('clothes/', views.ClothesList.as_view(), name='clothes-list'),
    path('clothes/<int:pk>/', views.ClothesDetail.as_view(), name='clothes-detail'),
    path('clothes/(?P<userID>.+)/$', views.ClothesList.as_view(), name='user-clothes-list'),
])
