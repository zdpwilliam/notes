package com.william;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by william on 17-6-8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-rest-template.xml" })
public class RestTemplateTest {

    private static final String url = "http://127.0.0.1:8282/services/";

    @Test
    public void test() {
        RestTemplate restTemplate = new RestTemplate();
        List subjectList = restTemplate.postForObject(url + "lessonService/getLearningSubjects",
                177152, List.class);
        System.out.println("subjectList ---------- " + subjectList);
    }
}
