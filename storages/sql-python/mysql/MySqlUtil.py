__author__ = 'william.zhang'

import MySQLdb

def db_local_sql_add(sql, data):
    conn = MySQLdb.connect(host="localhost", user="root", passwd="123456", db="openapi_stat", charset="utf8")
    cursor = conn.cursor()
    n = cursor.execute(sql, data)
    print sql, n
    cursor.close()
    conn.commit()
    conn.close()

def db_local_sql_multi_add(sql, datas):
    conn = MySQLdb.connect(host="localhost", user="root", passwd="123456", db="openapi_stat", charset="utf8")
    cursor = conn.cursor()
    n = cursor.executemany(sql, tuple(datas))
    print sql, n
    cursor.close()
    conn.commit()
    conn.close()


def db_local_sql_select(sql):
    conn = MySQLdb.connect(host="localhost", user="root", passwd="123456", db="openapi_stat", charset="utf8")
    cursor = conn.cursor()

    n = cursor.execute(sql)
    results = []
    for row in cursor.fetchall():
        results.append(row)

    cursor.close()
    conn.commit()
    conn.close()

    return tuple(results)

def db_qa_sql_add(sql, data, dbName):
    conn = MySQLdb.connect(host="192.168.1.161", user="naliworld", passwd="password!", db=dbName, charset="utf8")
    cursor = conn.cursor()
    n = cursor.execute(sql, data)
    print sql, n
    cursor.close()
    conn.commit()
    conn.close()

def db_qa_sql_multi_add(sql, datas, dbName):
    conn = MySQLdb.connect(host="192.168.1.161", user="naliworld", passwd="password!", db=dbName, charset="utf8")
    cursor = conn.cursor()
    n = cursor.executemany(sql, tuple(datas))
    print sql, n
    cursor.close()
    conn.commit()
    conn.close()

def db_qa_sql_select(sql, dbName):
    conn = MySQLdb.connect(host="192.168.1.161", user="naliworld", passwd="password!", db=dbName, charset="utf8")
    cursor = conn.cursor()
    n = cursor.execute(sql)
    results = []
    for row in cursor.fetchall():
        for r in row:
            results.append(r)
    cursor.close()
    conn.commit()
    conn.close()
    return results

def db_qa_sql_multi_update(sql, datas, dbName):
    conn = MySQLdb.connect(host="192.168.1.161", user="naliworld", passwd="password!", db=dbName, charset="utf8")
    cursor = conn.cursor()
    n = cursor.executemany(sql, tuple(datas))
    print sql, n
    cursor.close()
    conn.commit()
    conn.close()