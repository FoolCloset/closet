# Generated by Django 2.1.4 on 2018-12-18 12:26

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('closetModel', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='clothes',
            name='photo',
            field=models.URLField(default='http://120.76.62.132:8080/photos/40padded.jpg'),
        ),
    ]
