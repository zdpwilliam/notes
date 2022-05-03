/**
 * Created by zdpwilliam on 2016-04-05.
 */

public class PageTest {

    public static void main(String[] args) {
        Page<Integer> page = new Page<Integer>(64, 4, 8);
        System.out.println("----------------" + page);
    }

}
