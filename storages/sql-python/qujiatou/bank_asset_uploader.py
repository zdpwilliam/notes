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
            for col in range(booksheet.ncols - 2):
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


def insert_format_item(item_tuple):
    conn_tr = MySQLdb.connect(host="rm-bp18xpd36jyck25n2uo.mysql.rds.aliyuncs.com",
                              user="root", passwd="Qujiatou888",
                              db="ljt_finance", charset="utf8")
    sql_tr = " INSERT INTO `t_bank_asset` (`year`,`quarter`,`stock_code`,`cash_centralbank`," \
             " `interbank_deposit`,`precious_metals`,`interbank_lending`,`trading_financial_assets`," \
             " `derivative_financial_assets`,`resell_financial_assets`,`interest_receivable`," \
             " `offer_loans_and_advances`,`cansell_financial_assets`,`hold_to_maturity_investment`," \
             " `accounts_receivable_invest`,`longterm_receivable`,`longterm_equity_investment`," \
             " `real_estate_investment`,`fixed_assets`,`construction_project`,`intangible_assets`," \
             " `goodwill`,`deferred_tax_assets`,`others_assets`,`assets_total`)" \
             " VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s," \
             " %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) "
    cursor = conn_tr.cursor()
    insert_n = cursor.execute(sql_tr, item_tuple)
    conn_tr.commit()
    conn_tr.close()
    return insert_n


def insert_format_items(item_tuples):
    conn = MySQLdb.connect(host="rm-bp18xpd36jyck25n2uo.mysql.rds.aliyuncs.com",
                              user="root", passwd="Qujiatou888",
                              db="ljt_finance", charset="utf8")
    sql_tr = " INSERT INTO `t_bank_asset` (`year`,`quarter`,`stock_code`,`cash_centralbank`," \
             " `interbank_deposit`,`precious_metals`,`interbank_lending`,`trading_financial_assets`," \
             " `derivative_financial_assets`,`resell_financial_assets`,`interest_receivable`," \
             " `offer_loans_and_advances`,`cansell_financial_assets`,`hold_to_maturity_investment`," \
             " `accounts_receivable_invest`,`longterm_receivable`,`longterm_equity_investment`," \
             " `real_estate_investment`,`fixed_assets`,`construction_project`,`intangible_assets`," \
             " `goodwill`,`deferred_tax_assets`,`others_assets`,`assets_total`)" \
             " VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s," \
             " %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) "
    counter = 0
    cursor = conn.cursor()
    for item in item_tuples:
        counter += cursor.execute(sql_tr, item)
    conn.commit()
    conn.close()
    return counter


xls_path = 'qujiatou_finance_bank_asset.xlsx'
source_tuples = loadDataFromXls(xls_path)
if len(source_tuples) > 0:
    counter = 0
    for item_tuple in source_tuples:
        # print(item_tuple)
        counter += insert_format_item(item_tuple)
    print "%d 条记录插入成功!" % counter
else:
    print "No anything to do !"

