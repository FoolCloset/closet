# Generated by Django 2.1.4 on 2018-12-29 16:31

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('users', '0003_auto_20181222_1428'),
    ]

    operations = [
        migrations.AddField(
            model_name='clothes',
            name='subtype',
            field=models.CharField(default='other', max_length=20),
        ),
        migrations.AlterField(
            model_name='clothes',
            name='type',
            field=models.CharField(default='other', max_length=20),
        ),
    ]
