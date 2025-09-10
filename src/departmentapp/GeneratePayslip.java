package departmentapp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class GeneratePayslip extends JFrame {
    private JComboBox<String> empComboBox;
    private JTextArea payslipArea;
    private JButton generateButton;
    private Connection conn;

    public GeneratePayslip() {
        setTitle("Generate Payslip");
        setSize(400, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        empComboBox = new JComboBox<>();
        payslipArea = new JTextArea();
        generateButton = new JButton("Generate");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Select Employee:"));
        panel.add(empComboBox);
        panel.add(generateButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(payslipArea), BorderLayout.CENTER);

        connectToDatabase();
        loadEmployees();

        generateButton.addActionListener(e -> generatePayslip());

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

    private void generatePayslip() {
        String employee = (String) empComboBox.getSelectedItem();
        if (employee == null) {
            showError("Please select an employee.");
            return;
        }

        try (PreparedStatement pst = conn.prepareStatement("SELECT salary FROM Employees WHERE emp_name=?")) {
            pst.setString(1, employee);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double salary = rs.getDouble("salary");
                payslipArea.setText("Payslip for: " + employee + "\nSalary: $" + salary);
            } else {
                showError("No salary information found for the selected employee.");
            }
        } catch (SQLException e) {
            showError("Failed to generate payslip: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GeneratePayslip::new);
    }
}