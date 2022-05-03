package com.william.algorithm.coding;

import java.util.*;

/**
 * @Package com.william.algorithm.coding
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/9/23 16:44
 * @Version V1.0
 */
public class Demo {
    /**
     * Description:
     *
     * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
     *
     * 示例:
     *
     * 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出:
     * [
     *   ["ate","eat","tea"],
     *   ["nat","tan"],
     *   ["bat"]
     * ]
     * 说明：
     *
     * 所有输入均为小写字母。
     * 不考虑答案输出的顺序。
     */
    public static void main(String[] args) {
        String[] strAry = new String[] { "eat", "tea", "tan", "ate", "nat", "bat" };
        String[][] result = printOutSameCombine(strAry);
        for (int i = 0; i < result.length; i++) {
            System.out.println(Arrays.toString(result[i]));
        }
    }

    private static String[][] printOutSameCombine(String[] strAry) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strAry) {
            String combine = sortAscChar(str);
            List<String> strList = map.get(combine);
            if(Objects.isNull(strList)) {
                strList = new ArrayList<>();
                strList.add(str);
                map.put(combine, strList);
            } else {
                strList.add(str);
            }
        }
        String[][] result = new String[map.size()][];
        int pos = 0;
        Iterator<Map.Entry<String, List<String>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            result[pos] = iterator.next().getValue().toArray(new String[0]);
            pos ++;
        }
        return result;
    }

    private static String sortAscChar(String str) {
        Set<Character> sortChars = new TreeSet<>();
        for (int i = 0; i < str.length(); i++) {
            sortChars.add(str.charAt(i));
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : sortChars) {
            sb.append(c);
        }
        return sb.toString();
    }

}
