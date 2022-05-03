package com.william.algorithm.tmp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Package com.william.algorithm.tmp
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/9/18 17:53
 * @Version V1.0
 */
public class ReviewMain {

    // 输入：计算 a+/-b=n
    // 返回计算结果： INT
    public static void main(String[] args) {
        //1. 表达式格式校验 - 空字符，头部/尾部字符串
//        calculate("");
//        calculate("-+12-3++");
        //2. 计算支持的计算范围：最大值，最小值
        calculate(String.valueOf(Integer.MAX_VALUE) + "+0");
        calculate(String.valueOf(Integer.MIN_VALUE) + "-0");
        //3. 正确的计算方式
        System.out.println(calculate("1+12"));
        System.out.println(calculate("1+12-3"));
        System.out.println(calculate("1-12+3-1+12"));
    }

    public static int calculate(String noSeq) {
        if(Objects.isNull(noSeq)) {
            return 0;
        }
        List<Seq> seqList = new ArrayList<>();
        StringBuilder tmpNo = new StringBuilder();
        for (int i = 0; i < noSeq.length(); i++) {
            char curNo = noSeq.charAt(i);
             if('+' == curNo || '-' == curNo) {
                 seqList.add(new Seq(tmpNo.toString(), true));
                 seqList.add(new Seq(String.valueOf(curNo), false));
                 tmpNo = new StringBuilder();
             } else {
                 tmpNo.append(curNo);
             }
        }
        seqList.add(new Seq(tmpNo.toString(), true));
        int sum = 0;
        int calFlag = 0; //1 + , 0 -1
        for (Seq seq : seqList) {
            if(seq.isNo) {
                if(calFlag == 1) {
                    sum = sum + Integer.valueOf(seq.value);
                } else if(calFlag == -1) {
                    sum = sum - Integer.valueOf(seq.value);
                }
            } else {
                if("+".equals(seq.value)) {
                    calFlag = 1;
                } else if ("-".equals(seq.value)) {
                    calFlag = -1;
                } else {
                    calFlag = 0;
                }
            }
        }
        return sum;
    }

    public static class Seq {
        String  value;
        boolean isNo;

        public Seq(String value, boolean isNo) {
            this.value = value;
            this.isNo = isNo;
        }
    }

}
