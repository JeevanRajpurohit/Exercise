import java.util.List;

class Manager extends Employee implements Promotable {
    private int employeesWorkUnderMe;
    private List<String> roleHierarchy;

    public Manager(int id, String name, double salary, int employeesWorkUnderMe, List<String> roleHierarchy) {
        super(id, name, salary, "Manager",roleHierarchy);
        this.employeesWorkUnderMe = employeesWorkUnderMe;
        this.roleHierarchy = roleHierarchy;
    }

    @Override
    public double calculateBonus() {
        return salary * (performanceScore / 100.0) + (employeesWorkUnderMe * 100);
    }

    @Override
    public void promote() throws InvalidPromotionException {
        promote(12000);
    }

    @Override
    public void promote(double salaryIncrease) throws InvalidPromotionException {
        if (performanceScore < 80) {
            throw new InvalidPromotionException("Keep Learning Please Manager, Performance score too low for promotion ");
        }
        salary += salaryIncrease;
        LoggingPromotion.logPromotion(role, id, name);
        System.out.println("Manager Promoted");
    }

    @Override
    public void promote(String newRole, double salaryIncrease) throws InvalidPromotionException {
        if (performanceScore < 90) {
            throw new InvalidPromotionException("Keep Learning Manager, Performance score too low for role change");
        }
        int currentIndex = roleHierarchy.indexOf(role);
        int newIndex = roleHierarchy.indexOf(newRole);

        if (newIndex == -1) {
            throw new InvalidPromotionException("Invalid role: " + newRole);
        }
        if (newIndex != currentIndex + 1) {
            throw new InvalidPromotionException("Invalid promotion: " + newRole + " is not the next role in the hierarchy.");
        }
        role = newRole;
        salary += salaryIncrease;
        LoggingPromotion.logPromotion(newRole, id, name);
        System.out.println("Developer Promoted to new role as "+ newRole );
    }
}
