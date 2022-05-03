##### 1、 redis集群
[twemproxy-Redis集群管理方案](http://wiki.jikexueyuan.com/project/redis/cluster-up.html)

*项目内是 1主1从1哨兵模式的配置（使用redis的sentinel）*

##### 2、 redis aof和rdb数据恢复(简书)
[极客学院redis-aof和rdb数据恢复](http://wiki.jikexueyuan.com/project/redis/data-migration.html)

##### 3、 redis 数据分片(shard)
**1) 一致性Hash算法（consistent hashing）**
[博客](http://yywang.info/2017/04/15/hash/)
关键点:  a. 对key使用的hash算法
        b. hash后，确定虚拟节点和物理几点的映射关系
        
**2) Redis集群的哈希槽（Hash Slot）**
   Redis集群没有使用一致性hash，而是引入了哈希槽（Hash Slot）的概念，Redis集群一共有16384个哈希槽，
每个key通过CRC16校验后对16384取模来决定对应哪个槽。

   HASH_SLOT = CRC16(key) mod 16384

   每个主节点都负责处理 16384 个哈希槽的其中一部分，由于Redis 集群的key被分割为 16384 个slot， 所以集群的
最大节点数量也是 16384 个。推荐的最大节点数量为1000个左右。
    
比如当前集群有3个节点,那么：
   节点 A 包含 0 到 5500号哈希槽。
   节点 B 包含5501 到 11000 号哈希槽。
   节点 C 包含11001 到 16384号哈希槽。
   这种结构很容易添加或者删除节点。比如如果我想新添加个节点D，我需要从节点 A, B, C中得部分槽到D上。如果我像移除节点A,
需要将A中得槽移到B和C节点上,然后将没有任何槽的A节点从集群中移除即可。由于从一个节点将哈希槽移动到另 一个节点并不会停止
服务,所以无论添加删除或者改变某个节点的哈希槽的数量都不会造成集群不可用的状态。
说明：
   所有的哈希槽必须配置在集群中的某一个节点上。
   节点和哈希槽之间的对应关系在搭建集群时配置，集群使用中也支持动态迁移。

** 3) Redis 集群的主从复制拓扑结构
   单M-S结构   双M-S结构

** 4) Redis 一致性保证
Redis并不能保证数据的强一致性，这意味这在实际中集群在特定的条件下可能会丢失写操作。

> http://yywang.info 博客[微服务、业务建模、性能优化]