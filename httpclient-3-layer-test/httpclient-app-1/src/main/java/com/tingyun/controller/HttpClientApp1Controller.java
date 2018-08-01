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
@RequestMapping("/HttpClientApp1Controller")
public class HttpClientApp1Controller {

    @RequestMapping("/HttpClientApp1Method")
    @ResponseBody
    public String HttpClientApp1Method(){

        System.out.println("11111111111111111111111111111111111111111111111111111111");

        String url = "http://192.168.3.35:8080/httpclient-app-0-1.0-SNAPSHOT/HttpClientApp0Controller/HttpClientApp0Method";
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        String reuslt = null;
        try {
            HttpResponse httpResponse = httpCilent.execute(httpGet);
            reuslt = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(reuslt);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpCilent.close();//释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "HttpClientApp1Controller-HttpClientApp1Method-return-message";
    }

}
