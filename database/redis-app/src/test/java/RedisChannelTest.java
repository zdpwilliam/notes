import com.william.springdata.PubSubService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by william on 17-7-4.
 */
public class RedisChannelTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-data-redis-channel.xml");

        try {
            Thread.sleep(5000);
            System.out.println("top server listener started ...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PubSubService pubSubService = applicationContext.getBean(PubSubService.class);
        pubSubService.publish("user:topic", "你好的Miss wang!");
    }
}
