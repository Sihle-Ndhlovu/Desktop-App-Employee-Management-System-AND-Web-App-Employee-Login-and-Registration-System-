package servlets;

import DBConnect.ConnectingPoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    String url = "jdbc:postgresql://localhost:5432/EmployeeDB";
    String usr = "postgres";
    String password = "12457";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        ConnectingPoint cp = new ConnectingPoint();

        // Retrieve and validate form data
        String[] requiredFields = {
            "employeeId", "fullName", "department", "role", "password", "phone", "email"
        };
        
        for (String field : requiredFields) {
            if (request.getParameter(field) == null || request.getParameter(field).isEmpty()) {
                out.println("<script>alert('All fields are required!'); window.location='register.jsp';</script>");
                return;
            }
        }

        String empId = request.getParameter("employeeId");
        String email = request.getParameter("email");

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, usr, password)) {
                if (isEmployeeExists(conn, empId, email)) {
                    request.setAttribute("error","Employee ID & Email already exists" );
                    request.getRequestDispatcher("Register.jsp").forward(request, response);
                    return;
                }

                if (registerEmployee(conn, request)) {
                    // This is a redirection
                    response.sendRedirect("login.jsp");
                    return; 
                } else {
                    //request.setAttribute("error","Registration failed");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error","Driver Error");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }

    public boolean isEmployeeExists(Connection conn, String empId, String email) throws Exception {
        String checkQuery = "SELECT 1 FROM \"tblEmployees\" WHERE employeeid = ? OR email = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, empId);
            checkStmt.setString(2, email);
            try (ResultSet rs = checkStmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean registerEmployee(Connection conn, HttpServletRequest request) throws Exception {
        String insertQuery = "INSERT INTO \"tblEmployees\" (employeeid, name, department, role, password, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
         try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            insertStmt.setString(1, request.getParameter("employeeId"));
            insertStmt.setString(2, request.getParameter("fullName"));
            insertStmt.setString(3, request.getParameter("department"));
            insertStmt.setString(4, request.getParameter("role"));
            insertStmt.setString(5, request.getParameter("password")); 
            insertStmt.setString(6, request.getParameter("phone"));
            insertStmt.setString(7, request.getParameter("email"));
            
            if(insertStmt.executeUpdate() <=0){
                return false;
            }
             
            String loginInsertQuery = "INSERT INTO \"tblLogin\" (employeeid, password) VALUES (?, ?)";
            try(PreparedStatement loginInsertStmt = conn.prepareStatement(loginInsertQuery)){
                loginInsertStmt.setString(1, request.getParameter("employeeId"));
                loginInsertStmt.setString(2, request.getParameter("password"));
                if(loginInsertStmt.executeUpdate() <=0){
                    return false;
   
            }   
        }
    }
        return false;
    }
}
       