from django.urls import path
from rest_framework.urlpatterns import format_suffix_patterns
from users import views
from django.views.static import serve
from django.conf import settings


urlpatterns = format_suffix_patterns([
    path('sign-up/', views.sign_up),
    path('sign-in/', views.sign_in),
    path('sign-out/', views.sign_out),
    path('sign-off/', views.sign_off),
    path('user-info/', views.UserInfoList.as_view(), name='user-info-list'),
    path('user-info/<int:pk>/', views.UserInfoDetail.as_view(), name='user-info-detail'),
    path('clothes/', views.ClothesList.as_view(), name='clothes-list'),
    path('clothes/<int:pk>/', views.ClothesDetail.as_view(), name='clothes-detail'),
    path('collections/', views.CollectionList.as_view(), name='collection-list'),
    path('collections/<int:pk>/', views.CollectionDetail.as_view(), name='collection-detail'),
    path('users/', views.UserList.as_view(), name='user-list'),
    path('users/<int:pk>', views.UserDetail.as_view(), name='user-detail'),
    # path('getmatch/', views.get_match, name="match-getmatch"),
    path('upload/', views.upload_img),
])
