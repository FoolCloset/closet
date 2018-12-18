from django.contrib import admin
from closetModel.models import User, Clothes, Match, Collection

# Register your models here.

admin.site.register([User, Clothes, Match, Collection, ])
