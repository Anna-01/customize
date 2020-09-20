package com.xiaodai.customize;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaodai.customize.service.json.PersonService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Objects;

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


    @Test
     void testNo() {
        JSONObject jsonObject = new JSONObject(2);
        String brandName = null;
        jsonObject.put(brandName, null);
        String result = jsonObject.toJSONString();
        System.out.println("json结果" + result);
        JSONObject dataJson = JSONObject.parseObject(result);

        if(dataJson == null || dataJson.isEmpty()){
           System.out.println("为空");
        }
        dataJson.put("1", 3);
        System.out.println("json结果" + dataJson.toJSONString());

        String a =null;
        //System.out.println("a的值" + a.toString());
        System.out.println("==========");
        Object object = dataJson.get(null);
        System.out.println(object);
    }
}
