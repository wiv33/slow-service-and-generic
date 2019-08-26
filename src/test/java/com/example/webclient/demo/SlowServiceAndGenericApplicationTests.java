package com.example.webclient.demo;

import org.junit.Test;
import org.junit.runners.Parameterized;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SlowServiceAndGenericApplicationTests {
    static class Sup<T> {
        T value;
    }
    @Test
    public void contextLoads() {
        Type t = (new Sup<List<Map<String, Set<String>>>>(){}).getClass().getGenericSuperclass();
        ParameterizedType ptype = (ParameterizedType) t;
        System.out.println("ptype = " + ptype.getActualTypeArguments()[0]);


    }

}
