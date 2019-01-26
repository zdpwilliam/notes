package currencyop;

import com.william.currencyop.RedisTransService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by william on 17-8-2.
 */
public class RedisTransTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-data-redis-trans.xml");
        RedisTransService transService = applicationContext.getBean(RedisTransService.class);
//        normalOperation(transService);
        mutiThreadCurrencyTest(transService);
    }

    public static void mutiThreadCurrencyTest(RedisTransService transService) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        new Thread(() -> {
            while (true) {
                Future<Boolean> future = executorService.submit(new AddCallable(transService));
                try {
                    Thread.sleep(2000);
                    System.out.println("AddCallable ---- " + future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Future<List<String>> future = executorService.submit(new SearchCallable(transService, "will"));
                try {
                    Thread.sleep(3000);
                    System.out.println("SearchCallable --- " + future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                String uid = transService.getRandomUid();
                Future<Boolean> future = null;
                if(uid != null) {
                    future = executorService.submit(new updateCallable(transService, uid));
                }
                try {
                    Thread.sleep(4000);
                    if(future != null) {
                        System.out.println("updateCallable ---- " + future.get());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                String uid = transService.getRandomUid();
                Future<Boolean> future = null;
                if(uid != null) {
                    future = executorService.submit(new DeleteCallable(transService, uid));
                }
                try {
                    Thread.sleep(5000);
                    if(future != null) {
                        System.out.println("DeleteCallable ---- " + future.get());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//      executorService.shutdown();
    }

    public static void normalOperation(RedisTransService transService) {
        String lessonUid = "singleuid10011";
        String username = "william";
        Boolean addResult = transService.addNormalByUidName(lessonUid, username);
        System.out.println("addResult --------- " + addResult);
        List<String> searchResult = transService.searchBy(username);
        System.out.println("searchResult ------ " + searchResult);
        Boolean updateResult = transService.updateByUid(lessonUid, DeviceStatus.ABONORMAL.getValue());
        System.out.println("updateResult ------ " + updateResult);
        System.out.println("getRandomUid ------ " + transService.getRandomUid());
        Boolean deleteResult = transService.deleteByUid(lessonUid);
        System.out.println("deleteResult ------ " + deleteResult);
    }

    static class AddCallable implements Callable<Boolean> {
        private RedisTransService transService;

        public AddCallable(RedisTransService transService) {
            this.transService = transService;
        }

        @Override
        public Boolean call() {
            String lessonUid = genLessonUid();
            String name = "william_" + lessonUid;
            boolean result = transService.addNormalByUidName(lessonUid, name);
            return Boolean.valueOf(result);
        }
    }

    static class updateCallable implements Callable<Boolean> {
        private RedisTransService transService;
        private String lessonUid;

        public updateCallable(RedisTransService transService, String lessonUid) {
            this.transService = transService;
            this.lessonUid = lessonUid;
        }

        @Override
        public Boolean call() {
            boolean result = transService.updateByUid(lessonUid, randomStatus().getValue());
            return Boolean.valueOf(result);
        }
    }

    static class SearchCallable implements Callable<List<String>> {

        private RedisTransService transService;
        private String name;

        public SearchCallable(RedisTransService transService, String name) {
            this.transService = transService;
            this.name = name;
        }

        @Override
        public List<String> call() {
            return transService.searchBy(name);
        }
    }

    static class DeleteCallable implements Callable<Boolean> {

        private RedisTransService transService;
        private String lessonUid;

        public DeleteCallable(RedisTransService transService, String lessonUid) {
            this.transService = transService;
            this.lessonUid = lessonUid;
        }

        @Override
        public Boolean call() {
            return transService.deleteByUid(lessonUid);
        }
    }

    private static DeviceStatus randomStatus() {
        Random random = new Random();
        int randomInt = random.nextInt(Integer.MAX_VALUE);
        int statusValue = Math.abs(randomInt % DeviceStatus.values().length);
        return DeviceStatus.statusOf(statusValue);
    }

    private static String genLessonUid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    enum DeviceStatus {
        IS_OK(0),
        ABONORMAL(1),
        PROCESSING(2);

        int value;

        DeviceStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static DeviceStatus statusOf(int value) {
            DeviceStatus target = null;
            switch (value) {
                case 0:
                    return DeviceStatus.IS_OK;
                case 1:
                    return DeviceStatus.ABONORMAL;
                case 2:
                    return DeviceStatus.PROCESSING;
                default:
                    target = null;
            }
            return target;
        }
    }
}
