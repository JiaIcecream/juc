package com.liang.concurrencypractice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class ConcurrencyPracticeApplicationTests {

    @Test
    void contextLoads() {
        List<String> keywords = null;
        List<String> keys = new ArrayList<>();
        HashMap<String,String> templateMap = new HashMap<String, String>() {{
            put("register", "register/用户批量导入模版.xlsx");
            put("qaTemplate", "qaTemplate/QA问答模版.xlsx");
        }};

        templateMap.put("register","aaa");

        System.out.println(templateMap.get("register"));
    }

}
