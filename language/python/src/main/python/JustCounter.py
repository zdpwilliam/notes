#!/usr/bin/python
# -*- coding: UTF-8 -*-


class JustCounter:
    __secretCount = 0       # 私有变量
    _protectedCount = 0     # protected变量
    publicCount = 0         # 公开变量

    def count(self):
        self.__secretCount += 1
        self._protectedCount += 1
        self.publicCount += 1
        print self.__secretCount


counter = JustCounter()
print "-------------start------------------"
counter.count()
counter.count()
print counter.publicCount
print counter._protectedCount
# print counter.__secretCount  # 报错，实例不能访问私有变量
# Python不允许实例化的类访问私有数据，但你可以使用 object._className__attrName 访问属性，将如下代码替换以上代码的最后一行代码：
print counter._JustCounter__secretCount

print "-------------end--------------------"

# 单下划线、双下划线、头尾双下划线说明：
# __foo__: 定义的是特殊方法，一般是系统定义名字 ，类似 __init__() 之类的。
# _foo: 以单下划线开头的表示的是 protected 类型的变量，即保护类型只能允许其本身与子类进行访问，不能用于 from module import *
# __foo: 双下划线的表示的是私有类型(private)的变量, 只能是允许这个类本身进行访问了。