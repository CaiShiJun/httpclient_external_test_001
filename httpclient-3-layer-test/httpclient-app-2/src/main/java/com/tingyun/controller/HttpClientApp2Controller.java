package com.tingyun.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/HttpClientApp2Controller")
public class HttpClientApp2Controller {

    @RequestMapping("/HttpClientApp2Method")
    @ResponseBody
    public String HttpClientApp2Method(){

        System.out.println("222222222222222222222222222222222222222");

        String url = "http://192.168.2.39:8090/httpclient-app-1-1.0-SNAPSHOT/HttpClientApp1Controller/HttpClientApp1Method";

        for(int i = 0;i<2;i++) {
            CloseableHttpClient httpCilent = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            String reuslt = null;
            try {
                HttpResponse httpResponse = httpCilent.execute(httpGet);
                reuslt = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(reuslt);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpCilent.close();//释放资源
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "HttpClientApp2Controller-HttpClientApp2Method-return-message";
    }

}
