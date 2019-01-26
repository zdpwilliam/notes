#!/usr/bin/python
# -*- coding: UTF-8 -*-
import MySQLdb, xlrd


def loadDataFromXls(xls_path):
    items = []
    workbook = xlrd.open_workbook(xls_path)
    booksheet = workbook.sheet_by_index(0)
    for row in range(booksheet.nrows):
        if row > 1:
            item = []
            for col in range(booksheet.ncols - 3):
                year = 0
                if col == 0:
                    year = booksheet.cell(row, col).value
                if year == '':
                    break
                value = booksheet.cell(row, col).value
                if value == '':
                    value = 0.0
                item.append(value)
            if len(item) > 0:
                items.append(tuple(item))
    return tuple(items)


def insert_format_items(item_tuples):
    conn = MySQLdb.connect(host="rm-bp18xpd36jyck25n2uo.mysql.rds.aliyuncs.com",
                              user="root", passwd="Qujiatou888",
                              db="ljt_finance", charset="utf8")
    sql_tr = " INSERT INTO `t_bank_equity` (`year`,`quarter`,`stock_code`,`capital_stock`," \
             " `other_equity_instruments`,`preferred_stock`,`capital_reserve`,`treasury_stock`," \
             " `other_comprehensive_income`,`surplus_reserve`,`undistributed_profit`," \
             " `general_risk_prepare`,`other_reserves`,`foreign_currency`,`shareholders_equity_parent`," \
             " `shareholders_equity_minority`,`shareholders_equity_total`,`liabilities_and_equity_total`)" \
             " VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) "
    counter = 0
    cursor = conn.cursor()
    for item in item_tuples:
        counter += cursor.execute(sql_tr, item)
    conn.commit()
    conn.close()
    return counter


xls_path = 'qujiatou_finance_bank_equity.xlsx'
source_tuples = loadDataFromXls(xls_path)
if len(source_tuples) > 0:
    counter = insert_format_items(source_tuples)
    print "%d 条记录插入成功!" % counter
else:
    print "No anything to do !"

