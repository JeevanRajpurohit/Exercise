import java.util.List;

class Intern extends Employee implements Promotable {
    private String esteemedUniversity;
    private List<String> roleHierarchy;

    public Intern(int id, String name, double salary, String esteemedUniversity, List<String> roleHierarchy) {
        super(id, name, salary, "Intern", roleHierarchy);
        this.esteemedUniversity = esteemedUniversity;
        this.roleHierarchy = roleHierarchy;
    }

    @Override
    public double calculateBonus() {
        return salary * 0.09 * (performanceScore / 100.0);
    }

    @Override
    public void promote() throws InvalidPromotionException {
        promote(8000);
    }

    @Override


    public void promote(double salaryIncrease) throws InvalidPromotionException {
        if (performanceScore < 30) {
            throw new InvalidPromotionException("Keep Learning Please Interns, Performance score too low for promotion");
        }
        salary += salaryIncrease;
        LoggingPromotion.logPromotion(role, id, name);
        System.out.println("Intern Promoted");
    }

    @Override
    public void promote(String newRole, double salaryIncrease) throws InvalidPromotionException {
        if (performanceScore < 50) {
            throw new InvalidPromotionException("Keep Learning Interns, Performance score too low for role change");
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
