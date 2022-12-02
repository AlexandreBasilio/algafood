package com.algaworks.algafood.api.controller;

import java.sql.*;

public class test {
    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:algafood.db");
        Statement stat = conn.createStatement();

        ResultSet rs = stat.executeQuery("select * from restaurante;");
        while (rs.next()) {
            System.out.println("date = " + rs.getDate("data_cadastro"));
            System.out.println("dateAsString = " + rs.getString("data_cadastro"));
        }
        rs.close();
        conn.close();
    }
}
