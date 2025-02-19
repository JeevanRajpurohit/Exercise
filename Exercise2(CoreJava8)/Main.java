import java.util.*;

public class Main {
    public static void main(String[] args) {

        Map<Integer, Employee> employees = new HashMap<>();
        List<String> roleHierarchy = Arrays.asList("Intern", "Junior Java Developer", "Developer", "Senior Java Developer", "Manager", "Senior Manager");
        Scanner sc= new Scanner(System.in);

        Developer developer1 = new Developer(1, "Jeevan Rajpurohit", 58000, "Java",roleHierarchy);
        System.out.print("Please Enter performance score for Developer1 out of 100 : ");
        int scoreOfDeveloper= sc.nextInt();
        developer1.setPerformanceScore(scoreOfDeveloper);

        Manager manager1 = new Manager(2, "Rakesh Rajpurohit", 180000, 10,roleHierarchy);
        System.out.print("Please Enter performance score for Manager1 out of 100 : ");
        int scoreOfManager= sc.nextInt();
        manager1.setPerformanceScore(scoreOfManager);

        Intern intern1 = new Intern(3, "Bhavesh Rajpurohit", 20000, "DAIICT",roleHierarchy);
        System.out.print("Please Enter performance score for Intern1 out of 100 : ");
        int scoreOfIntern= sc.nextInt();
        intern1.setPerformanceScore(scoreOfIntern);

        employees.put(developer1.getId(), developer1);
        employees.put(manager1.getId(), manager1);
        employees.put(intern1.getId(), intern1);

        try {
            developer1.promote("Senior Java Developer", 12999);
        } catch (InvalidPromotionException e) {
            System.err.println("Promotion error for Developer: " + e.getMessage());
        }

        try {
            manager1.promote(11001);
        } catch (InvalidPromotionException e) {
            System.err.println("Promotion error for Manager: " + e.getMessage());
        }

        try {
            intern1.promote("Junior Java Developer", 17999);
        } catch (InvalidPromotionException e) {
            System.err.println("Promotion error for Intern: " + e.getMessage());
        }

            System.out.println("Enter 1 : To see Top employee performance by score , Enter 2 : To See Top Performance by salary ");
            int Value= sc.nextInt();
            if(Value==1) {
                employees.values().stream()
                        .sorted((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()))
                        .limit(2)
                        .forEach(e -> System.out.println(e.getName() + ": " + e.getSalary()));
            }else {
                employees.values().stream()
                        .sorted((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()))
                        .forEach(e -> System.out.println(e.getName() + " - Role: " + e.getRole() + " - Salary: in Rupees" + String.format("%.2f", e.getSalary())));
            }

            double totalBonuses = employees.values().stream()
                    .mapToDouble(Employee::calculateBonus)
                    .sum();
            System.out.println("\nTotal Bonuses: in Rupees" + String.format("%.2f", totalBonuses));

    }
}