#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys,datetime,MySQLdb
reload(sys)
sys.setdefaultencoding("UTF-8")


def load_answer_data(conn_old):
    sql_forge = "SELECT id, qorder_id, uanswer_category_id, created_at FROM fb_user_answer"
    cursor_old = conn_old.cursor()
    cursor_old.execute(sql_forge)
    return cursor_old.fetchall()


def insert_answer(format_answer_tuples):
    part_conn_new = MySQLdb.connect(host="127.0.0.1", user="root",
                                    passwd="123456", db="wo", charset="utf8")
    sql_tr = "insert into fb_user_answer (id, ticket_id, category_id, created_at) " \
             " VALUES (%s, %s, %s, %s)"
    cursor = part_conn_new.cursor()
    insert_count = cursor.executemany(sql_tr, format_answer_tuples)
    part_conn_new.commit()
    part_conn_new.close()
    return insert_count


def format_answer(answer):
    format_answer_tmp = []
    format_answer_tmp.append(answer[0])
    format_answer_tmp.append(answer[1])
    format_answer_tmp.append(answer[2])
    if answer[3] is None:
        format_answer_tmp.append(datetime.datetime.now())
    else:
        format_answer_tmp.append(answer[3])

    return tuple(format_answer_tmp)


conn_old = MySQLdb.connect(host="", user="forge",
                           passwd="", db='wo', charset="utf8")
conn_new = MySQLdb.connect(host="127.0.0.1", user="root",
                           passwd="123456", db="wo", charset="utf8")

try:
    source_answer_tuples = load_answer_data(conn_old)
    total_count = insert_answer(source_answer_tuples)
    print "共 %d条记录插入成功!" % total_count

finally:
    conn_old.commit()
    conn_old.close()
    conn_new.commit()
    conn_new.close()

