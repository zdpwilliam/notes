#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys,redis
reload(sys)
sys.setdefaultencoding("UTF-8")

key_pattern = "course-hour-rank-key:*teacher-month:20*"
redis_conn = redis.Redis(host="",
                         password="",
                         port=6379, db=0)
keys = redis_conn.keys(key_pattern)

counter = 0
for key in keys:
    result = redis_conn.delete(key)
    print "%s deleted --> %s" % (key, str(result))
    counter = (counter + 1)

print "deleted count: %d" % counter
