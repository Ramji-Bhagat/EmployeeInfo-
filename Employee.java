public class Employee {
    private String empID;
    private String empName;
    private double basicSalary;
    private String grade;
    private String deptCode;

    public Employee(String empID, String empName, double basicSalary, String grade, String deptCode) {
        this.empID = empID;
        this.empName = empName;
        this.basicSalary = basicSalary;
        this.grade = grade;
        this.deptCode = deptCode;
    }

    public String getempID() {
        return empID;
    }

    public String getEmpName() {
        return empName;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public String getGrade() {
        return grade;
    }

    public String getDeptCode() {
        return deptCode;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empID='" + empID + '\'' +
                ", empName='" + empName + '\'' +
                ", basicSalary=" + basicSalary +
                ", grade='" + grade + '\'' +
                ", deptCode='" + deptCode + '\'' +
                '}';
    }
}

