package java_first_one;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Employee extends Thread implements Comparable<Employee> {

    private String employeeName;
    private double salary;
    private int age;

    public Employee(String employeeName, double salary, int age) {
        this.employeeName = employeeName;
        this.salary = salary;
        this.age = age;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void run() {
        System.out.println("Thread is running for employee: " + employeeName);
    }

    @Override
    public int compareTo(Employee otherEmployee) {
        int ageComparison = Integer.compare(this.age, otherEmployee.age);
        if (ageComparison != 0) {
            return ageComparison;
        }
        return Double.compare(this.salary, otherEmployee.salary);
    }

    public static void main(String[] args) {
        String[] names = {
            "employee 1 ", "employee 2", "employee 3", "employee 4", "employee 5",
            "employee 6", "employee 7", "employee 8", "employee 9", "employee 10"
        };

        Employee[] employees = new Employee[10];

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            employees[i] = new Employee(names[i],
                    50000 + random.nextInt(50000), 20 + random.nextInt(20));
        }

        System.out.println("Unsorted Array:");
        for (Employee employee : employees) {
            System.out.println("Name: " + employee.getEmployeeName() +
                    ", Salary: " + employee.getSalary() +
                    ", Age: " + employee.getAge() + "\n");
        }

        for (Employee employee : employees) {
            Thread thread = new Thread(employee, employee.getEmployeeName());
            thread.start();
        }

        try {
            for (Thread thread : Thread.getAllStackTraces().keySet()) {
                if (thread.getName().contains("Employee")) {
                    thread.join();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Arrays.sort(employees, Comparator.comparingInt(Employee::getAge));

        System.out.println("\nSorted Array by Age:");
        for (Employee employee : employees) {
            System.out.println("Name: " + employee.getEmployeeName() +
                    ", Salary: " + employee.getSalary() +
                    ", Age: " + employee.getAge() + "\n");
        }

        Arrays.sort(employees, Comparator.comparingDouble(Employee::getSalary));

        System.out.println("\nSorted Array by Salary:");
        for (Employee employee : employees) {
            System.out.println("Name: " + employee.getEmployeeName() +
                    ", Salary: " + employee.getSalary() +
                    ", Age: " + employee.getAge() + "\n");
        }
    }
}
