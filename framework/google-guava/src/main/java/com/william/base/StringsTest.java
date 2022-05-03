package com.william.base;

import com.google.common.base.Strings;

/**
 * 
 * @author william
 *
 */
public class StringsTest {

	public static void main(String[] args) {
		testStrings();
	}

	public static void testStrings() {
		String testStr = "mm:%s:mm";
		System.out.println(String.format(testStr, Strings.repeat("*", 200)));
		System.out.println(Strings.nullToEmpty(null) + "is null");
        System.out.println(Strings.emptyToNull(""));
        System.out.println(Strings.isNullOrEmpty(null));
		System.out.println(Strings.commonPrefix("aaaa", "aaabbb"));
        System.out.println(Strings.commonSuffix("ccbb", "aaabbb"));
        System.out.println(Strings.padStart("aaaaa", 10, 'b'));
        System.out.println(Strings.padEnd("aaaaaaacccc", 30, 'b'));
	}
}
