package com.example.fotoapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.OutputStream;
import java.sql.*;

@WebServlet("/foto")
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            long id = Long.parseLong(request.getParameter("id"));

            Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT nome, tipo, dados FROM fotos WHERE id = ?"
            );
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                response.setContentType(rs.getString("tipo"));
                OutputStream out = response.getOutputStream();
                out.write(rs.getBytes("dados"));
                out.flush();
            } else {
                response.getWriter().write("Foto não encontrada");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
