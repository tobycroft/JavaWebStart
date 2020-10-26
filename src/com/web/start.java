package com.web;

import com.mysql.cj.xdevapi.JsonArray;
import com.mysql.cj.xdevapi.JsonParser;
import com.mysql.cj.xdevapi.JsonString;
import com.oracle.javafx.jmx.json.JSONReader;
import com.oracle.javafx.jmx.json.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet(name = "start1", urlPatterns = "/start/aa")

public class start extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        System.out.println("客户端发起了post请求");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("客户端发起了get请求");
        String uid = req.getParameter("uid");
        String token = req.getParameter("token");

        System.out.println(uid);
        System.out.println(token);

//        db(uid);

        PrintWriter pw = resp.getWriter();
        ResultSet result = db(uid);

        try {
            while (result.next()) {
                pw.write(result.getString("username"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static ResultSet db(String uid) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://192.168.1.11:3306/javachat_master";
            Connection connection = DriverManager.getConnection(url, "javachat_master", "javachat_master");
            Statement statement = connection.createStatement();
            String sql = "select * from cq_user where id = ?";
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setObject(1, uid);
            ResultSet result = pre.executeQuery();
            return result;
//            while (result.next()) {
//                System.out.println(result.getString("username"));
//            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;

    }

    @Override
    public void destroy() {
        System.out.println("已经被销毁");
        super.destroy();
    }
}
