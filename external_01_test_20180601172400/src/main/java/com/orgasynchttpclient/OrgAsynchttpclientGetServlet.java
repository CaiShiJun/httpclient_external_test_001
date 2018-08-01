package com.orgasynchttpclient;

import org.asynchttpclient.*;
import org.asynchttpclient.config.AsyncHttpClientConfigDefaults;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class OrgAsynchttpclientGetServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = null;
        stringBuffer.append("<html><head><title></title></head><body><h2>OrgAsyncHttpClient</h2>");
        if (req.getParameter("orgasynchttpclientGetUrl") == null) {
            urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/test";
        } else {
            urlString = (String) req.getParameter("orgasynchttpclientGetUrl");
            String queryString = req.getQueryString();
            urlString = URLDecoder.decode(req.getQueryString().toString().substring(queryString.indexOf("=") + 1), "UTF-8");
            if (!urlString.contains("http")) {
                String urlpre = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
                urlString = urlpre + "/" + urlString;
            }
        }
        stringBuffer.append(new Date() + "<br>" + urlString + "<br>");
        DefaultAsyncHttpClientConfig.Builder configBuilder=new DefaultAsyncHttpClientConfig.Builder();
        configBuilder.setRequestTimeout(6000000);
        configBuilder.setReadTimeout(6000000);
        configBuilder.setConnectTimeout(5000);
        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient(configBuilder.build());
        Future<String> f = asyncHttpClient.prepareGet(urlString).execute(
                new AsyncCompletionHandler<String>() {

                    public String onCompleted(Response response) throws Exception {
                        return response.getResponseBody();
                    }
                    public void onThrowable(Throwable t) {
                        t.printStackTrace();
                    }
                });
        try {
            String response = response = f.get(6000000, TimeUnit.MILLISECONDS);
            stringBuffer.append(response + "<br>");

        } catch (InterruptedException e) {
            stringBuffer.append(e.getMessage() + "<br>");

            e.printStackTrace();
        } catch (ExecutionException e) {
            stringBuffer.append(e.getMessage() + "<br>");

            e.printStackTrace();
        } catch (TimeoutException e) {
            stringBuffer.append(e.getMessage() + "<br>");

            e.printStackTrace();
        }
        asyncHttpClient.close();
        stringBuffer.append(" </body></html>");
        resp.getWriter().write(stringBuffer.toString());

    }
}
