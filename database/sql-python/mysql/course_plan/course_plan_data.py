#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys,datetime,MySQLdb,HTMLParser
reload(sys)
sys.setdefaultencoding("UTF-8")


def loadPlanData(conn_forge):
    sql_forge = " SELECT id, cou_id, tea_id, stu_id, grade, les_subject, plan_mins," \
                "    expected_target, parent_subject, student_subject, created_at, updated_at" \
                " FROM courses_plan where id < 9552"
    cursor_forge = conn_forge.cursor()
    cursor_forge.execute(sql_forge)
    return cursor_forge.fetchall()

def matchGradeId(conn_tr, grade):
    sql_tr = "SELECT system_id FROM system_dict " \
             "WHERE group_code = 'GRADE' and request_value = '%s'" % grade
    cursor_tr = conn_tr.cursor()
    cursor_tr.execute(sql_tr)
    return cursor_tr.fetchall()[0][0]

def matchSubjectId(conn_tr, subject):
    sql_tr = "SELECT system_id FROM system_dict " \
             "WHERE group_code = 'SUBJECT' and request_value = '%s'" % subject
    cursor_tr = conn_tr.cursor()
    cursor_tr.execute(sql_tr)
    result_tuple = cursor_tr.fetchall()
    if len(result_tuple) < 1:
        return -1;
    else:
        return result_tuple[0][0]

def insert_format_plan(format_plan_tuples):
    conn_tr = MySQLdb.connect(host="",
                              user="forge", passwd="",
                              db="tr", charset="utf8")
    sql_tr = "INSERT INTO course_plan" \
             " (id, cou_id, tea_id, stu_id, grade_id, subject_id, course_hour, goal_suggestion," \
             " parent_suggestion, student_suggestion, created_at, updated_at)" \
             " VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
    cursor = conn_tr.cursor()
    insert_n = cursor.executemany(sql_tr, format_plan_tuples)
    conn_tr.commit()
    conn_tr.close()
    return insert_n

def concate_format_plan(plan, gradeId, subjectId):
    format_plan = []
    for index in range(len(plan)):
        if index == 4:
            format_plan.append(gradeId)
        elif index == 5:
            format_plan.append(subjectId)
        elif index == 7 or index == 8 or index == 9:
            source_str = plan[index]
            tmp_str = ""
            if source_str is not None:
                tmp_str = html_parser.unescape(source_str)
            str_len = len(tmp_str)
            if str_len > 3000:
                str_len = 2999
            else:
                str_len = (str_len - 1)
            format_plan.append(tmp_str[0:str_len])
        else:
            format_plan.append(plan[index])
    return tuple(format_plan)

html_parser = HTMLParser.HTMLParser()

conn_forge = MySQLdb.connect(host="", user="forge",
                             passwd="", db='forge', charset="utf8")
conn_tr = MySQLdb.connect(host="", user="forge",
                          passwd="", db="tr", charset="utf8")

try:
    source_plan_tuples = loadPlanData(conn_forge)
    format_plan_tuples = []

    counter = 0
    for plan in source_plan_tuples:
        grade = str(plan[4])
        subject = str(plan[5])
        if len(grade) == 0 or len(subject) == 0:
            continue
        else:
            gradeId = matchGradeId(conn_tr, grade)
            subjectId = matchSubjectId(conn_tr, subject)
            format_plan = concate_format_plan(plan, gradeId, subjectId)
            format_plan_tuples.append(format_plan)
            counter += 1

        if counter % 50 == 0 and counter > 0:
            insert_n = insert_format_plan(tuple(format_plan_tuples))
            print "%s 条记录插入成功!" % insert_n
            format_plan_tuples = []
        else:
            print "当前记录数: %d" % counter

    print "%d 条记录插入成功!" % counter

finally:
    conn_forge.commit()
    conn_forge.close()
    conn_tr.commit()
    conn_tr.close()

