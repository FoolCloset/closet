# Generated by Django 2.1.4 on 2018-12-18 12:04

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
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
            ],
        ),
        migrations.CreateModel(
            name='Collection',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('snapshot', models.URLField(default='http://120.76.62.132:8080/photos/40padded.jpg')),
            ],
        ),
        migrations.CreateModel(
            name='Match',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('clothes_list', models.CharField(default='1', max_length=50)),
                ('like', models.BooleanField(default=True)),
                ('occasion', models.CharField(default='daily', max_length=30)),
            ],
        ),
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('password', models.CharField(default='', max_length=256)),
                ('name', models.CharField(default='ali', max_length=30)),
                ('style', models.CharField(default='casual', max_length=30, null=True)),
                ('profile', models.URLField(default='http://120.76.62.132:8080/photos/40padded.jpg')),
                ('email', models.EmailField(default='emailExam@163.com', max_length=254)),
                ('phone', models.CharField(default='40088208820', max_length=30)),
            ],
        ),
        migrations.AddField(
            model_name='match',
            name='user',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, to='closetModel.User'),
        ),
        migrations.AddField(
            model_name='collection',
            name='match',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='closetModel.Match'),
        ),
        migrations.AddField(
            model_name='collection',
            name='user',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='closetModel.User'),
        ),
        migrations.AddField(
            model_name='clothes',
            name='user',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, to='closetModel.User'),
        ),
        migrations.AlterUniqueTogether(
            name='collection',
            unique_together={('user', 'match')},
        ),
    ]
