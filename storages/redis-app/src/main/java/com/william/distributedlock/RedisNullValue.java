package com.william.distributedlock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by zdpwilliam on 17-3-13.
 */
public class RedisNullValue {

    public static final String STUDENT_USED_HOURS_MOUNTH_RANK = "student_used_hours_mounth_rank";
    public static final int MONTH_SECONDS = 30 * 24 * 3600;

    public static void main(String[] args) {
        for (int i = 100001; i < 100050; i++) {
            System.out.println("percent ------ :" + getRankPercentRate(String.valueOf(i), randomScore()));
        }
    }

    public static double randomScore() {
        Random random = new Random();
        return Integer.valueOf(random.nextInt(100)).doubleValue();
    }

    public static double getRankPercentRate(String userId, double Score) {
        double rateResult;
        Jedis redisConn = RedisUtil.getJedis();
        redisConn.select(1);
        if(redisConn.exists(STUDENT_USED_HOURS_MOUNTH_RANK)) {
            rateResult = caculateUserRankRate(redisConn, Score, userId);
        } else {
            rateResult = caculateUserRankRate(redisConn, Score, userId);
            redisConn.expire(STUDENT_USED_HOURS_MOUNTH_RANK, MONTH_SECONDS);
        }
        RedisUtil.returnResource(redisConn);
        return rateResult;
    }

    /**
     *  计算学生的 超过比例 已乘100
     * @param redisConn
     * @param score     学生本月已用课时
     * @param userId    学生的 stuId
     * @return
     */
    public static double caculateUserRankRate(Jedis redisConn, double score, String userId) {
        if(userId == null || userId.trim().length() == 0) {
            return 0D;
        }
        Pipeline pipeline = redisConn.pipelined();
        pipeline.zadd(STUDENT_USED_HOURS_MOUNTH_RANK, score, userId);
        pipeline.zcard(STUDENT_USED_HOURS_MOUNTH_RANK);
        pipeline.zrank(STUDENT_USED_HOURS_MOUNTH_RANK, userId);
        List<Object> valueList = pipeline.syncAndReturnAll();
        long sum = Long.valueOf(String.valueOf(valueList.get(1))).longValue();
        long rank = Long.valueOf(String.valueOf(valueList.get(2))).longValue();
        return caculateRankRate(rank, sum);
    }

    /**
     *  计算排名比例（保留了 1位小数）
     * @param rank 排名
     * @param sum  总数
     * @return
     */
    private static double caculateRankRate(long rank, long sum) {
        BigDecimal divisor = new BigDecimal(sum);
        return new BigDecimal(100 * (sum - rank))
                .divide(divisor, 1, RoundingMode.HALF_UP).doubleValue();
    }

    public static void removeKeys() {
        Jedis jedis = RedisUtil.getJedis();
        jedis.select(1);
        Set<String> keys = jedis.keys("wo-group:*");
        if(keys != null && keys.size() > 0) {
            Iterator<String> keysIterator = keys.iterator();
            while (keysIterator.hasNext()) {
                String key = keysIterator.next();
                System.out.println("key " + key + " has been deleted");
                jedis.del(key);
            }
        }
        jedis.close();
    }
}
