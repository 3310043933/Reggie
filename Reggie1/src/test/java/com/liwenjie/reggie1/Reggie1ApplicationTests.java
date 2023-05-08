package com.liwenjie.reggie1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class Reggie1ApplicationTests {

    @Test
    void contextLoads() {
        String a1 = "111,222,333,444";
        Arrays.stream(a1.split(",")).map(Integer::valueOf)
                .forEach(item -> System.out.println(item.getClass().getName()));
    }

}
