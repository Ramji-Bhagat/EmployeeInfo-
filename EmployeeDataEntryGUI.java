import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class EmployeeDataEntryGUI extends JFrame {
    private JTextField empIDField, empNameField, basicSalaryField;
    private JComboBox<String> gradeComboBox, deptComboBox;
    private HashMap<String, Employee> employeeMap;

    public EmployeeDataEntryGUI() {
        setTitle("Employee Data Entry");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Components
        JLabel empIDLabel = new JLabel("Employee ID:");
        empIDField = new JTextField(15);

        JLabel empNameLabel = new JLabel("Employee Name:");
        empNameField = new JTextField(15);

        JLabel basicSalaryLabel = new JLabel("Basic Salary:");
        basicSalaryField = new JTextField(15);

        JLabel gradeLabel = new JLabel("Grade:");
        String[] grades = {"A", "B", "C"};
        gradeComboBox = new JComboBox<>(grades);

        JLabel deptLabel = new JLabel("Department:");
        String[] deptNames = {"001 - Sales", "002 - Finance", "003 - Human Resources"}; // Department names for display
        deptComboBox = new JComboBox<>(deptNames);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEmployee();
            }
        });

        // Initialize employee map by loading existing data from file
        employeeMap = EmployeeSearchGUI.loadEmployeeData("employees.txt");

        // Layout
        setLayout(new GridLayout(6, 2, 10, 10));
        add(empIDLabel);
        add(empIDField);
        add(empNameLabel);
        add(empNameField);
        add(basicSalaryLabel);
        add(basicSalaryField);
        add(gradeLabel);
        add(gradeComboBox);
        add(deptLabel);
        add(deptComboBox);
        add(new JLabel()); // Empty label for spacing
        add(saveButton);

        setVisible(true);
    }

    private void saveEmployee() {
        String empID = empIDField.getText().trim();
        String empName = empNameField.getText().trim();
        String basicSalaryStr = basicSalaryField.getText().trim();
        String grade = (String) gradeComboBox.getSelectedItem();
        String selectedDept = (String) deptComboBox.getSelectedItem();
        String deptCode = getDeptCode(selectedDept);

        // Validate input
        if (empID.isEmpty() || empName.isEmpty() || basicSalaryStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double basicSalary;
        try {
            basicSalary = Double.parseDouble(basicSalaryStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid basic salary format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if empID already exists
        if (employeeMap.containsKey(empID)) {
            JOptionPane.showMessageDialog(this, "Employee with this Employee ID already exists.", "Duplicate Employee ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create Employee object
        Employee employee = new Employee(empID, empName, basicSalary, grade, deptCode);

        // Prompt for confirmation
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to add this employee?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            // Save employee to file and update employeeMap
            if (writeToFile(employee)) {
                JOptionPane.showMessageDialog(this, "Employee saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Update employeeMap with new employee
                employeeMap.put(empID, employee);
                // Clear input fields after saving
                empIDField.setText("");
                empNameField.setText("");
                basicSalaryField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean writeToFile(Employee employee) {
        try (FileWriter writer = new FileWriter("employees.txt", true)) {
            // Append employee details to file
            writer.write(employee.getempID() + "," + employee.getEmpName() + "," +
                    employee.getBasicSalary() + "," + employee.getGrade() + "," + employee.getDeptCode() + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getDeptCode(String deptName) {
        // Simulated department code mapping (replace with your actual mapping)
        switch (deptName) {
            case "001 - Sales":
                return "001 - Sales";
            case "002 - Finance":
                return "002 - Finance";
            case "003 - Human Resources":
                return "003 - Human Resources";
            default:
                return ""; // Handle default case
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeDataEntryGUI());
    }
}
