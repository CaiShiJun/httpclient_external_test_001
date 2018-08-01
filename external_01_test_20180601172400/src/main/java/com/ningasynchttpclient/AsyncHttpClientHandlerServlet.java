package com.ningasynchttpclient;

import com.ning.http.client.*;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncHttpClientHandlerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = null;
        stringBuffer.append("<html><head><title></title></head><body><h2>AsyncHttpClient :AsyncHandler execute</h2>");
        if (req.getParameter("asynchttpclientGetUrl") == null) {
            urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/test";
        } else {
            urlString = (String) req.getParameter("asynchttpclientGetUrl");
            String queryString = req.getQueryString();
            urlString = URLDecoder.decode(req.getQueryString().toString().substring(queryString.indexOf("=") + 1), "UTF-8");
            if (!urlString.contains("http")) {
                String urlpre = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
                urlString = urlpre + "/" + urlString;
            }
        }
        stringBuffer.append(new Date() + "<br>" + urlString + "<br>");

        AsyncHttpClientConfig.Builder configBuilder = new AsyncHttpClientConfig.Builder();
        configBuilder.setRequestTimeoutInMs(6000000);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient(configBuilder.build());
        Future<String> responseFuture = asyncHttpClient.prepareGet(urlString).execute(new AsyncHandler<String>() {
            private ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            public STATE onStatusReceived(HttpResponseStatus status) throws Exception {
                int statusCode = status.getStatusCode();
                System.out.println(statusCode);
                return STATE.CONTINUE;
            }

            public AsyncHandler.STATE onHeadersReceived(HttpResponseHeaders h) throws Exception {
                FluentCaseInsensitiveStringsMap headers = h.getHeaders();
                System.out.println(headers);
                return AsyncHandler.STATE.CONTINUE;
            }

            public AsyncHandler.STATE onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                bytes.write(bodyPart.getBodyPartBytes());

                return AsyncHandler.STATE.CONTINUE;
            }

            public String onCompleted() throws Exception {
                // Will be invoked once the response has been fully read or a ResponseComplete exception
                // has been thrown.
                // NOTE: should probably use Content-Encoding from headers
                return bytes.toString("UTF-8");
            }

            public void onThrowable(Throwable t) {
                System.out.println(t.getMessage());

            }
        });
        try {
            String bodyResponse = responseFuture.get();
            stringBuffer.append(bodyResponse);
        } catch (InterruptedException e) {
            stringBuffer.append("<br> " + e.getMessage() + "<br>");
            e.printStackTrace();
        } catch (ExecutionException e) {
            stringBuffer.append("<br> " + e.getMessage() + "<br>");
            e.printStackTrace();
        }
        asyncHttpClient.close();
        stringBuffer.append(" </body></html>");
        resp.getWriter().write(stringBuffer.toString());

    }
}
