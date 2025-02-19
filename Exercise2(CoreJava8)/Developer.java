import java.sql.SQLOutput;
import java.util.List;

class Developer extends Employee implements Promotable {
    private String preferredLanguage;
    private List<String> roleHierarchy;

    public Developer(int id, String name, double salary, String programmingLanguage, List<String> roleHierarchy) {
        super(id, name, salary, "Developer", roleHierarchy);
        this.preferredLanguage = programmingLanguage;
        this.roleHierarchy = roleHierarchy;
    }

    @Override
    public double calculateBonus() {
        return salary * 0.05 * (performanceScore / 100.0);
    }

    @Override
    public void promote() throws InvalidPromotionException {
        promote(10000);
    }

    @Override
    public void promote(double salaryIncrease) throws InvalidPromotionException {
        if (performanceScore < 65) {
            throw new InvalidPromotionException("Keep Learning Please Developer, Performance score too low for promotion");
        }
        salary += salaryIncrease;
        LoggingPromotion.logPromotion(role, id, name);
        System.out.println("Developer Promoted");
    }

    @Override
    public void promote(String newRole, double salaryIncrease) throws InvalidPromotionException {
        if (performanceScore < 75) {
            throw new InvalidPromotionException("Keep Learning Developer, Performance score too low for role change");
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