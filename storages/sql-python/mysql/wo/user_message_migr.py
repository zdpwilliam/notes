#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys,MySQLdb
reload(sys)
sys.setdefaultencoding("UTF-8")


def load_message_data(conn_old):
    sql_forge = " SELECT fbsc_tmp.id, lesson_uid, username, mobile, role, content, create_time" \
                " FROM fb_session_content fbsc_tmp" \
                " left join fb_lesson_session fbls_tmp" \
                " on fbsc_tmp.session_id = fbls_tmp.id"
    cursor_forge = conn_old.cursor()
    cursor_forge.execute(sql_forge)
    return cursor_forge.fetchall()


def insert_format_message(format_message_tuples):
    part_conn_new = MySQLdb.connect(host="127.0.0.1", user="root",
                               passwd="123456", db="wo", charset="utf8")
    sql_tr = "insert into fb_user_message (id, lesson_uid, username, mobile, role, content, send_time) " \
             " VALUES (%s, %s, %s, %s, %s, %s, %s)"
    cursor = part_conn_new.cursor()
    insert_count = cursor.executemany(sql_tr, format_message_tuples)
    part_conn_new.commit()
    part_conn_new.close()
    return insert_count


conn_old = MySQLdb.connect(host="", user="forge",
                           passwd="", db='wo', charset="utf8")
conn_new = MySQLdb.connect(host="127.0.0.1", user="root",
                           passwd="123456", db="wo", charset="utf8")

try:
    source_message_tuples = load_message_data(conn_old)
    counter = insert_format_message(source_message_tuples)
    print "%d 条记录插入成功!" % counter
finally:
    conn_old.commit()
    conn_old.close()
    conn_new.commit()
    conn_new.close()

