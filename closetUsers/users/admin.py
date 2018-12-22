from django.contrib import admin
from .models import User, Clothes, Match, Collection

# Register your models here.
admin.site.register([User, Clothes, Match, Collection])
