package Exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ECommerceDiscountSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Product> productList = new ArrayList<>();

        Electronics laptop = new Electronics("Laptop", 50000);
        Clothing tshirt = new Clothing("T-Shirt", 1500);
        productList.add(new Electronics("TV",34000));
        productList.add(new Clothing("Jeans Pant",999));

        productList.add(laptop);
        productList.add(tshirt);

        for (Product product : productList) {
            System.out.print("Enter the Discount percentage for " + product.name + " : ");
            double discount = scanner.nextDouble();
            ((Discountable) product).applyDiscount(discount);
        }

        System.out.println("Final Product Details:");
        for (Product product : productList) {
            System.out.println(product.getDetails());
        }

        scanner.close();

    }
}






