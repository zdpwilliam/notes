#!/usr/bin/python
# -*- coding: UTF-8 -*-
import datetime, MySQLdb, pandas, random


def strtodatetime(datestr, format):
    return datetime.datetime.strptime(datestr, format)


def loadPlanData(start, end):
    report = []
    for dateIndex in pandas.date_range(start, end):
        date_time = dateIndex.strftime('%Y-%m-%d %H:%M:%S')
        date_index = dateIndex.strftime('%Y-%m-%d')
        regular_les_count = random.randint(1, 2000)
        hw_all_count = random.randint(1, regular_les_count)
        hw_stu_finish_on_time_count = random.randint(1, hw_all_count)
        hw_use_standard_hw_count = random.randint(1, hw_all_count)
        regular_fml_cs_count = random.randint(1, regular_les_count/4)
        regular_usr_cs_count = random.randint(1, regular_les_count/4)
        regular_other_cs_count = random.randint(1, regular_les_count/4)
        regular_explained_cs_count = random.randint(1, regular_les_count/4)
        test_les_count = random.randint(1, 1000)
        test_fml_cs_count = random.randint(1, test_les_count/4)
        test_usr_cs_count = random.randint(1, test_les_count/4)
        test_other_cs_count = random.randint(1, test_les_count/4)
        test_explained_cs_count = random.randint(1, test_les_count/4)
        daily_max_les_count = (regular_les_count + test_les_count) / 10
        test_les_stu_count = random.randint(1, 1000)
        reg_les_stu_count = random.randint(1, 1000)
        full_teacher_count = random.randint(1, 1000)
        part_teacher_count = random.randint(1, 1000)
        qb_cut_audited_count = random.randint(1, 500)
        qb_entry_audited_count = random.randint(1, 500)
        qb_tag_audited_count = random.randint(1, 500)
        qb_storaged_count = random.randint(1, 500)
        report.append((0, 0, 0, date_time, None, date_time, None, 0, '',
                       hw_all_count, hw_stu_finish_on_time_count, hw_use_standard_hw_count, regular_les_count,
                       regular_fml_cs_count, regular_usr_cs_count, regular_other_cs_count, regular_explained_cs_count,
                       test_les_count, test_fml_cs_count, test_usr_cs_count, test_other_cs_count,
                       test_explained_cs_count, daily_max_les_count, test_les_stu_count, reg_les_stu_count,
                       full_teacher_count, part_teacher_count, qb_cut_audited_count, qb_entry_audited_count,
                       qb_tag_audited_count, qb_storaged_count, date_index))

    return tuple(report)


def insert_format_plan(format_plan_tuples):
    conn_tr = MySQLdb.connect(host="",
                              user="forge", passwd="",
                              db="tr", charset="utf8")
    sql_tr = " INSERT INTO `data_report` (`sort`, `state`, `deleted`, `created_time`,  `created_user`, " \
    " `updated_time`, `updated_user`,`versions`,`remarks`,`hw_all_count`,`hw_stu_finish_on_time_count`," \
    " `hw_use_standard_hw_count`, `regular_les_count`,`regular_formal_cs_count`,`regular_user_cs_count`," \
    " `regular_other_cs_count`,`regular_explained_cs_count`, `test_les_count`,`test_formal_cs_count`," \
    " `test_user_cs_count`,`test_other_cs_count`,`test_explained_cs_count`, `daily_max_les_count`,`test_les_stu_count`," \
    " `reg_les_stu_count`,`full_teacher_count`,`part_teacher_count`,`qb_cut_audited_count`, `qb_entry_audited_count`," \
    " `qb_tag_audited_count`,`qb_storaged_count`,`data_date`) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, " \
    " %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) "

    cursor = conn_tr.cursor()
    insert_n = cursor.execute(sql_tr, format_plan_tuples)
    conn_tr.commit()
    conn_tr.close()
    return insert_n


start = strtodatetime('2017-11-2', "%Y-%m-%d")
end = strtodatetime('2018-2-2', "%Y-%m-%d")
# start = strtodatetime('2018-2-5', "%Y-%m-%d")
# end = strtodatetime('2018-2-5', "%Y-%m-%d")

counter = 0
source_plan_tuples = loadPlanData(start, end)
for plan_tuple in source_plan_tuples:
    counter += insert_format_plan(plan_tuple)

print "%d 条记录插入成功!" % counter
