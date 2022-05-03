#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys,MySQLdb
reload(sys)
sys.setdefaultencoding("UTF-8")


def load_answer_data(conn_old):
    sql_forge = "SELECT id, qorder_id, username, fbud_tmp.mobile, role, contact, net_state," \
                " platform_info, version_info, device_state_info, created_at, updated_at" \
                " FROM wo.fb_user_device fbud_tmp" \
                " left join fb_qorder_user_relation fbqur_tmp" \
                " on fbud_tmp.id = fbqur_tmp.user_id "
    cursor_old = conn_old.cursor()
    cursor_old.execute(sql_forge)
    return cursor_old.fetchall()


def insert_answer(format_answer_tuples):
    part_conn_new = MySQLdb.connect(host="127.0.0.1", user="root",
                                    passwd="123456", db="wo", charset="utf8")
    sql_tr = "INSERT INTO `fb_ticket_user_record` (`id`, `ticket_id`, `username`, `mobile`," \
             " `role`, `contact`, `net_state`, `platform_info`, `version_info`, `device_state_info`," \
             " `created_at`, `updated_at`) " \
             " VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);"
    cursor = part_conn_new.cursor()
    insert_count = cursor.executemany(sql_tr, format_answer_tuples)
    part_conn_new.commit()
    part_conn_new.close()
    return insert_count


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

