package com.xiaodai.customize;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaodai.customize.service.json.PersonService;
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

    /**
     * 增加
     */
    @Test
    void testAddPerson() {
        personService.addPerson();
    }
    @Test
    void testDel() {
        personService.delPerson();
    }

    @Test
    void testFind() {
        personService.findById();
    }

    @Test
    void testUpdate() {
        personService.updatePerson();
    }
    @Test
    void testLog() {
        logger.info("日志测试");
    }


    @Test
    void testJson() {
        String json = "{\"name\":\"lijiaxing\",\"age\":\"11\"}";
        JSONObject wbTokenBo = JSONObject.parseObject(json);
        wbTokenBo.put("name", "覆盖");
        logger.info("结果" +  wbTokenBo.toJSONString());
    }
    @Test
    void testJsonUrl() {
        String res = "{     \"error\":0,     \"msg\":\"操作成功\",     \"data\":{   \"url\":\"123\",     } } ";
        JSONObject json = JSON.parseObject(res);
        Integer error= (Integer)json.get("error");
        if (error != 0) {
            logger.info("error结果不为0"  + error);
        } else {
            logger.info("error结果"  + error);
        }
    }
}
