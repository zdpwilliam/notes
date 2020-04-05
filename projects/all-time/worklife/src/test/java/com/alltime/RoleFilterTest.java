package com.alltime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Package com.alltime
 * @Description:
 * @Author deepen.zhang
 * @Date 2019-09-26 15:05
 * @Version V1.0
 */
public class RoleFilterTest {


    public static void main(String[] args) throws ParseException {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dft.parse(dft.format(new Date())).getTime());
        System.out.println(System.currentTimeMillis());
        System.out.println(dft.format(new Date(System.currentTimeMillis())));
        System.out.println(System.currentTimeMillis() + 24 * 3600 * 1000);
        System.out.println(dft.format(new Date(System.currentTimeMillis() + 24 * 3600 * 1000)));
        System.out.println(dft.format(new Date(System.currentTimeMillis() + 24 * 3600 * 1000 * 60L)));
        System.out.println(isEffectiveTime(String.valueOf(dft.parse("2019-12-27").getTime())));
    }

    private static boolean isEffectiveTime(String deliveryDate) {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        long nowInMilis = 0L;
        try {
            nowInMilis = dft.parse(dft.format(new Date())).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long deliveryTime = Long.valueOf(deliveryDate);
        return (nowInMilis <= deliveryTime) && (deliveryTime <= (nowInMilis + 60 * 24 * 3600 * 1000L));
    }
}
