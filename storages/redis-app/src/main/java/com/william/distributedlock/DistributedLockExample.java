package com.william.distributedlock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Created by zdpwilliam on 17-2-10.
 */
public class DistributedLockExample {

    public static void main(String[] args) {
        //demo_3_redlock();

        int totalThreadCount = 10;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(totalThreadCount);
        final CountDownLatch countDownLatch = new CountDownLatch(totalThreadCount);
        for (int i = 0; i < totalThreadCount; i++) {
            new Thread(new Runnable() {
                long threadId = System.currentTimeMillis();
                public void run() {
                    System.out.println("cyclicBarrier start ---------- !");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println("doBuiness start ---------- !");
                    doBuinessWithMyLock(threadId);
                    System.out.println("doBuiness end ---------- !");
                    countDownLatch.countDown();
                }
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(redisson != null) {
            redisson.shutdown();
        }
    }

    static final String lockName = "locak:william";
    static RedissonClient redisson = null;

    static {
        // 1. Create config object
        Config config = new Config();
        config.useSingleServer().setAddress("localhost:6379").setConnectionPoolSize(50);
        redisson = Redisson.create(config);
    }

    private static void doBuiness(final long threadId) {
        // 3. Get object you need
        // RMap<String, String> map = redisson.getMap("myMap");
        RLock lock = redisson.getLock(lockName);
        try {
            if (lock.tryLock(1000L, 500L, TimeUnit.MILLISECONDS)) {
                System.out.println(threadId + " doBuiness start --------- !");
                for (int i = 0; i < 5; i++) {
                    System.out.println(threadId + " run no." + i);
                }
                System.out.println(threadId + " doBuiness end ----------  !");
            }
            else {
                System.out.println(threadId + " doBuiness not get the lock ----------  !");
            }
        }
        catch (InterruptedException e) {
            if (lock.isLocked()) {
                lock.unlock();
            }
            e.printStackTrace();
        }
        finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
            System.out.println("lock.unlock success unlocked --------!");
        }
    }

    private static final String USER_NAME = "william";
    private static final String LOCK_KEY = "lock:william";

    /**
     * 一般事务流程 demo
     */
    public static void demo_1_transaction() {
        Jedis jedis = RedisUtil.getJedis();
        Transaction tx = jedis.multi();
        Response<Long> result = null;
        do {
            result = tx.setnx(LOCK_KEY, "In the Spring, become a fullow!");
        }
        while (result == null);
        tx.exec();
        try {
            if(tx != null) {
                tx.close();
            }
        }catch (Exception e) {
        }
        jedis.close();
        RedisUtil.returnResource(jedis);
        System.out.println("result ----------- :" + result.get());
    }

    public static void demo_2_distributed_lock() {
        Jedis jedis = RedisUtil.getJedis();
        Map<String, String> map = new HashMap();
        map.put("name","william");
        map.put("age","27");
        map.put("tid", String.valueOf(0));
        jedis.hmset("user", map);
        //RedisUtil.returnResource(jedis);

        mutiThreadRun();
    }

    private static void doBuinessWithMyLock(final long threadId) {
        String threadContent = Thread.currentThread().getName() + Thread.currentThread().getId();
        Jedis jedis = RedisUtil.getJedis();
        Long result = null;
        String locked = null;

        try {
            //1 获取锁
            locked = acquireLockWithTimeout(LOCK_KEY, 10000, 5000);
            if (locked != null) {
                System.out.println(threadId + " doBuiness start --------- !");
                for (int i = 0; i < 5; i++) {
                    System.out.println(threadId + " run no." + i);
                }
                System.out.println(threadId + " doBuiness end ----------  !");
            }
            else {
                System.out.println(threadId + " doBuiness not get the lock ----------  !");
            }
        }
        finally {
            if (locked != null) {
                boolean release_result = release(USER_NAME, locked);
                System.out.println("release_lock success unlocked --------!");
            }
        }
    }

    private static void doBuiness() {
        String threadContent = Thread.currentThread().getName() + Thread.currentThread().getId();
        Jedis jedis = RedisUtil.getJedis();
        Long result = null;

        //1 获取锁
        String locked = acquire_lock(jedis, USER_NAME, 5000);

        //2 其它业务操作
        if(locked != null) {
            System.out.println(threadContent + "get locked !");
            result = jedis.hset("user", "tid", threadContent);
        }
        System.out.println(threadContent + " jedis.hset result : " + result);

        //3 释放锁
        boolean release_result = release_lock(jedis, USER_NAME, locked);
        System.out.println("threadContent --------- finish: " + threadContent + " ---- release_result：" + release_result);
//                  RedisUtil.returnResource(jedis);
    }



    private static void mutiThreadRun() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
//                    doBuiness();
                    demo_3_redlock();
                }
            }).start();
        }
    }



    /**
     *  redlock实现
     */
    public static void demo_3_redlock() {
        String lockName = "locak:william";
        // 1. Create config object
        Config config = new Config();
        config.useSingleServer().setAddress("localhost:6379").setConnectionPoolSize(50);

        // 2. Create Redisson instance
        RedissonClient redisson = Redisson.create(config);

        // 3. Get object you need
        // RMap<String, String> map = redisson.getMap("myMap");
        RLock lock = redisson.getLock(lockName);
        try {
            if(lock.tryLock()) {
                System.out.println("lock.tryLock --------!");

                lock.lock();
                System.out.println("lock.locked --------!");

                System.out.println("this thread id is " + Thread.currentThread().getId());
            }
        }
        finally {
            lock.unlock();
            System.out.println("lock.unlock success unlocked --------!");
        }
        redisson.shutdown();
    }

    /**
     * 获取分布式锁
     * @param jedis
     * @param lockName
     * @param acquire_timeout
     * @return
     */
    public static String acquire_lock(Jedis jedis, String lockName, int acquire_timeout) {
        String identifier = random32(new Date().toString());
        System.out.println(Thread.currentThread().getId() + " identifier -------------: " + identifier);

        long end = new Date().getTime() + acquire_timeout;
        while (new Date().getTime() < end) {
            Transaction tx = jedis.multi();
            Long result = jedis.setnx(("locak:" + lockName), identifier);
            if(result != null && result > 0) {
                return identifier;
            }
            try {
                tx.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 释放分布式锁
     * @param jedis
     * @param lockName
     * @param identifier
     * @return
     */
    public static boolean release_lock(Jedis jedis, String lockName, String identifier) {
        String fullLockName = "locak:" + lockName;

        Pipeline pipeline = null;
        String dbFullLockValue = null;
        while (true) {
            dbFullLockValue = jedis.get(fullLockName);
            pipeline = jedis.pipelined();

            try {
                pipeline.watch(fullLockName);
                if(identifier.equals(dbFullLockValue)) {
                    pipeline.multi();
                    pipeline.del(fullLockName);
                    pipeline.exec();
                    pipeline.close();
                    return true;
                }
                break;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        RedisUtil.returnResource(jedis);
        return false;
    }


    /* 带有超时特性的分布式锁 */
    /**
     * 获取分布式锁
     *
     * @param lockName
     *            竞争获取锁key
     * @param acquireTimeoutInMS
     *            获取锁超时时间
     * @param lockTimeoutInMS
     *            锁的超时时间
     * @return 获取锁标识
     */
    public static String acquireLockWithTimeout(String lockName, long acquireTimeoutInMS, long lockTimeoutInMS) {
        Jedis conn = null;
        boolean broken = false;
        String retIdentifier = null;
        try {
            conn = RedisUtil.getJedis();
            String identifier = UUID.randomUUID().toString();
            String lockKey = "locak:" + lockName;
            int lockExpire = (int) (lockTimeoutInMS / 1000);

            long end = System.currentTimeMillis() + acquireTimeoutInMS;
            while (System.currentTimeMillis() < end) {
                if (conn.setnx(lockKey, identifier) == 1) {
                    conn.expire(lockKey, lockExpire);
                    retIdentifier = identifier;
                }
                if (conn.ttl(lockKey) == -1) {
                    conn.expire(lockKey, lockExpire);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (JedisException je) {
            if (conn != null) {
                broken = true;
                RedisUtil.returnResource(conn);
            }
        } finally {
            if (conn != null && !broken) {
                RedisUtil.returnResource(conn);
            }
        }
        return retIdentifier;
    }

    /**
     *  释放分布式锁
     * @param lockName
     * @param identifier
     * @return
     */
    public static boolean release(String lockName, String identifier) {
        String fullLockName = "locak:" + lockName;
        boolean broken = false;
        boolean retFlag = false;
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            while (true) {
                jedis.watch(fullLockName);
                if (identifier.equals(jedis.get(fullLockName))) {
                    Transaction trans = jedis.multi();
                    trans.del(fullLockName);
                    List<Object> results = trans.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                jedis.unwatch();
                break;
            }

        } catch (JedisException je) {
            if (jedis != null) {
                broken = true;
                RedisUtil.returnResource(jedis);
            }
        } finally {
            if (jedis != null && !broken) {
                RedisUtil.returnResource(jedis);
            }
        }
        return retFlag;
    }

    private static String random32(String random) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
        }
        digest.update(random.getBytes());
        byte hash[]  = digest.digest();
        StringBuffer result = new StringBuffer(hash.length * 2);
        for (int i = 0; i < hash.length; i++) {
            if (((int) hash[i] & 0xff) < 0x10) {
                result.append("0");
            }
            result.append(Long.toString((int) hash[i] & 0xff, 16));
        }
        return result.toString();
    }
}
