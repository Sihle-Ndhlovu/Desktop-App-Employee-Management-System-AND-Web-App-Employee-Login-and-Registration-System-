package departmentapp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewSalary extends JFrame {
    private JTextArea salaryInfo;
    private Connection conn;

    public ViewSalary() {
        setTitle("View Employee Salaries");
        setSize(400, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        salaryInfo = new JTextArea();
        salaryInfo.setEditable(false);
        add(new JScrollPane(salaryInfo), BorderLayout.CENTER);

        connectToDatabase();
        loadSalaries();

        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/EmployeeDB", "user", "pass");
        } catch (SQLException e) {
            showError("Database connection failed: " + e.getMessage());
        }
    }

    private void loadSalaries() {
        String query = "SELECT emp_name, salary FROM Employees";
        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            StringBuilder data = new StringBuilder();

            while (rs.next()) {
                data.append("Employee: ").append(rs.getString("emp_name"))
                    .append(" | Salary: $").append(rs.getDouble("salary"))
                    .append("\n");
            }

            salaryInfo.setText(data.toString());
        } catch (SQLException e) {
            showError("Failed to load salaries: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewSalary::new);
    }
}
