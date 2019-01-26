-- mysql的事务相关配置
--1.查看当前会话隔离级别
 select @@tx_isolation;
--2.查看系统当前隔离级别
select @@global.tx_isolation;
--3.设置当前会话隔离级别
 set session transaction isolation level repeatable read;
--4.设置系统当前隔离级别
 set global transaction isolation level repeatable read;
--5.命令行，开始事务时
 set autocommit=off 或者 start transaction;
--6.查看mysql当前事务设置
 show variables like 'tx_%';

--1、read uncommitted数据测试 --允许脏读取，但不允许更新丢失
--更改事务T1、T2的隔离级别均为：read uncommitted
--事务T1：
start transaction;
update goods set count = count - 1 where id = 0 and (count - 1) > 0;
--事务T2：
set autocommit = off;
start transaction;
select * from goods;-- 发现数据已被更改
--事务T1：
rollback;
--事务T2：
select * from goods;-- 发现数据已被还原

--2、read committed数据测试 --允许不可重复读取，但不允许脏读取
--更改事务T1、T2的隔离级别均为：read committed
--测试以上情况不会再发生，即不会出现脏读
--事务T1：
start transaction;
update goods set count = count - 1 where id = 0 and (count - 1) > 0;
commit;
--事务T2：
set autocommit = off;
start transaction;
select * from goods;-- 第1次查看数据 count = 598
--事务T1：
start transaction;
update goods set count = count - 1 where id = 0 and (count - 1) > 0;
commit;
--事务T2：
select * from goods;-- 第2次查看数据 count = 597, 同一T2的事务，读取了两个不同的count

--3、read committed数据测试 --禁止不可重复读取和脏读取，但是有时可能出现幻影数据，innodb中使用了措施来防止幻读
--更改事务T1、T2的隔离级别均为：repeatable read
--测试不会再出现脏读
--事务T1：
start transaction;
select * from goods;  -- 4条记录 count分别为：596 600 600 600
--事务T2：
start transaction;
select * from goods;  -- 4条记录 count分别为：596 600 600 600
--事务T1：
start transaction;
update goods set count = 596 where id = 1;
select * from goods;  -- 4条记录count分别为：596 596 600 600
commit;
--事务T2：
select * from goods;  -- 4条记录count分别为：596 600 600 600
update goods set count = count - 1 where count = 596;
--返回结果： Query OK, 2 rows affected (9.49 sec)         这时T2中出现了T1更改后的id=1的count值，出现幻读现象
--                     Rows matched: 2  Changed: 2  Warnings: 0
select * from goods; --再次查询一下发现 4条记录count分别为：595 595 600 600

--4、serializable;数据测试 --提供严格的事务隔离，锁表，读取时会自动设置读锁，然后所有会话只能进行读操作；写时自动设置写锁，然后所有会话只能等待
--测试不会再出现脏读
--事务T1：
start transaction;
select * from goods;  -- 4条记录 count分别为：596 596 600 600
--事务T2：
start transaction;
select * from goods;  -- 4条记录 count分别为：596 596 600 600
update goods set count = 595 where id = 0;
select * from goods;  -- 4条记录 count分别为：595 596 600 600
--事务T1：
update goods set count = count - 1 where count = 595;
--返回结果：ERROR 1205 (HY000): Lock wait timeout exceeded; try restarting transaction
