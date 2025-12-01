package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            Part filePart = request.getPart("foto");
            String nome = filePart.getSubmittedFileName();
            String tipo = filePart.getContentType();

            InputStream dados = filePart.getInputStream();

            Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO fotos (nome, tipo, dados) VALUES (?, ?, ?)"
            );

            stmt.setString(1, nome);
            stmt.setString(2, tipo);
            stmt.setBinaryStream(3, dados, (int) filePart.getSize());

            stmt.executeUpdate();
            stmt.close();
            conn.close();

            response.getWriter().write("Foto salva com sucesso!");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
