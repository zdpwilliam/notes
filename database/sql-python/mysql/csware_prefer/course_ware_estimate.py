#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys,datetime,MySQLdb
reload(sys)
sys.setdefaultencoding("UTF-8")


def strtodatetime(datestr, format):
    return datetime.datetime.strptime(datestr, format)


def print_subject_cs_ware_map(csware_prefer, start, end, file):
    subject_map = {}
    sql_forge = "SELECT ls_tmp.les_subject, s_tmp.stu_grade FROM lessons ls_tmp" \
                " left join students s_tmp on ls_tmp.stu_id = s_tmp.id" \
                " where ls_tmp.id =  "
    sql_tr = "SELECT lesson_id FROM `tr`.`lesson_test_estimate` WHERE " \
             "created_at between '%s' AND '%s' AND csware_prefer = %s" % (start, end, csware_prefer)

    conn_forge = MySQLdb.connect(host="", user="zmforge_sa",
                                 passwd="", db='forge', charset="utf8")
    conn_tr = MySQLdb.connect(host="", user="tr_sa",
                              passwd="", db="tr", charset="utf8")

    try:
        cursor_forge = conn_forge.cursor()
        cursor_tr = conn_tr.cursor()
        n_tr = cursor_tr.execute(sql_tr)
        print >> file, "%scount: %s " % (csware_map[csware_prefer], str(n_tr))

        lesson_id_set = cursor_tr.fetchall()
        for lessonId in lesson_id_set:
            cursor_forge.execute(sql_forge + str(lessonId[0]))
            subject_grade_tuple = cursor_forge.fetchall()
            if len(subject_grade_tuple) < 1:
                print subject_grade_tuple
                continue
            if len(subject_grade_tuple[0]) < 2:
                print subject_grade_tuple[0]
                continue
            grade_str = str(subject_grade_tuple[0][1])
            grade_subject = "无|%s"
            if grade_str.find("小") > -1:
                grade_subject = "小学%s" % subject_grade_tuple[0][0]
            elif grade_str.find("初") > -1:
                grade_subject = "初中%s" % subject_grade_tuple[0][0]
            elif grade_str.find("高") > -1:
                grade_subject = "高中%s" % subject_grade_tuple[0][0]
            else:
                grade_subject = grade_subject % subject_grade_tuple[0][0]
            subject_count = subject_map.get(grade_subject)
            if subject_count is not None:
                subject_count = int(subject_count) + 1
                subject_map[grade_subject] = subject_count
            else:
                subject_map[grade_subject] = 1
    except MySQLdb.Error, e:
        print "MySQLdb Operational Error! %s" % str(e)

    finally:
        try:
            cursor_forge.close()
            conn_forge.commit()
            conn_forge.close()
        except IOError:
            print "conn_forge close error!"
        try:
            cursor_tr.close()
            conn_tr.commit()
            conn_tr.close()
        except IOError:
            print "conn_tr close error!"
    return subject_map


now = strtodatetime("2018-01-21-23-59-59", "%Y-%m-%d-%H-%M-%S")
a_week_ago = now - datetime.timedelta(7)
start = a_week_ago.strftime("%Y-%m-%d %H:%M:%S")
end = now.strftime("%Y-%m-%d %H:%M:%S")

file_path = "/home/admin/tmp/cs_ware_test.txt"
file = open(file_path, "w+")
print >> file, "\n %s 至 %s 的课件统计信息: \n" % (start, end)

csware_map = {"1": "掌门正式课件", "2": "掌门其他课件资料", "3": "自制课件", "4": "讲试卷、错题或其他"}
for key in csware_map.keys():
    for subject_str, count in print_subject_cs_ware_map(key, start, end, file).items():
        subject_count_str = "%s %s" % (subject_str, str(count))
        print >> file, subject_count_str

try:
    file.close()
except IOError:
    print "file close error!"

print "\n %s 至 %s 的课件信息统计完成! ---- 输出路径: %s" % (start, end, file_path)

# 需要更改的变量:
# file_path: 输出文件路径
# forge数据库连接参数: host,user,passwd
# tr数据库连接参数:    host,user,passwd