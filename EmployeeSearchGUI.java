import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.*;
import java.util.HashMap;

public class EmployeeSearchGUI extends JFrame {
    private JTextField empIDField;
    private HashMap<String, Employee> employeeMap;

    public EmployeeSearchGUI(HashMap<String, Employee> employeeMap) {
        this.employeeMap = employeeMap;

        setTitle("Employee Search");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel empIDLabel = new JLabel("Enter Employee ID:");
        empIDField = new JTextField(15);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployee();
            }
        });

        setLayout(new FlowLayout());
        add(empIDLabel);
        add(empIDField);
        add(searchButton);

        setVisible(true);
    }

    private void searchEmployee() {
        String empID = empIDField.getText().trim();

        if (empID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (employeeMap.containsKey(empID)) {
            Employee employee = employeeMap.get(empID);
            JOptionPane.showMessageDialog(this, "Employee Details:\n" + employee, "Employee Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Employee not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static HashMap<String, Employee> loadEmployeeData(String filename) {
        HashMap<String, Employee> employeeMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String empID = parts[0];
                    String empName = parts[1];
                    double basicSalary = Double.parseDouble(parts[2]);
                    String grade = parts[3];
                    String deptCode = parts[4];
                    Employee employee = new Employee(empID, empName, basicSalary, grade, deptCode);
                    employeeMap.put(empID, employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employeeMap;
    }

    public static void main(String[] args) {
        // Load employee data from file
        HashMap<String, Employee> employeeMap = loadEmployeeData("employees.txt");

        SwingUtilities.invokeLater(() -> new EmployeeSearchGUI(employeeMap));
    }
}
