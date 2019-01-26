#!/usr/bin/python
# -*- coding: utf-8 -*-

import hashlib, time


def random_md5():
    now = long(time.time() * 1000)
    data = "ceshi%s123key" % str(now)
    hash_md5 = hashlib.md5(data)
    return hash_md5.hexdigest()


# 带时间戳+密钥的随机字符串
print random_md5()
