package currencyop;

import com.google.common.collect.Sets;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by william on 17-8-2.
 */
public class ZSetSingleKeyRWTest {
    private static final String SORTED_KEY = "lesson_zset_key";

    private RedisConnectionFactory connFactory;
    private final AtomicInteger conter = new AtomicInteger();

    public void setConnFactory(RedisConnectionFactory connFactory) {
        this.connFactory = connFactory;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-data-redis-trans.xml");
        ZSetSingleKeyRWTest test = new ZSetSingleKeyRWTest();
        test.setConnFactory(applicationContext.getBean(RedisConnectionFactory.class));

        new Thread(() -> test.write()).start();
        new Thread(() -> test.read()).start();
    }

    public void write() {
        new Thread(() -> {
            while (true) {
                RedisConnection conn = connFactory.getConnection();
                conn.zAdd(SORTED_KEY.getBytes(),
                        Long.valueOf(System.currentTimeMillis()).doubleValue(),
                        String.format(conter.incrementAndGet() + "_%s", genLessonUid()).getBytes());
                conn.close();

                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void read() {
        new Thread(() -> {
            while (true) {
                RedisConnection conn = connFactory.getConnection();
                Long totalCount = conn.zCard(SORTED_KEY.getBytes());
                System.out.println("read card: -------- " + totalCount);
//                Set<byte[]> uids = conn.zRange(SORTED_KEY.getBytes(), 0, -1);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Set<String> uids = Sets.newTreeSet();
                ScanOptions scanOptions = ScanOptions.scanOptions().build();
                Cursor<RedisZSetCommands.Tuple> tupleCursor = conn.zScan(SORTED_KEY.getBytes(), scanOptions);
                while (tupleCursor.hasNext()) {
                    RedisZSetCommands.Tuple tuple = tupleCursor.next();
                    uids.add(new String(tuple.getValue()));
                }
                conn.close();
                System.out.println("read size: -------- " + uids.size());
                System.out.print("read content: ------- ");
                uids.stream().forEach(uidByte -> System.out.println(new String(uidByte)));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void update() {
        new Thread(() -> {
            while (true) {
                RedisConnection conn = connFactory.getConnection();
                conn.zScan(SORTED_KEY.getBytes(), null);
                conn.close();
                System.out.print("read content: ------- ");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static String genLessonUid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
