#!/usr/bin/python
# -*- coding: utf-8 -*-
import MySQLdb
import redis
import datetime
import calendar
import httplib


# 1、加载老师id列表
# 2、请求每月课时接口
# 3、插入记录到mysql数据库