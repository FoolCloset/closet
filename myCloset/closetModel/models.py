from django.db import models

# Create your models here.
# READ ONLY!!!  CAN'T MODIFY!!!


class User(models.Model):
    id = models.AutoField(primary_key=True)
    password = models.CharField(max_length=256, default='')
    name = models.CharField(max_length=30, default='ali')
    style = models.CharField(max_length=30, default='casual', null=True)
    profile = models.URLField(default='http://120.76.62.132:8080/photos/40padded.jpg')
    email = models.EmailField(default='emailExam@163.com')
    phone = models.CharField(max_length=30, default='40088208820')

    def __str__(self):
        return self.name


class Clothes(models.Model):
    id = models.AutoField(primary_key=True)
    user = models.ForeignKey(User, default=1, on_delete=models.CASCADE)
    type = models.CharField(max_length=20, default='Tshirt' )
    color = models.CharField(max_length=20, default='black')
    note = models.TextField(max_length=100, null=True, blank=True)
    season = models.CharField(max_length=20, null=True, blank=True, default='summer')
    pattern = models.CharField(max_length=20, default='pure')
    # class Meta:
    #     unique_together=("id", "user")


class Match(models.Model):
    id = models.AutoField(primary_key=True)
    user = models.ForeignKey(User, default=1, on_delete=models.CASCADE)
    clothes_list = models.CharField(max_length=50, default='1')
    like = models.BooleanField(default=True)
    occasion = models.CharField(max_length=30, default='daily')


class Collection(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    match = models.ForeignKey(Match, on_delete=models.CASCADE)

    class Meta:
        unique_together = ("user", "match")
    snapshot = models.URLField(default='http://120.76.62.132:8080/photos/40padded.jpg')
