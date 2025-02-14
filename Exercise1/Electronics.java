package Exercise1;

class Electronics extends Product implements Discountable {

    Electronics(String name, double price) {
        super(name,price);
    }

    @Override
    public void applyDiscount(double percentage) {
        this.price-= this.price*(percentage/100);
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDetails() {
        return "ELectronics Product name and price is : " + name + " : "+ price;
    }
}
