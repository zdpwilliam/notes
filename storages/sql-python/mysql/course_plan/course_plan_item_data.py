#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys,datetime,MySQLdb,HTMLParser
reload(sys)
sys.setdefaultencoding("UTF-8")


def loadPlanData(conn_forge):
    sql_forge = "SELECT cpi_tmp.id, cp_tmp.cou_id, cpi_tmp.plan_id, plan_duration, plan_outlines," \
	            " plan_content, deleted_state, cpi_tmp.updated_at, cpi_tmp.created_at" \
                " FROM courses_plan_item  cpi_tmp" \
                " left join courses_plan cp_tmp" \
                " on cpi_tmp.plan_id = cp_tmp.id" \
                " limit 300002, 300375"
    cursor_forge = conn_forge.cursor()
    cursor_forge.execute(sql_forge)
    return cursor_forge.fetchall()

def insert_format_plan(format_plan_tuples):
    conn_tr = MySQLdb.connect(host="",
                              user="tr_sa", passwd="",
                              db="tr", charset="utf8")
    sql_tr = "INSERT INTO course_plan_item (id, cou_id, plan_id, duration, outline, detail," \
             "   deleted, updated_at, created_at)" \
             " VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)"
    cursor = conn_tr.cursor()
    insert_n = cursor.executemany(sql_tr, format_plan_tuples)
    conn_tr.commit()
    conn_tr.close()
    return insert_n

def concate_format_plan(plan):
    format_plan = []
    for index in range(len(plan)):
        if index == 4:
            source_str = plan[index]
            tmp_str = ""
            if source_str is not None:
                tmp_str = html_parser.unescape(source_str)
            str_len = len(tmp_str)
            if str_len > 500:
                str_len = 499
            else:
                str_len = (str_len - 1)
            format_plan.append(tmp_str[0:str_len])
        elif index == 5:
            source_str = plan[index]
            tmp_str = ""
            if source_str is not None:
                tmp_str = html_parser.unescape(source_str)
            str_len = len(tmp_str)
            if str_len > 1000:
                str_len = 999
            else:
                str_len = (str_len - 1)
            format_plan.append(tmp_str[0:str_len])
        elif index == 6:
            del_state = plan[index]
            if del_state == 1:
                format_plan.append(0)
            else:
                format_plan.append(1)
        else:
            format_plan.append(plan[index])
    return tuple(format_plan)

conn_forge = MySQLdb.connect(host="", user="zmforge_sa",
                             passwd="", db='forge', charset="utf8")

html_parser = HTMLParser.HTMLParser()

try:
    source_plan_tuples = loadPlanData(conn_forge)
    format_plan_tuples = []

    counter = 0
    for plan in source_plan_tuples:
        format_plan = concate_format_plan(plan)
        format_plan_tuples.append(format_plan)
        counter += 1

        if counter % 100 == 0 and counter > 0:
            insert_n = insert_format_plan(tuple(format_plan_tuples))
            print "%s 条记录插入成功!" % insert_n
            format_plan_tuples = []

    print "%d 条记录插入成功!" % counter

finally:
    conn_forge.commit()
    conn_forge.close()
