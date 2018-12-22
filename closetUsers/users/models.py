from django.db import models
from django.contrib.auth.models import AbstractUser
from django.conf import settings


# Create your models here.
class User(AbstractUser):
    """
    save the user info
    """
    style = models.CharField(max_length=30, default='casual', null=True, blank=True)
    profile = models.URLField(default='http://120.76.62.132:8080/photos/40padded.jpg', blank=True)
    phone = models.CharField(max_length=11,  null=True, blank=True)

    def __str__(self):
        return self.username


class Clothes(models.Model):
    id = models.AutoField(primary_key=True)
    user = models.ForeignKey(settings.AUTH_USER_MODEL, related_name='clothes', default=1, on_delete=models.CASCADE)
    type = models.CharField(max_length=20, default='Tshirt' )
    color = models.CharField(max_length=20, default='black')
    note = models.TextField(max_length=100, null=True, blank=True)
    season = models.CharField(max_length=20, null=True, blank=True, default='summer')
    pattern = models.CharField(max_length=20, default='pure')
    photo = models.URLField(default='http://120.76.62.132:8080/photos/default.jpg')
    # class Meta:
    #     unique_together=("id", "user")


class Match(models.Model):
    id = models.AutoField(primary_key=True)
    user = models.ForeignKey(settings.AUTH_USER_MODEL, default=1, on_delete=models.CASCADE)
    clothes_list = models.CharField(max_length=50, default='1')
    like = models.BooleanField(default=True)
    occasion = models.CharField(max_length=30, default='daily')


class Collection(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    match = models.ForeignKey(Match, on_delete=models.CASCADE)

    class Meta:
        unique_together = ("user", "match")
    snapshot = models.URLField(default='http://120.76.62.132:8080/photos/default.jpg')

