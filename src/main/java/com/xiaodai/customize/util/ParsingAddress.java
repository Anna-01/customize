package com.xiaodai.customize.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.relational.core.dialect.HsqlDbDialect;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 解析省市区 字典
 *
 * @author My
 */
public class ParsingAddress {

    public static void main(String[] args) throws Exception {

        String testAddress = "{\n" +
                "  \"86\": {\n" +
                "    \"110000\": \"北京市\",\n" +
                "    \"120000\": \"天津市\",\n" +
                "    \"130000\": \"河北省\",\n" +
                "    \"140000\": \"山西省\",\n" +
                "    \"150000\": \"内蒙古自治区\",\n" +
                "    \"210000\": \"辽宁省\",\n" +
                "    \"220000\": \"吉林省\",\n" +
                "    \"230000\": \"黑龙江省\",\n" +
                "    \"310000\": \"上海市\",\n" +
                "    \"320000\": \"江苏省\",\n" +
                "    \"330000\": \"浙江省\",\n" +
                "    \"340000\": \"安徽省\",\n" +
                "    \"350000\": \"福建省\",\n" +
                "    \"360000\": \"江西省\",\n" +
                "    \"370000\": \"山东省\",\n" +
                "    \"410000\": \"河南省\",\n" +
                "    \"420000\": \"湖北省\",\n" +
                "    \"430000\": \"湖南省\",\n" +
                "    \"440000\": \"广东省\",\n" +
                "    \"450000\": \"广西壮族自治区\",\n" +
                "    \"460000\": \"海南省\",\n" +
                "    \"500000\": \"重庆市\",\n" +
                "    \"510000\": \"四川省\",\n" +
                "    \"520000\": \"贵州省\",\n" +
                "    \"530000\": \"云南省\",\n" +
                "    \"540000\": \"西藏自治区\",\n" +
                "    \"610000\": \"陕西省\",\n" +
                "    \"620000\": \"甘肃省\",\n" +
                "    \"630000\": \"青海省\",\n" +
                "    \"640000\": \"宁夏回族自治区\",\n" +
                "    \"650000\": \"新疆维吾尔自治区\",\n" +
                "    \"710000\": \"台湾省\",\n" +
                "    \"810000\": \"香港特别行政区\",\n" +
                "    \"820000\": \"澳门特别行政区\"\n" +
                "  },\n" +
                "  \"110000\": {\n" +
                "    \"110100\": \"市辖区\"\n" +
                "  },\n" +
                "   \"110100\": {\n" +
                "    \"110101\": \"东城区\",\n" +
                "    \"110102\": \"西城区\",\n" +
                "    \"110105\": \"朝阳区\",\n" +
                "    \"110106\": \"丰台区\",\n" +
                "    \"110107\": \"石景山区\",\n" +
                "    \"110108\": \"海淀区\",\n" +
                "    \"110109\": \"门头沟区\",\n" +
                "    \"110111\": \"房山区\",\n" +
                "    \"110112\": \"通州区\",\n" +
                "    \"110113\": \"顺义区\",\n" +
                "    \"110114\": \"昌平区\",\n" +
                "    \"110115\": \"大兴区\",\n" +
                "    \"110116\": \"怀柔区\",\n" +
                "    \"110117\": \"平谷区\",\n" +
                "    \"110118\": \"密云区\",\n" +
                "    \"110119\": \"延庆区\"\n" +
                "  }\n" +
                "}";

        testAddress = ReadTxt();

        ReadJson(testAddress);


    }

    /**
     * @param jsonStr
     * @throws Exception
     */
    public static void ReadJson(String jsonStr) throws Exception {
        //如果没有则要建立一个新的output.txt文件
        File writeName = new File("C:\\Users\\My\\Desktop\\city\\province.txt");
        writeName.createNewFile(); // 创建新文件
        BufferedWriter out = new BufferedWriter(new FileWriter(writeName));

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String chinaCode = "86";
        //所有省
        JSONObject provinceArray = jsonObject.getJSONObject(chinaCode);

       // getKeyValues(provinceArray.)

        for (String key : provinceArray.keySet()) {
            //得到省的名字 如: 北京市,天津市，河北省
            String province = provinceArray.get(key).toString();
           // System.out.println("省:" + province);
            //输出省的sql
            String type = "省";
            getProvince(key, province, chinaCode, type , out);
            //获取省下面的区  市辖区
            JSONObject cityArrays = jsonObject.getJSONObject(key);
            if (cityArrays == null) {
                System.out.println("没有区" + key + province);
                continue;
            }

            for (String cKey : cityArrays.keySet()) {
                //得到市区的名字
                String city = cityArrays.get(cKey).toString();
                getProvince(cKey, city, key, "市", out);
               // System.out.println("市：" + city);
                JSONObject areaArray = jsonObject.getJSONObject(cKey);

                if (areaArray == null) {
                    System.out.println("没有" + cKey + city);
                    continue;
                }
                for (String aKey : areaArray.keySet()) {
                    //得到区的名字
                    String area = areaArray.get(aKey).toString();
                    getProvince(aKey, area, cKey, "区", out);
                   // System.out.println( province + city + area);
                    //生成sql

                }
            }
        }

        //释放资源
        out.flush();
        out.close();



       /*
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            //获取省
            JSONArray jsonArray1 = jsonObject.get("86");
            System.out.println(province);
            //执行插入数据库***
            JSONArray jsonArray2 = JSONArray.parseArray(jsonObject.get("city"));
            for (int j = 0; j < jsonArray2.size(); j++) {
                //市
                JSONObject jsonObject2 = jsonArray2.getJSONObject(j);
                String city = jsonObject2.get("name").toString();
                System.out.println(city);
                //执行插入数据库***
                JSONArray jsonArray3 = (JSONArray) jsonObject2.get("area");
                for (int k = 0; k < jsonArray3.size(); k++) {
                    //区
                    String area = jsonArray3.get(k).toString();
                    System.out.println(area);
                    //执行插入数据库***
                }
            }
        }*/
    }

    /**
     * 读取全国省市区json文件
     *
     * @return String
     */
    public static String ReadTxt() {
        String str = "";
        String line = "";
        String pathname = "C:\\Users\\My\\Desktop\\city\\省市区.txt";
        //通过将给定路径名字符串转换为抽象路径名来创建一个新 File 实例。
        File file = new File(pathname);
        try {
            //创建一个使用默认字符集的 InputStreamReader。
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            //创建一个使用默认大小输入缓冲区的缓冲字符输入流。
            BufferedReader br = new BufferedReader(reader);
            while ((line = br.readLine()) != null) {
                //一次读入一行数据
                str += line;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     *
     */
    public static HashMap<String, String> getKeyValues(JSONObject jsonObject) {
        Iterator iter = jsonObject.entrySet().iterator();
        HashMap<String, String> map = new HashMap<>(jsonObject.size() + 10);
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            map.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return map;
    }

    /**
     * 获取所有的省
     * @param key
     * @param province
     * @param chinaCode
     */
    public static void getProvince(String key, String province, String chinaCode, String type, BufferedWriter out) {

        try {
            String provinceSql = "INSERT INTO city_area (CITY_CODE , CITY_NAME, CITY_TYPE , PARENT_CODE, CREATE_TIME, UPDATE_TIME, DEL_FLAG ) VALUES (%s, %s, %s, %s, NOW(), NOW(), 0);";
            provinceSql = String.format(provinceSql, getSqlString(key), getSqlString(province), getSqlString(type), getSqlString(chinaCode));
            out.write(provinceSql);
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拼sql查询语句参数
     * @return
     */
    public static String getSqlString(String result) {
       return  "'" + result + "'";
    }
}
