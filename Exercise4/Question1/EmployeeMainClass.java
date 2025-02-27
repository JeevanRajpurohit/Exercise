package Question1;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeMainClass {

    private static boolean containsOnlyLettersAndSpaces(String name) {
        if ("null".equals(name) || name.isEmpty()) {
            return false;
        }
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }
    private static boolean isValidDepartmentName(String name){
        if ("null".equals(name) || name.isEmpty()) {
            return false;
        }
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }

        return true;
    }
    private static boolean isEmployeeIdExists(List<Employee> employeeList, int employeeId) {
        for (Employee emp : employeeList) {
            if (emp.getId() == employeeId) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);

        System.out.print("Enter number of user to add : ");
        int numberOfUserWantToAdd = sc.nextInt();

        List<Employee> employeeList= new ArrayList<>();
        int n=1;
        while(numberOfUserWantToAdd>0) {
            int employeeId = 0;
            String employeeName = "";
            String employeeDepartment = "";
            double employeeSalary = 0.0;

            while (true) {

                System.out.print("Enter the details of employee number " + n + ", Please Enter employeeId :");

                if (sc.hasNextInt()) {
                    employeeId = sc.nextInt();
                    sc.nextLine();
                    if ( employeeId > 0 && !isEmployeeIdExists(employeeList, employeeId)) {
                        break;
                    } else if (employeeId <= 0) {
                        System.out.println("Employee ID must be a positive integer. Please try again.");
                    } else {
                        System.out.println("Employee ID already exists. Please enter a unique ID.");
                    }
                } else {
                    System.out.println("Invalid input. Employee ID must be an integer. Please try again.");
                    sc.nextLine();
                }
            }


            while (true) {
                System.out.print("Enter the name: ");
                employeeName = sc.nextLine();
                if (containsOnlyLettersAndSpaces(employeeName)) {
                    break;
                } else {
                    System.out.println("Invalid input. Name should not be empty or null and should only contain letters. Please try again.");
                }
            }

            while (true) {
                System.out.print("Enter the department: ");
                employeeDepartment = sc.nextLine();
                if (isValidDepartmentName(employeeDepartment)) {
                    break;
                }else {
                    System.out.println("Invalid input. Department should not be empty or null or order than letters. Please try again.");
                }
            }

            while (true) {
                System.out.print("Enter the salary: ");
                if (sc.hasNextDouble()) {
                    employeeSalary = sc.nextDouble();
                    sc.nextLine();
                    if (employeeSalary > 0) {
                        break;
                    } else {
                        System.out.println("Salary must be a positive number. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input. Salary must be a number. Please try again.");
                    sc.nextLine();
                }
            }

            employeeList.add(new Employee(employeeId,employeeName,employeeDepartment,employeeSalary));
            numberOfUserWantToAdd--;
            n++;

        }
        //1st
        List<Employee> employeeSalaryGreaterThanThreshold = employeeList.stream().filter(e-> e.getSalary()>50000).collect(Collectors.toList());
        System.out.println("Employee with salary greater than 50000 " + employeeSalaryGreaterThanThreshold);

        //2nd
        Comparator<Employee> emp = (i, j) -> {
            if (i.getSalary() > j.getSalary()) {
                return -1;
            } else if (i.getSalary() < j.getSalary()) {
                return 1;
            } else {
                return 0;
            }
        };
        employeeList.sort(emp);
        System.out.println("Sorting of employee in descending order of salary is : "+ employeeList);

        //3rd
        if(employeeList.size()<3){
            System.out.println("Size of employee in organization is less than threshold");
        }else {
            List<Employee> topThreeEmployee = employeeList.stream().sorted(emp).limit(3).toList();
            System.out.println("List of top 3 employee is : " + topThreeEmployee);
        }

       // 4th
        Map<Integer, String> mappingOfEmployee = employeeList.stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getName));
        System.out.println("Mapping of employeeId with name : "+ mappingOfEmployee);
    }


}
