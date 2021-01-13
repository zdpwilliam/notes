package com.william.algorithm.strongPasswordChecker;

/**
 * @Package com.william.algorithm.strongPasswordChecker
 * @Description:
 * @Author deepen.zhang
 * @Date 2020-04-05 18:09
 * @Version V1.0
 */
public class Solution {
//
//    int strongPasswordChecker(String s) {
//        boolean mislchar, misdigit, misuchar;
//        mislchar = misdigit = misuchar = true;
//
//        char[] chars = s.toCharArray();
//        for (char c : chars) {
//            if (isdigit(c))
//                misdigit = false;
//            else if (islower(c))
//                mislchar = false;
//            else if (isupper(c))
//                misuchar = false;
//        }
//
//        int done = 0, dtwo = 0, replace = 0;
//        int missnum = mislchar + misuchar + misdigit;
//
//        for (int i = 2; i < chars.length; i++) {
//            if (chars[i] == chars[i - 1] && chars[i - 1] == chars[i - 2]) {
//                int len = 3;
//                while (i + 1 < chars.length && chars[i + 1] == chars[i]) {
//                    i++; len++;
//                }
//                if (len % 3 == 0)
//                    done++;
//                else if (len % 3 == 1)
//                    dtwo++;
//                replace += len / 3;
//            }
//        }
//
//        if (chars.length < 6)
//            return max(6 - chars.length, missnum);
//        else if (chars.length <= 20)
//            return max(replace, missnum);
//        else {
//            int del = chars.length - 20;
//            replace -= min(del, done);
//            if (del - done > 0)
//                replace -= min((del - done) / 2, dtwo);
//            if (del - done - 2 * dtwo > 0)
//                replace -= (del - done - 2 * dtwo) / 3;
//            return del + max(replace, missnum);
//        }
//    }

    public static void main(String[] args) {

    }
}
