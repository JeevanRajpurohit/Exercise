package Exercise1;

class Clothing extends Product implements Discountable {
     Clothing(String name, double price) {
        super(name, price);
    }

    @Override
    public void applyDiscount(double percentage) {
        price-= price*(percentage)/100;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDetails() {
        return "Clothing Product name and price is : " + name + " : " + price;
    }
}
