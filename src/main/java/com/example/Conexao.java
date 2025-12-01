package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/fotosdb";
        String user = "postgres";
        String password = "postgres123";

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}
