package com.william.course.basics;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.junit.Assert.assertEquals;

/**
 * @Package com.william.course.basics
 * @Description:
 *      *@Mock:
 *      *@Spy:
 *      *@Captor:
 *      *@InjectMocks:
 * @Author deepen.zhang
 * @Date 2021/2/7 17:38
 * @Version V1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class Lesson_1_Mockito_Annotations {

    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenNotUseMockAnnotation_thenCorrect() {
        List mockList = Mockito.mock(ArrayList.class);

        mockList.add("one");
        Mockito.verify(mockList).add("one");
//        Mockito.verify(mockList).clear();
        assertEquals(0, mockList.size());

        Mockito.when(mockList.size()).thenReturn(100);
        assertEquals(100, mockList.size());
    }


    @Mock
    List<String> mockedList;
    @Test
    public void whenUseMockAnnotation_thenMockIsInjected() {
        mockedList.add("one");
        Mockito.verify(mockedList).add("one");
        assertEquals(0, mockedList.size());

        Mockito.when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());
    }



    @Test
    public void whenNotUseSpyAnnotation_thenCorrect() {
        List<String> spyList = Mockito.spy(new ArrayList<String>());
        spyList.add("one");
        spyList.add("two");
        Mockito.verify(spyList).add("one");
        Mockito.verify(spyList).add("two");

        assertEquals(2, spyList.size());
        Mockito.doReturn(100).when(spyList).size();
        assertEquals(100, spyList.size());
    }


    @Spy
    List<String> spiedList = new ArrayList<String>();
    @Test
    public void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
        spiedList.add("one");
        spiedList.add("two");

        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");

        assertEquals(2, spiedList.size());

        Mockito.doReturn(100).when(spiedList).size();
        assertEquals(100, spiedList.size());
    }


    @Test
    public void whenNotUseCaptorAnnotation_thenCorrect() {
        List mockList = Mockito.mock(List.class);
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);

        mockList.add("one");
        Mockito.verify(mockList).add(arg.capture());

        assertEquals("one", arg.getValue());
    }


    @Mock
    List mockedList2;
    @Captor
    ArgumentCaptor argCaptor;
    @Test
    public void whenUseCaptorAnnotation_thenTheSam() {
        mockedList2.add("one");
        Mockito.verify(mockedList2).add(argCaptor.capture());

        assertEquals("one", argCaptor.getValue());
    }


    static class MyDictionary {
        Map<String, String> wordMap;
        public MyDictionary() {
            wordMap = new HashMap<String, String>();
        }
        public void add(final String word, final String meaning) {
            wordMap.put(word, meaning);
        }
        public String getMeaning(final String word) {
            return wordMap.get(word);
        }
    }
    @Mock
    Map<String, String> wordMap;
    @InjectMocks
    MyDictionary dic = new MyDictionary();
    @Test
    public void whenUseInjectMocksAnnotation_thenCorrect() {
        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

        assertEquals("aMeaning", dic.getMeaning("aWord"));
    }


    @Mock
    Map<String, String> wordMap2;
    @Spy
    MyDictionary spyDic = new MyDictionary();

    @Test
    public void whenUseInjectMocksAnnotation_thenCorrect2() {
        Mockito.when(wordMap2.get("aWord")).thenReturn("aMeaning");

        assertEquals("aMeaning", spyDic.getMeaning("aWord"));
    }


    @Mock
    List<String> mockedNPEList;

    @Test(expected = NullPointerException.class)
    public void whenMockitoAnnotationsUninitialized_thenNPEThrown() {
        Mockito.when(mockedNPEList.size()).thenReturn(1);
        Mockito.when(mockedNPEList.get(0).toString()).thenReturn("1");
    }

}
