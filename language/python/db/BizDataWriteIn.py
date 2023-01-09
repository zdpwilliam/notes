#!/usr/bin/python
import csv
import pymysql
import time


class BizData:
    def __init__(self, fields: []):
        self.traceId = fields[0]
        self.startTime = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(int(fields[1]) / 1000))
        self.productId = int(BizData.find_field("productId", fields))
        self.openid = BizData.find_field("openid", fields)
        self.tcode = BizData.find_field("tcode", fields)
        self.pid = BizData.find_field("pid", fields)
        self.vid = int(BizData.find_field("vid", fields))
        self.wid = int(BizData.find_field("wid", fields))
        self.cid = int(BizData.find_field("cid", fields))
        self.bosId = int(BizData.find_field("bosId", fields))
        self.merchantId = int(BizData.find_field("merchantId", fields))

    @staticmethod
    def find_field(field_name: str, fields: []):
        for field in fields:
            cur_field = str(field)
            match_name = "%s:" + field_name
            try:
                if cur_field.find(match_name) > 0:
                    return cur_field.replace(match_name, "")
            except:
                print('field_name --> %s,%s' % (field_name, fields))
        return "0"


bizDataList = []
csv_reader = csv.reader(open(
    "/Users/william/Spaces/gitlab/saas-cms-data-transfer/saas-cms-data-transfer-service/src/test/resources/traceBizData.csv"))
for line in csv_reader:
    bizDataList.append(BizData(line))

# 打开数据库连接
db = pymysql.connect(host='localhost', user='root', password='', database='local-my-rpt')
# 使用 cursor() 方法创建一个游标对象 cursor
cursor = db.cursor()
# SQL 插入语句
sql = "insert into biz_data_rpt (traceId, starTime, productId, openid, pid, tcode, vid, wid, merchantId, bosId, cid)" \
      " values ('%s', '%s', %s, '%s', %s, '%s', %s, %s, %s, %s, %s);"
try:
    for bizData in bizDataList:
        oneIn = sql % (bizData.traceId, bizData.startTime, bizData.productId, bizData.openid, bizData.pid,
                       bizData.tcode, bizData.vid, bizData.wid, bizData.merchantId, bizData.bosId, bizData.cid)
        # 执行sql语句
        cursor.execute(sql)
    # 提交到数据库执行
    db.commit()
except:
    # 如果发生错误则回滚
    db.rollback()

# 关闭数据库连接
db.close()
