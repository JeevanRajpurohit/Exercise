interface Promotable {
    void promote() throws InvalidPromotionException;
    void promote(double salaryIncrease) throws InvalidPromotionException;
    void promote(String newRole, double salaryIncrease) throws InvalidPromotionException;
}