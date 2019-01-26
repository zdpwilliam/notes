#!/usr/bin/python
# -*- coding: UTF-8 -*-
import MySQLdb
import redis
import datetime
import calendar


class RecordItem:
    def __init__(self, dataDate, year, month, role,
                 dimension, rank, value, score, createdAt):
        self.dataDate = dataDate
        self.year = year
        self.month = month
        self.role = role
        self.dimension = dimension
        self.rank = rank
        self.value = value
        self.score = score
        self.createdAt = createdAt

    def __str__(self):
        to_string = "('%s', %d, %d, '%s', %d, %d, %s, %f, '%s') " % (self.dataDate,
                self.year, self.month, self.role, self.dimension, self.rank,
                self.value, self.score, self.createdAt)
        return to_string


def str_to_datetime(datestr, format):
    return datetime.datetime.strptime(datestr, format)


def get_between_month(start, end):
    date_list = []
    begin_date = datetime.datetime.strptime(start, "%Y-%m-%d")
    end_date = datetime.datetime.strptime(end, "%Y-%m-%d")
    while begin_date <= end_date:
        date_str = begin_date.strftime("%Y-%m")
        date_list.append(date_str)
        begin_date = add_months(begin_date, 1)
    return tuple(date_list)


def add_months(dt, months):
    month = dt.month - 1 + months
    year = dt.year + month / 12
    month = month % 12 + 1
    day = min(dt.day, calendar.monthrange(year, month)[1])
    return dt.replace(year=year, month=month, day=day)


def load_base_data(start_str, end_str):
    conn_forge = MySQLdb.connect(host="",
                                 user="forge", passwd="",
                                 db="forge", charset="utf8")
    sql_forge = "select tea_id, date_format(les_started_at, '%Y-%m') as dateMonth, " \
                " sum(les_last_mins)/60 as course_hour from lessons " \
                " where les_started_at between '" + start_str + "' and '" + end_str + "'" \
                " and deleted_at is null " \
                " and client_state > 1 " \
                " group by tea_id, date_format(les_started_at, '%Y-%m')"
    try:
        cursor_forge = conn_forge.cursor()
        cursor_forge.execute(sql_forge)
        return cursor_forge.fetchall()
    except MySQLdb.Error, e:
        print "MySQLdb Operational Error! %s" % str(e)
    finally:
        conn_forge.commit()
        conn_forge.close()
    return tuple([])


def fill_to_redis(tuples, store_key):
    if len(tuples) == 0:
        return -1
    redis_conn = redis.Redis(host='', port=6379, password='')
    count = 0
    for group in tuples:
        redis_conn.zadd(store_key, group[0], group[2])
        count += 1
    return count


def load_from_redis(store_key):
    redis_conn = redis.Redis(host='', port=6379, password='')
    tuples = redis_conn.zrevrange(store_key, 0, -1, withscores=True)
    redis_conn.delete(store_key)
    return tuples


def format_items(tuples, date):
    items = []
    data_date = date.strftime("%Y-%m-%d")
    year = date.year
    month = date.month
    role = 'teacher'
    dimension = 2
    createdAt = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    rank = 1
    last_value = 0
    for line in tuples:
        item = RecordItem(data_date, year, month, role, dimension,
                          rank, line[0], line[1], createdAt)
        items.append(item)
        if last_value != line[0]:
            rank += 1
        last_value = line[0]
    return tuple(items)


def insert_data(format_tuples):
    conn_forge = MySQLdb.connect(host="",
                                 user="forge", passwd="",
                                 db="forge", charset="utf8")
    sql_forge = " insert into keshi_rank_record(`data_date`, `year`, `month`, `role`, `dimension`," \
                " `rank`, `value`, `score`, `created_at`) VALUES "
    insert_count = 0
    try:
        cursor_forge = conn_forge.cursor()
        for format_value in format_tuples:
            sql_tmp = sql_forge + str(format_value)
            insert_count += cursor_forge.execute(sql_tmp)
        return insert_count
    except MySQLdb.Error, e:
        print "MySQLdb Operational Error! %s" % str(e)
        return -1
    finally:
        conn_forge.commit()
        conn_forge.close()


redis_month_key = "course-hour-rank-key:teacher-month:tmp"
startstr = '2017-01-01'
endstr = '2018-02-28'
months = get_between_month(startstr, endstr)
counter = 0
for month_str in months:
    print "开始处理 %s 月数据： ..." % month_str
    month = str_to_datetime(month_str, '%Y-%m')
    firstDayWeekDay, monthRange = calendar.monthrange(month.year, month.month)
    firstDate = datetime.date(year=month.year, month=month.month, day=1)
    lastDate = datetime.date(year=month.year, month=month.month, day=monthRange)
    month_first = firstDate.strftime('%Y-%m-%d') + ' 00:00:00'
    month_last = lastDate.strftime('%Y-%m-%d') + ' 23:59:59'
    data = load_base_data(month_first, month_last)
    print "%d 条记录加载成功!" % len(data)
    cached_count = fill_to_redis(data, redis_month_key)
    print "%d 条记录添加到redis!" % cached_count
    redis_data = load_from_redis(redis_month_key)
    print "%d 条记录从redis中获取成功!" % len(redis_data)
    items = format_items(redis_data, firstDate)
    print "%d 条items格式成功!" % len(items)
    count = insert_data(items)
    print "%d 条记录插入成功!" % count
    print "%s 月数据处理成功。" % month_str
    counter += count

print "总计处理 %d 条记录!" % counter