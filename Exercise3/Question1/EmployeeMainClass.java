package Question1;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeMainClass {
        public static  void main(String[] args) {

            List<Employee> employeeList = new ArrayList<>();
            employeeList.add(new Employee(1, "Jeevan", "IT", 16000, 22));
            employeeList.add(new Employee(2, "Rakesh", "IT", 190000, 25));
            employeeList.add(new Employee(3, "Bhavesh", "government", 55000, 17));
            employeeList.add(new Employee(4, "Raju", "Finance", 60000, 25));
            employeeList.add(new Employee(5, "Ramesh", "Marketing", 41000, 47));
//        //1st(a) Question
//        //Approach 1
            List<Employee> result = employeeList.stream().filter(e -> e.getSalary() > 50000).collect(Collectors.toList());
            System.out.println(result);
//        //Approach 2
            employeeList.removeIf(e -> e.getSalary() <= 50000);
            System.out.println(employeeList);
            //Approach 3( not work)
            for (Employee e : employeeList) {  // //ConcurrentModificationException not work , at a time we cannot able to do 2 acticity.
                if (e.getSalary() <= 50000) {
                    employeeList.remove(e);
                }
            }
            System.out.println(employeeList);   // need to create new array and store element their, other way remove iterator.
            Iterator<Employee> iterator = employeeList.iterator();
            while (iterator.hasNext()) {
                Employee e = iterator.next();
                if (e.getSalary() <= 50000) {
                    iterator.remove();
                }
            }

            System.out.println(employeeList);
//
//        //   1st(b) Question
//        // Approach 1;
//        //because of I override equals and hashcode in employee class this works.(distict()) keyword.
            List<Employee> employeeWithDistinctDepartment = employeeList.stream().distinct().toList();
            System.out.println(employeeWithDistinctDepartment);
//
            //Approach 2 using streamMap+ distinct( getting Only name of unique department exist:
            List<String> employeeWithDistinctDepartment1 = employeeList.stream().map(Employee::getDepartment)
                    .distinct().toList();
//            System.out.println(employeeWithDistinctDepartment);
//
            //1st(c) Question (Approach 1)
            Map<String, Double> avgSalaryByDepartment1 = employeeList.stream().collect(Collectors.groupingBy(
                    Employee::getDepartment,
                    Collectors.averagingDouble(Employee::getSalary)

            ));
//            System.out.println(avgSalaryByDepartment);
            //new approach using teeing.
            Map<String, Double> avgSalaryByDepartment = employeeList.stream()
                    .collect(Collectors.groupingBy(
                            Employee::getDepartment,
                            Collectors.teeing(
                                    Collectors.summingDouble(Employee::getSalary),
                                    Collectors.counting(),
                                    (sum, count) -> sum / count
                            )
                    ));
            System.out.println(avgSalaryByDepartment1);

            //   1st(d) Question
            List<Employee> sortEmployeeByAge = employeeList.stream().sorted(Comparator.comparingInt(Employee::getAge)).collect(Collectors.toList());
            System.out.println(sortEmployeeByAge);
            System.out.println(sortEmployeeByAge.getFirst());
            System.out.println(sortEmployeeByAge.getLast());

            //or
            Employee oldestEmployee = employeeList.stream()
                    .max(Comparator.comparingInt(Employee::getAge))
                    .orElse(null);
            System.out.println(oldestEmployee);

            Employee yongestEmployee = employeeList.stream().min(Comparator.comparingInt(Employee::getAge)).orElse(null);
            System.out.println(yongestEmployee);
            //or
            System.out.println("*********************");  //can use same way any also.
            List<Employee> sortedByAge = employeeList.stream()
                    .sorted(Comparator.comparingInt(Employee::getAge))
                    .toList();
            Employee youngest = sortedByAge.stream().findFirst().orElse(null);
            System.out.println(youngest);

        }
}
