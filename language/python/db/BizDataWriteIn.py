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

    def to_tuple(self):
        return (self.traceId, self.startTime, self.productId, self.openid, self.pid,
                self.tcode, self.vid, self.wid, self.cid, self.bosId, self.merchantId)

    @staticmethod
    def find_field(field_name: str, fields: []):
        for field in fields:
            cur_field = str(field)
            match_name = "%s:" % field_name
            if cur_field.find(match_name) < 0:
                continue
            else:
                return cur_field.replace(match_name, "")
        return "0"


bizDataList = []
csv_reader = csv.reader(open(
    "/Users/william/Spaces/gitlab/saas-cms-data-transfer/saas-cms-data-transfer-service/src/test/resources/traceBizData.csv"))
for line in csv_reader:
    bizDataList.append(BizData(line).to_tuple())

# 打开数据库连接
db = pymysql.connect(host='localhost', user='root', password='', database='local-my-rpt', charset='utf8')

#事务开启
db.begin()
# 使用 cursor() 方法创建一个游标对象 cursor
cursor = db.cursor()
# SQL 插入语句
sql = "insert into biz_data_rpt (traceId, starTime, productId, openid, pid, tcode, vid, wid, merchantId, bosId, cid)" \
      " values (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);"
try:
    # 执行sql语句
    cursor.executemany(sql, bizDataList)
    # 提交到数据库执行
    db.commit()
except Exception as e:
    # 如果发生错误则回滚
    print("error --> ", e)
    db.rollback()

# 关闭数据库连接
db.close()
