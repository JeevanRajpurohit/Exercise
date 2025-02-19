import java.util.List;

abstract class Employee {
    protected int id;
    protected String name;
    protected double salary;
    protected String role;
    protected int performanceScore;
    protected List<String> roleHierarchy;


    public Employee(int id, String name, double salary, String role, List<String> roleHierarchy) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.role = role;
        this.performanceScore = 0;
        this.roleHierarchy = roleHierarchy;
    }

    public abstract double calculateBonus();


    public int getId() {
        return id;
    }
    public String getName() {

        return name;
    }
    public double getSalary() {
        return salary;
    }
    public String getRole() {
        return role;
    }

    public int getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(int score) {
        this.performanceScore = score;
    }
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", role='" + role + '\'' +
                ", performanceScore=" + performanceScore +
                '}';
    }
}