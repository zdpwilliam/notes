#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys,datetime,MySQLdb,HTMLParser
reload(sys)
sys.setdefaultencoding("UTF-8")

conn_forge = MySQLdb.connect(host="", user="zmforge_sa",
                             passwd="", db='forge', charset="utf8")
sql_forge = "delete from courses_plan where id in (6231, 6750, 7729, 8245, 8650) "

try:
    cursor_forge = conn_forge.cursor()
    del_n = cursor_forge.execute(sql_forge)
    print "%d 条记录删除成功!" % del_n

finally:
    conn_forge.commit()
    conn_forge.close()