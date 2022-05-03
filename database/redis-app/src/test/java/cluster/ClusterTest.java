package cluster;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by william on 17-7-5.
 */
public class ClusterTest {

    private ApplicationContext context;

    private RedisTemplate redisTemplate;
    final String key = "key7";

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("spring-redis-sentinel.xml");
        redisTemplate = context.getBean("redisTemplate", RedisTemplate.class);
    }

    @Test
    public void test1() {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.set(key.getBytes(), (System.currentTimeMillis() + "").getBytes());
                return 1L;
            }
        });
    }

    @Test
    public void test2() {
        Object execute = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.get(key.getBytes());
            }
        });
        System.out.println(new String((byte[]) execute));
    }
}
