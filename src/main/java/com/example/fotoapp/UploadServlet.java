package com.example.fotoapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

@MultipartConfig
public class UploadServlet extends HttpServlet {

    private static final String URL = "jdbc:postgresql://localhost:5432/fotosdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres123";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Part filePart = req.getPart("foto");  // campo do formulário no Postman
        String fileName = filePart.getSubmittedFileName();
        String fileType = filePart.getContentType();

        InputStream fileContent = filePart.getInputStream();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO fotos (nome, tipo, dados) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, fileName);
            stmt.setString(2, fileType);
            stmt.setBinaryStream(3, fileContent);

            stmt.executeUpdate();
            stmt.close();

            resp.getWriter().println("Foto salva com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar foto: " + e.getMessage());
        }
    }
}
