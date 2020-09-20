package com.xiaodai.customize.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SqlUtil {
    public static  int i= 0;
    public static void main(String args[]) {
        try {
            //写文件
            File writename = new File("C:\\Users\\My\\Desktop\\result.txt"); //如果没有则要建立一个新的output.txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));

            List<File> list = getFile(new File("C:\\Users\\My\\Desktop\\fix\\"));
            for (int i=0; i<list.size(); i++ ) {
                System.out.println("第"+ (i+1) +"个" + list.get(i).getName());
                //对于每个文件进行按行读取操作
                redaByLine(list.get(i), out);
            }
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void redaByLine(File filename, BufferedWriter out) {
        int i =0;
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            while((line = br.readLine())!=null){
                //stringReplace(line)
                String pre;
                String post;
                //按逗号拆分
                //"5186902","{\"weibo_jieqian\":\"2.00U1q3dGzs9WME2c7176c8640vN6Nu\"}"
                String p1 = line.substring( 0, line.indexOf(","));
                String p2 = line.substring(line.indexOf(",")+1, line.length());
                //去双引号
                pre = stringReplace(p1);
                //去两端双引号
                post = transSpace(p2);
                // 去除 \
                post = stringGang(post);
                //转json
                post = transJson(post ,pre);
                //null 時 為null
                if (post == null) {
                    post = null;
                } else {
                    //token 加上双引号
                    post = "'" + post + "'";
                }
                //当post 为null 时 设置 为nulll

                //sql 固定形式
                String sql =  "update user_info set WB_TOKEN = %s , UPDATE_TIME = now() where id = %s ;";
                //替换
                String result = String.format(sql, post,  pre);
                System.out.println(result);
                out.write(result);
                i++;
                out.newLine();
            }
            br.close();
            System.out.println("文件读取完毕 读取" + i );
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( e +  "出现问题行数" + i);
        }
    }

    public static String transJson(String post, String pre) {
        try {
            JSONObject jsonObject = JSON.parseObject(post);
            if (jsonObject == null || jsonObject.isEmpty()) {
                return null;
            }
            Object object = jsonObject.get("weibo_jieqian");

            if (!Objects.isNull(object)) {
                return object.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("异常post" + post + "id " + pre) ;
        }
        return null;
    }


    /**
     * 替换前后空格
     * @return
     */

        //去除字符串前后的双引号
        public static String transSpace(String fdId) {
            if(fdId.indexOf("\"")==0){
                //去掉第一个 "
                fdId = fdId.substring(1,fdId.length());
            }
            if(fdId.lastIndexOf("\"")==(fdId.length()-1)){
            } fdId = fdId.substring(0,fdId.length()-1);  //去掉最后一个 "
            return fdId;
        }

    /**
     * 去双引号
     * @param str
     * @return
     */
    public static String stringReplace(String str) {
        //去掉" "号
         str= str.replace("\"", "");

        return str ;
    }

    public static  String stringGang(String str) {
        str  = str.replace("\\", "");
        return  str;
    }

    // 读取文件夹下所有文件名
    public static List getFile(File file) {
        List<File> listLocal = new ArrayList<>();
        if (file != null) {
            File[] f = file.listFiles();
            if (f != null) {
                for (int i = 0; i < f.length; i++) {
                    //getFile(f[i]);
                    listLocal.add(f[i]);
                }
            }
        }
        return listLocal;
    }

}
