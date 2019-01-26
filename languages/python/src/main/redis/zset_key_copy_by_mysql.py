#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys,redis,MySQLdb
reload(sys)
sys.setdefaultencoding("UTF-8")

source_key = "teacher_teach_minutes_month_rank"
full_target_key = "course-hour-rank-key:full-teacher-month"
part_target_key = "course-hour-rank-key:part-teacher-month"

redis_conn = redis.Redis(host="",
                         password="", port=6379, db=0)
tuple_values = redis_conn.zrange(source_key, 0, -1, withscores=True)

conn_forge = MySQLdb.connect(host="", user="zmforge_sa",
                             passwd="", db='forge', charset="utf8")

sql = "SELECT time_type FROM teachers where id = "

counter = 0
for tuple_value in tuple_values:
    id_value = tuple_value[0]
    cursor_forge = conn_forge.cursor()
    n_tr = cursor_forge.execute(str(sql + id_value))
    teacher_type = cursor_forge.fetchall()[0][0]

    if teacher_type == 0 or teacher_type == 3:
        result = redis_conn.zadd(part_target_key, id_value, tuple_value[1])
    else:
        result = redis_conn.zadd(full_target_key, id_value, tuple_value[1])

    counter = (counter + 1)
    print result

print "copy count: %d" % counter
