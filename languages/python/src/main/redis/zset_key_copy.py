#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys,redis
reload(sys)
sys.setdefaultencoding("UTF-8")

source_key = "teacher_teach_minutes_month_rank"
target_key = "course-hour-rank-key:teacher-month"
# source_key = "student_used_hours_mounth_rank"
# target_key = "course-hour-rank-key:student-month"

redis_conn = redis.Redis(host="", password="", port=6379, db=0)
tuple_values = redis_conn.zrange(source_key, 0, -1, withscores=True)

counter = 0
for tuple_value in tuple_values:
    result = redis_conn.zadd(target_key, tuple_value[0], tuple_value[1])
    counter = (counter + 1)
    print result

print "copy count: %d" % counter
