__author__ = 'william.zhang'

import random, time, DateUtil, MySqlUtil, MySQLdb

def prepareForAppSummaryDaily():
    date_rang = DateUtil.getDateRang("2016-03-01", "2016-04-01")
    now = DateUtil.getNowTime()
    listParams = []
    for num in date_rang:
        tmp = (random.randint(2000, 10000), random.randint(2000, 10000), num, now, now)
        listParams.append(tmp)
    return tuple(listParams)

def prepareForAppStatDaily() :
    appKeys = MySqlUtil.db_qa_sql_select("SELECT app_key FROM app", "open_api")
    date_rang = DateUtil.getDateRang("2016-03-01", "2016-04-01")
    listParams = []
    for num in date_rang:
        for key in appKeys:
            tmp = (key, random.randint(500, 1000), random.randint(50000, 100000), random.randint(8000, 15000), random.randint(60, 1800),
                   random.randint(3000, 6000), random.randint(60, 1800), round(random.random(), 2), round(random.random(), 2),
                   round(random.random(), 2), num, DateUtil.getNowTime(), DateUtil.getNowTime())
            listParams.append(tmp)
    return tuple(listParams)

def prepareForAppChannelKpi():
    listParams = []
    channelType = 0
    while(channelType < 7):
        channelType = channelType + 1
        temp = (channelType, 1000000, 10000*random.randint(10, 40), DateUtil.getNowTime(), DateUtil.getNowTime())
        listParams.append(temp)
    return tuple(listParams)

# sql = "SELECT * FROM app_summary_daily"
# print MySqlUtil.db_qa_sql_select(sql, "openapi_stat")

conn = MySQLdb.connect(host="192.168.1.161", user="naliworld", passwd="password!", db="open_api", charset="utf8")
cursor = conn.cursor()
ids = ""
for index in range(119, 125):
    if(index < 124) :
        ids += str(index) + ","
    else:
        ids += str(index)
print ids
sql = "UPDATE app SET app_channel_type = 0 WHERE id IN (" + ids + ")"
print sql

n = cursor.execute(sql)
cursor.close()
conn.commit()
conn.close()

# sql = "insert into app_summary_daily(active_developer_count , active_app_count, stat_date, created_at, updated_at)" \
#       " values(%s, %s, %s, %s, %s) "
# # MySqlUtil.db_local_sql_multi_add(sql, prepareForAppSummaryDaily())
# MySqlUtil.db_qa_sql_multi_add(sql, prepareForAppSummaryDaily(), "openapi_stat")


# sql = "insert into app_channel_kpi(app_channel_type, base, kpi, created_at, updated_at) VALUES (%s, %s, %s, %s, %s)"
# # MySqlUtil.db_local_sql_multi_add(sql, prepareForAppChannelKpi())
# MySqlUtil.db_qa_sql_multi_add(sql, prepareForAppChannelKpi(), "openapi_stat")


# sql = "insert into app_stat_daily(app_key, dau, total_api_call, total_play_count, avg_play_duration, total_live_play_count," \
#       "avg_live_play_duration, next_day_retention, weekly_retention, monthly_retention, stat_date, created_at, updated_at)" \
#       " values(%s, %s, %s, %s, %s,   %s, %s, %s, %s, %s,    %s, %s, %s) "
# # MySqlUtil.db_local_sql_multi_add(sql, prepareForAppStatDaily())
# MySqlUtil.db_qa_sql_multi_add(sql, prepareForAppStatDaily(), "openapi_stat")