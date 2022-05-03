import com.william.springdata.Example;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zdpwilliam on 17-2-27.
 */
public class SpringDataRedisExample {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-data-redis-example.xml");
        Example example = applicationContext.getBean(Example.class);
        try {
            example.addLink("10011", new URL("http://www.baidu.com"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
