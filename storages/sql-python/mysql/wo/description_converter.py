#!/usr/bin/python
# -*- coding:utf-8 -*-
import sys, MySQLdb, urllib
reload(sys)
sys.setdefaultencoding("UTF-8")


def load_descriptions(conn_forge):
    sql_forge = " SELECT id, description FROM fb_ticket where id < 129572 "
    cursor_forge = conn_forge.cursor()
    cursor_forge.execute(sql_forge)
    return cursor_forge.fetchall()


def update_descriptions(conn_tr, id, description):
    sql_tr = " UPDATE fb_ticket SET description = %s WHERE id = %s "
    cursor = conn_tr.cursor()
    cursor.execute(sql_tr, (description, id))


conn = MySQLdb.connect(host="",
                       user="forge", passwd="",
                       db="wo", charset="UTF8")

counter = 0
try:
    data_tuples = load_descriptions(conn)
    for data in data_tuples:
        id_tmp = str(data[0])
        description_tmp = urllib.unquote(str(data[1]))
        if description_tmp is None or description_tmp == 'None':
            description_tmp = ''
        update_descriptions(conn, id_tmp, description_tmp)
        counter += 1

    print "%d 条记录插入成功!" % counter

finally:
    conn.commit()
    conn.close()
