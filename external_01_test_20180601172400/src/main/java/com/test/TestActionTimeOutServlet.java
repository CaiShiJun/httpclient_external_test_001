package com.test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestActionTimeOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sleepTime = req.getParameter("sleep");
        try {
            Thread.sleep(Long.valueOf(sleepTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resp.getWriter().write("sleep 1000 ms,success");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
