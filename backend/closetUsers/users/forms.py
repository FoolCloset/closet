from django.contrib.auth.forms import UserCreationForm, UserChangeForm
from django import forms
from .models import User


# 由于重写了auth 的User类，需要重写UserCreationForm 和 UserChangeForm两个表格
class RegisterForm(UserCreationForm):

    class Meta(UserCreationForm.Meta):
        model = User
        fields = ('username', 'email', 'phone', 'style', 'profile')

    # check if email is valid
    def clean_email(self):
        email = self.cleaned_data['email']
        users = User.objects.filter(email=email)
        if users:
            raise forms.ValidationError("该邮箱已注册过，尝试登录？")
        return email


class ChangeInfoForm(UserChangeForm):

    class Meta(UserChangeForm.Meta):
        model = User
        fields = ('username', 'email', 'phone', 'style', 'profile')

