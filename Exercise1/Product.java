package Exercise1;

abstract class Product {
     String name;
     double price;

    public Product(String name, double price) {
        this.price=price;
        this.name=name;
    }

     abstract double getPrice();
     abstract String getDetails();

}

