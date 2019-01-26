#!/usr/bin/python
# -*- coding: UTF-8 -*-
from datetime import *
import sys,MySQLdb,HTMLParser
reload(sys)
sys.setdefaultencoding("UTF-8")


# sql_tr = "INSERT INTO course_plan" \
#          " (id, cou_id, stu_id, tea_id, grade_id, subject_id, course_hour, goal_suggestion," \
#          " parent_suggestion, student_suggestion, created_at, updated_at)" \
#          " VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) "
#
# conn_tr = MySQLdb.connect(host="",
#                           user="", passwd="",
#                           db="", charset="utf8")
#
# now = str(datetime.now())
# one_line = ('6', '6', '6', '6', '6', '6', '2.0', '中文', '中文', '中文', now, now)
#
# cursor = conn_tr.cursor()
# cursor.execute(sql_tr, one_line)
#
# conn_tr.commit()
# conn_tr.close()

html_parser = HTMLParser.HTMLParser()

str = html_parser.unescape(None);

print str