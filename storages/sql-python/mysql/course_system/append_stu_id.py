#!/usr/bin/python
# -*- coding: UTF-8 -*-
import MySQLdb


def load_lesson_id_list(conn_tr):
    sql_forge = "SELECT DISTINCT lesson_id FROM lesson_course_system GROUP BY lesson_id"
    cursor_tr = conn_tr.cursor()
    cursor_tr.execute(sql_forge)
    return cursor_tr.fetchall()


def load_lesson_stu_id_list(conn_tr, lesson_id_list):
    sql_forge = "SELECT id, stu_id FROM lessons WHERE id in (%s -1)"
    sql_ids = ""
    for lesson_id in lesson_id_list:
        sql_ids += "%d, " % lesson_id

    cursor_tr = conn_tr.cursor()
    cursor_tr.execute(sql_forge % sql_ids)
    return cursor_tr.fetchall()


def update_format_stu_id(lesson_stu_id_tuple_list):
    conn_tr_inner = MySQLdb.connect(host="", user="tr_sa",
                                    passwd="", db='tr', charset="utf8")
    sql_tr = "update lesson_course_system set stu_id = %s WHERE lesson_id = %s"
    cursor = conn_tr_inner.cursor()
    counter = 0
    for lesson_stu_id in lesson_stu_id_tuple_list:
        counter += cursor.execute(sql_tr, (lesson_stu_id[1], lesson_stu_id[0]))

    conn_tr_inner.commit()
    conn_tr_inner.close()
    return counter

conn_tr = MySQLdb.connect(host="", user="tr_sa",
                             passwd="", db='tr', charset="utf8")

conn_forge = MySQLdb.connect(host="", user="zmforge_sa",
                             passwd="", db='forge', charset="utf8")

batch_size = 200
# prepare for lessonId and stuId map
try:
    lesson_id_tuple = load_lesson_id_list(conn_tr)
    lesson_id_list = list(filter(lambda lesson_id: lesson_id[0] is not None, lesson_id_tuple))

    lesson_counter = len(lesson_id_list)
    last_count = lesson_counter / batch_size
    print "%d 条记录需要更新! 每批 %d 共 %d 批： \n" % (lesson_counter, batch_size, last_count)

    monitor_counter = 1
    lesson_point_counter = 0
    lesson_id_batch = []
    for lesson_id in lesson_id_list:
        lesson_length = len(lesson_id_batch)
        if lesson_length == batch_size or monitor_counter == last_count:
            lesson_stu_tuple_list = load_lesson_stu_id_list(conn_forge, lesson_id_batch)
            update_n = update_format_stu_id(lesson_stu_tuple_list)
            lesson_point_counter += update_n
            print "%d 节课记录添加stu_id成功! 当前第 %d批" % (update_n, monitor_counter)
            monitor_counter += 1
            lesson_id_batch = []
        else:
            lesson_id_batch.append(lesson_id[0])
            lesson_length += 1

    print "%d 条记录更新成功!" % lesson_point_counter

finally:
    conn_forge.commit()
    conn_forge.close()
