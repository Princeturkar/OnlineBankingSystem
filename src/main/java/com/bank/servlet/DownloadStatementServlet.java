package com.bank.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/downloadStatement")
public class DownloadStatementServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer accountNo = (Integer) session.getAttribute("account");

        if (accountNo == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=statement_" + accountNo + ".pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph("SecureBank Statement"));
            document.add(new Paragraph("Account Number: " + accountNo));
            document.add(new Paragraph(" ")); // Blank line

            PdfPTable table = new PdfPTable(4);
            table.addCell("Date");
            table.addCell("Type");
            table.addCell("Amount");
            table.addCell("Description");

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_db", "root", "prince07");
            
            String sql = "SELECT * FROM transactions WHERE account_no=? ORDER BY transaction_date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountNo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                table.addCell(rs.getTimestamp("transaction_date").toString());
                table.addCell(rs.getString("transaction_type"));
                table.addCell("$" + rs.getDouble("amount"));
                table.addCell(rs.getString("description"));
            }

            document.add(table);
            document.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
