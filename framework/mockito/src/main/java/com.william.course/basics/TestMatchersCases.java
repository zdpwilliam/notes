package com.william.course.basics;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @Package com.william.course.basics
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/2/9 17:17
 * @Version V1.0
 */
public class TestMatchersCases {

    public void testArgs() {
        ArrayList<String> mockedList = mock(ArrayList.class);
        //stubbing using built-in anyInt() argument matcher
        // 使用内置的anyInt()参数匹配器
        when(mockedList.get(anyInt())).thenReturn("element");

        //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
        // 使用自定义的参数匹配器( 在isValid()函数中返回你自己的匹配器实现 )
//      when(mockedList.contains(isValid())).thenReturn("");

        //following prints "element"
        // 输出element
        System.out.println(mockedList.get(999));
        //you can also verify using an argument matcher
        // 你也可以验证参数匹配器
        verify(mockedList).get(anyInt());
    }

    private String isValid() {
        return "valid";
    }

}
