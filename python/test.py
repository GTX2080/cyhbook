# -*- coding: utf-8 -*-
import math

def my_ba(x,y,step,angle =0 ):
    nx = x + step * math.cos(angle)
    ny = x + step * math.sin(angle)
    return nx,ny

def test(x,y,z):
    return 15,16,17,18
        

def enroll(name,gender,age=6,city='NY'):
    print('name:', name)
    print('gender:', gender)
    print('age:', age)
    print('city:', city)
    print('注册成功')

def end(l = []):
    l.append('end')
    return l

def calc(number):
    sum = 0
    if n in number:
        