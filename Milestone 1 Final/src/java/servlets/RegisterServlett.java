/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import DBConnect.ConnectingPoint;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Dineo
 */
public class RegisterServlett extends HttpServlet {
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServelletII</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServelletII at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
    }
    
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String employeeId = request.getParameter("employeeId");
        String fullname = request.getParameter("fullName");
        String department = request.getParameter("department");
        String role = request.getParameter("role");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        
        ConnectingPoint connectpoint = new ConnectingPoint();
        
        try(Connection conn = connectpoint.getCon()) {
            
           if(employeeIdExists(conn, employeeId)){
               request.setAttribute("error","Employee ID already exists" );
               request.getRequestDispatcher("Register.jsp").forward(request, response);
               return;
           }
           if(emailExists(conn, email)){
               request.setAttribute("error","Email already exists" );
               request.getRequestDispatcher("Register.jsp").forward(request, response);
               return;
           }
           try(PreparedStatement pstmtEmployee = conn.prepareStatement("INSERT INTO \"tblEmployees\"(employeeid, name, department, role, password, phone, email) VALUES (?,?,?,?,?,?,?)"); 
            PreparedStatement pstmtlogin = conn.prepareStatement("INSERT INTO \"tblLogin\"(employeeid, password) VALUES(?,?)")){
                
                pstmtEmployee.setString(1, employeeId);
                pstmtEmployee.setString(2, fullname);
                pstmtEmployee.setString(3, department);
                pstmtEmployee.setString(4, role);
                pstmtEmployee.setString(5, password);
                pstmtEmployee.setString(6, phone);
                pstmtEmployee.setString(7, email);
                pstmtEmployee.executeUpdate();
                
                pstmtlogin.setString(1, employeeId);
                pstmtlogin.setString(2, password);
                pstmtlogin.executeUpdate();
                
                response.sendRedirect("login.jsp");
        }
        
        
        }catch(SQLException ex){
            ex.printStackTrace();
            request.setAttribute("error","Registration failed" + ex.getMessage());
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
            request.setAttribute("error","Driver Error" + ex.getMessage());
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }
    
    private boolean employeeIdExists(Connection  conn, String employeeId) throws SQLException{
        try(PreparedStatement pstmt = conn.prepareStatement("SELECT 1 FROM \"tblEmployees\" WHERE employeeid = ?")){
            pstmt.setString(1, employeeId);
            ResultSet rst = pstmt.executeQuery();
            return rst.next();
        }
    }
    
    private boolean emailExists(Connection  conn, String email) throws SQLException{
        try(PreparedStatement pstmt = conn.prepareStatement("SELECT 1 FROM \"tblEmployees\" WHERE email = ?")){
            pstmt.setString(1, email);
            ResultSet rst = pstmt.executeQuery();
            return rst.next();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
