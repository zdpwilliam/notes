#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys,MySQLdb
reload(sys)
sys.setdefaultencoding("UTF-8")


def load_session_data(conn_old):
    sql_forge = "SELECT id, lesson_uid, session_title, created_at FROM fb_lesson_session"
    cursor_forge = conn_old.cursor()
    cursor_forge.execute(sql_forge)
    return cursor_forge.fetchall()


def insert_format_session(format_session_tuples):
    part_conn_new = MySQLdb.connect(host="127.0.0.1", user="root",
                               passwd="123456", db="wo", charset="utf8")
    sql_tr = "insert into fb_user_session (id, lesson_uid, channel, created_at) " \
             " VALUES (%s, %s, %s, %s)"
    cursor = part_conn_new.cursor()
    insert_count = cursor.executemany(sql_tr, format_session_tuples)
    part_conn_new.commit()
    part_conn_new.close()
    return insert_count


conn_old = MySQLdb.connect(host="", user="forge",
                           passwd="", db='wo', charset="utf8")
conn_new = MySQLdb.connect(host="127.0.0.1", user="root",
                           passwd="123456", db="wo", charset="utf8")

try:
    source_session_tuples = load_session_data(conn_old)
    counter = insert_format_session(source_session_tuples)
    print "%d 条记录插入成功!" % counter
finally:
    conn_old.commit()
    conn_old.close()
    conn_new.commit()
    conn_new.close()

