# Generated by Django 2.1.4 on 2018-12-22 13:15

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('users', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Clothes',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('type', models.CharField(default='Tshirt', max_length=20)),
                ('color', models.CharField(default='black', max_length=20)),
                ('note', models.TextField(blank=True, max_length=100, null=True)),
                ('season', models.CharField(blank=True, default='summer', max_length=20, null=True)),
                ('pattern', models.CharField(default='pure', max_length=20)),
                ('photo', models.URLField(default='http://120.76.62.132:8080/photos/default.jpg')),
                ('user', models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='Collection',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('snapshot', models.URLField(default='http://120.76.62.132:8080/photos/default.jpg')),
            ],
        ),
        migrations.CreateModel(
            name='Match',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('clothes_list', models.CharField(default='1', max_length=50)),
                ('like', models.BooleanField(default=True)),
                ('occasion', models.CharField(default='daily', max_length=30)),
                ('user', models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.AddField(
            model_name='collection',
            name='match',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='users.Match'),
        ),
        migrations.AddField(
            model_name='collection',
            name='user',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL),
        ),
        migrations.AlterUniqueTogether(
            name='collection',
            unique_together={('user', 'match')},
        ),
    ]