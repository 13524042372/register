package com.base.project.rabbitmq;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class BaseTes {
   
    @Test
    public void stringInternTest() {
        System.err.println(" test start:" );

        String s11 = new String("aaa");
        String s12 = "aaa";
        System.err.println("s11 == s12 :" + (s11 == s12));
        
        String s21 = "aaa";
        String s22 = "aaa";
        System.err.println(" s22 == s21 :" + (s22 == s21));
        
        String s31 = new String("s3").intern();
        String s32 = "s3";
        System.err.println("s31 == s32 :" + (s31 == s32));
    }
    
    @Test
    public void stringTest() {
        String s1 = new String("aaa");
        String s2 = "aaa";
        System.out.println(s1 == s2);    // false

        s1 = new String("bbb").intern();
        s2 = "bbb";
        System.out.println(s1 == s2);    // true

        s1 = "ccc";
        s2 = "ccc";
        System.out.println(s1 == s2);    // true

        s1 = new String("ddd").intern();
        s2 = new String("ddd").intern();
        System.out.println(s1 == s2);    // true

        s1 = "ab" + "cd";
        s2 = "abcd";    
        System.out.println(s1 == s2);    // true

        String temp = "hh";
        s1 = "a" + temp;
        // 如果调用s1.intern 则最终返回true
        s2 = "ahh";
        System.out.println(s1 == s2);    // false

        temp = "hh".intern();
        s1 = "a" + temp;
        s2 = "ahh";
        System.out.println(s1 == s2);    // false

        temp = "hh".intern();
        s1 = ("a" + temp).intern();
        s2 = "ahh";
        System.out.println(s1 == s2);    // true

        s1 = new String("1");    // 同时会生成堆中的对象 以及常量池中1的对象，但是此时s1是指向堆中的对象的
        s1.intern();            // 常量池中的已经存在
        s2 = "1";
        System.out.println(s1 == s2);    // false

        String s3 = new String("1") + new String("1");    // 此时生成了四个对象 常量池中的"1" + 2个堆中的"1" + s3指向的堆中的对象（注此时常量池不会生成"11"）
        s3.intern();    // jdk1.7之后，常量池不仅仅可以存储对象，还可以存储对象的引用，会直接将s3的地址存储在常量池
        String s4 = "11";    // jdk1.7之后，常量池中的地址其实就是s3的地址
        System.out.println(s3 == s4); // jdk1.7之前false， jdk1.7之后true

        s3 = new String("2") + new String("2");
        s4 = "22";        // 常量池中不存在22，所以会新开辟一个存储22对象的常量池地址
        s3.intern();    // 常量池22的地址和s3的地址不同
        System.out.println(s3 == s4); // false

        // 对于什么时候会在常量池存储字符串对象，我想我们可以基本得出结论: 1. 显示调用String的intern方法的时候; 2. 直接声明字符串字面常量的时候，例如: String a = "aaa";
        // 3. 字符串直接常量相加的时候，例如: String c = "aa" + "bb";  其中的aa/bb只要有任何一个不是字符串字面常量形式，都不会在常量池生成"aabb". 且此时jvm做了优化，不//   会同时生成"aa"和"bb"在字符串常量池中
    }
    @Test
    public void timeTest() {
        long curLockTime = System.currentTimeMillis();
        String curTime = String.valueOf(curLockTime).substring(0, 10);
        System.err.println(curTime);
      /*  Calendar c = Calendar.getInstance();
        c.setTime(new Date());*/
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String tmSmp = sf.format(new Date());
        System.err.println(tmSmp);

    }
    @Test
    public void cifTempQueueMsg() {
        try {
            String msg = "{\r\n" + 
                    "\r\n" + 
                    "    \"userNo\": \"110000044797963\",\r\n" + 
                    "    \"bcmcCardSn\": \"BCMC20180829191526515936\"\r\n" + 
                    "}";
            RabbitMqProduct.createMsgToVHost(msg, "cif_q_updateEvent","cif_x_updateEvent","/cif");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void httpClient()  {
        while(true) {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpget = new HttpGet("http://172.27.26.82:8081/v1/main/test");
    
                System.out.println("Executing request " + httpget.getRequestLine());
    
                // Create a custom response handler
                ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
    
                    @Override
                    public String handleResponse(
                            final HttpResponse response) throws ClientProtocolException, IOException {
                        int status = response.getStatusLine().getStatusCode();
                        if (status >= 200 && status < 300) {
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : null;
                        } else {
                            throw new ClientProtocolException("Unexpected response status: " + status);
                        }
                    }
    
                };
                String responseBody = httpclient.execute(httpget, responseHandler);
                System.out.println("----------------------------------------");
                System.out.println(responseBody);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                //httpclient.close();
            }
        }
    }
    
    @Test
    public void timestamp() {
        long time = 0L;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            time = format.parse("2018-08-30").getTime();
            System.err.println("2018-08-30:" + time);
            time = format.parse("2018-09-01").getTime();
            System.err.print("2018-08-31:" + time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
