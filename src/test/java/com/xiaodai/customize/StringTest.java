package com.xiaodai.customize;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.xiaodai.customize.util.SqlUtil.*;

@SpringBootTest
public class StringTest {

    @Test
    void testString() {
        //String org =  ' "5581465","{\"weibo_jieqian\":\"2.003t1iJHzs9WME559651acd20Licvv\"}"
      /*  JSONObject jsonObject= new JSONObject();
        jsonObject.put("weibo", "");
        String object = jsonObject.get("ss").toString();
        System.out.println("teststring 判断：" + object);*/


        String str = "\"1172865\",\"{\\\"fafa_credit\\\":\\\"2.00xt3HPGNJJSLB2515e2eaa6f7Y7aB\\\",\\\"weibo_jieqian\\\":\\\"2.00xt3HPGzs9WME6bd20dc5b8ZWhcjD\\\"}\"";

        String p1 = str.substring( 0, str.indexOf(","));
        String p2 = str.substring(str.indexOf(",")+1, str.length());

        String pre;
        String post;
        //去双引号
        pre = stringReplace(p1);

        //去两端双引号

        post = transSpace(p2);

        // 去除 \
        post = stringGang(post);

        //转json
        post = transJson(post ,pre);

        System.out.println("最终结果" +post);
    }
   @Test
    void  testS() {
        String json = "{\"weibo_jieqian\": } ";
      // String result  = sqlutil.transJson(json);

       //System.out.println("结果:" + result);
       JSONObject jsonObject = new JSONObject();
       jsonObject.put(null, "n");
       System.out.println("存入后json结果" + jsonObject.toJSONString());
   }
}
