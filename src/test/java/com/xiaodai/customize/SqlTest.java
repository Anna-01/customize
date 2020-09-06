package com.xiaodai.customize;


import com.xiaodai.customize.service.PersonService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class SqlTest {
    Logger logger = LoggerFactory.getLogger(SqlTest.class);

    @Resource
    private PersonService personService;
    @Test
    void testAddPerson() {
        personService.addPerson();
    }
    @Test
    void testLog() {
        logger.info("日志测试");
    }
}
