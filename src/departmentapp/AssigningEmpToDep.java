package departmentapp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AssigningEmpToDep extends JFrame {
    private final JComboBox<String> empComboBox;
    private final JComboBox<String> depComboBox;
    private final JButton assignButton;
    private Connection conn;

    public AssigningEmpToDep() {
        setTitle("Assign Employee to Department");
        setSize(400, 250);
        setLayout(new GridLayout(3, 2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        empComboBox = new JComboBox<>();
        depComboBox = new JComboBox<>();
        assignButton = new JButton("Assign");

        add(new JLabel("Select Employee:"));
        add(empComboBox);
        add(new JLabel("Select Department:"));
        add(depComboBox);
        add(assignButton);

        connectToDatabase();
        loadComboBoxData(empComboBox, "SELECT emp_name FROM Employees");
        loadComboBoxData(depComboBox, "SELECT dep_name FROM Departments");

        assignButton.addActionListener(e -> assignEmployee());

        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/EmployeeDB", "user", "pass");
        } catch (SQLException e) {
            showError("Database connection failed: " + e.getMessage());
        }
    }

    private void loadComboBoxData(JComboBox<String> comboBox, String query) {
        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                comboBox.addItem(rs.getString(1));
            }
        } catch (SQLException e) {
            showError("Failed to load data: " + e.getMessage());
        }
    }

    private void assignEmployee() {
        String employee = (String) empComboBox.getSelectedItem();
        String department = (String) depComboBox.getSelectedItem();

        if (employee == null || department == null) {
            showError("Please select both an employee and a department.");
            return;
        }

        try (PreparedStatement pst = conn.prepareStatement("UPDATE Employees SET department=? WHERE emp_name=?")) {
            pst.setString(1, department);
            pst.setString(2, employee);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Employee assigned successfully!");
            } else {
                showError("No employee found with the selected name.");
            }
        } catch (SQLException e) {
            showError("Failed to assign employee: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AssigningEmpToDep::new);
    }
}