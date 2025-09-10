package departmentapp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateSalary extends JFrame {
    private JComboBox<String> empComboBox;
    private JTextField salaryField;
    private JButton updateButton;
    private Connection conn;

    public UpdateSalary() {
        setTitle("Update Salary");
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        empComboBox = new JComboBox<>();
        salaryField = new JTextField();
        updateButton = new JButton("Update Salary");

        add(new JLabel("Select Employee:"));
        add(empComboBox);
        add(new JLabel("New Salary:"));
        add(salaryField);
        add(updateButton);

        connectToDatabase();
        loadEmployees();

        updateButton.addActionListener(e -> updateSalary());

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
        loadComboBoxData(empComboBox, "SELECT emp_name FROM Employees");
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

    private void updateSalary() {
        String employee = (String) empComboBox.getSelectedItem();
        if (employee == null) {
            showError("Please select an employee.");
            return;
        }

        double newSalary;
        try {
            newSalary = Double.parseDouble(salaryField.getText());
        } catch (NumberFormatException e) {
            showError("Please enter a valid salary.");
            return;
        }

        try (PreparedStatement pst = conn.prepareStatement("UPDATE Employees SET salary=? WHERE emp_name=?")) {
            pst.setDouble(1, newSalary);
            pst.setString(2, employee);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Salary updated successfully!");
            } else {
                showError("No employee found with the selected name.");
            }
        } catch (SQLException e) {
            showError("Failed to update salary: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UpdateSalary::new);
    }
}
