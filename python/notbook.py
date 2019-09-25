# -*- coding: utf-8 -*-

#数组
#初始化
l = [1,2,3,4,5,6,7,8,9,11,24]
l = [] #空初始化
l = list() #空初始化
h = list(range(10))  #初始化带序列的
h.append(15) #添加到末尾
h.insert(5,999) #插入元素到5位置
h.pop() #删除末尾
h.pop(5) #删除第i个元素
h.pop(-1) #删除倒数第一个元素
len(h) #获取长度

#tuple :元组 tuple一旦初始化就不能修改
classmates = ('Michael', 'Bob', 'Tracy')

#字典 dict 类似于map   key唯一
ma = {'Michael': 95, 'Bob': 75, 'Tracy': 85} #初始化
ma = {} #初始化
ma['Michael'] = 95 #赋值
'Thomas' in ma #判断key是否存在
ma.get('Thomas') #获取value 没有返回None
ma.get('Thomas', -1) #获取value如果获取不到可以返回指定的值
ma.pop('Thomas') #删除元素

#set 
# set和dict类似，也是一组key的集合，但不存储value。由于key不能重复，所以，在set中，没有重复的key。
# 要创建一个set，需要提供一个list作为输入集合：
s = set([1, 2, 3]) #初始化
#重复元素在set中自动被过滤
#通过add(key)方法可以添加元素到set中，可以重复添加，但不会有效果：
s.add(4)
#通过remove(key)方法可以删除元素：
s.remove(4)
#set可以看成数学意义上的无序和无重复元素的集合，因此，两个set可以做数学意义上的交集、并集等操作：
s1 = set([1, 2, 3])
s2 = set([2, 3, 4])
s1 & s2
{2, 3}
s1 | s2
{1, 2, 3, 4}


#函数
#函数名其实就是指向一个函数对象的引用，完全可以把函数名赋给一个变量，相当于给这个函数起了一个“别名”：
a = abs # 变量a指向abs函数
a(-1) # 所以也可以通过a调用abs函数
1

#定义函数
def my_abs(x):
    if x >= 0:
        return x
    else:
        return -x

#默认参数
# n为默认参数 如果power(5) 则等于power(5,2)
def power(x, n=2):
    return 0
#一是必选参数在前，默认参数在后，否则Python的解释器会报错（思考一下为什么默认参数不能放在必选参数前面）；
#二是如何设置默认参数。
#当函数有多个参数时，把变化大的参数放前面，变化小的参数放后面。变化小的参数就可以作为默认参数。
