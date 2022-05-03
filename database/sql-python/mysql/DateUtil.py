__author__ = 'william.zhang'

import time, datetime

def datetostr(date):
    return str(date)[0:10]

def strtodatetime(datestr, format):
    return datetime.datetime.strptime(datestr, format)

def datediff(startDateStr, endDateStr):
    format = "%Y-%m-%d";
    bd = strtodatetime(startDateStr, format)
    ed = strtodatetime(endDateStr, format)
    oneday = datetime.timedelta(days=1)
    count = 0
    while bd != ed:
        ed = ed - oneday
        count += 1
    return count

def getDateRang(startDateStr, endDateStr):
    format = "%Y-%m-%d"
    bd = strtodatetime(startDateStr, format)
    ed = strtodatetime(endDateStr, format)
    oneday = datetime.timedelta(days=1)
    num = datediff(startDateStr, endDateStr) + 1
    li = []
    for i in range(0, num):
        li.append(datetostr(ed))
        ed = ed - oneday
    return li

def getNowTime():
    return time.strftime('%Y-%m-%d-%H-%M-%S', time.localtime(time.time()));

print strtodatetime("2017-11-19-23-59-59", "%Y-%m-%d-%H-%M-%S")