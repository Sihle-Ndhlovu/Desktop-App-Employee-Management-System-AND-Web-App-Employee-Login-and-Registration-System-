package departmentapp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RemovingEmpFromDep extends JFrame {
    private JComboBox<String> empComboBox;
    private JButton removeButton;
    private Connection conn;

    public RemovingEmpFromDep() {
        setTitle("Remove Employee from Department");
        setSize(400, 200);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        empComboBox = new JComboBox<>();
        removeButton = new JButton("Remove");

        add(new JLabel("Select Employee:"));
        add(empComboBox);
        add(removeButton);

        connectToDatabase();
        loadEmployees();

        removeButton.addActionListener(e -> removeEmployee());

        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/EmployeeDB", "user", "pass");
        } catch (SQLException e) {
            showError("Database connection failed: " + e.getMessage());
        }
    }

    private void loadEmployees() {
        loadComboBoxData(empComboBox, "SELECT emp_name FROM Employees WHERE department IS NOT NULL");
    }

    private void loadComboBoxData(JComboBox<String> comboBox, String query) {
        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                comboBox.addItem(rs.getString(1)); // Use column index for simplicity
            }
        } catch (SQLException e) {
            showError("Failed to load employees: " + e.getMessage());
        }
    }

    private void removeEmployee() {
        String employee = (String) empComboBox.getSelectedItem();
        if (employee == null) {
            showError("Please select an employee.");
            return;
        }

        try (PreparedStatement pst = conn.prepareStatement("UPDATE Employees SET department=NULL WHERE emp_name=?")) {
            pst.setString(1, employee);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Employee removed from department!");
            } else {
                showError("No employee found with the selected name.");
            }
        } catch (SQLException e) {
            showError("Failed to remove employee: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RemovingEmpFromDep::new);
    }
}