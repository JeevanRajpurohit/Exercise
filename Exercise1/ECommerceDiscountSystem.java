package Exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ECommerceDiscountSystem {
    public static double calculateTotalPrice(List<Product> productList){
        double totolPrice=0.0;
        for(Product product : productList){
            totolPrice+=(product.price);
        }
        return totolPrice;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Product> productList = new ArrayList<>();

        Electronics laptop = new Electronics("Laptop", 50000);
        Clothing tshirt = new Clothing("T-Shirt", 1500);
        productList.add(new Electronics("TV",34000));
        productList.add(new Clothing("Jeans Pant",999));

        productList.add(laptop);
        productList.add(tshirt);

        System.out.print("Please Enter 1 to see , Total price of product before discount : ");
        int value = scanner.nextInt();
        if(value==1){
            System.out.println(calculateTotalPrice(productList));
        }

        for (Product product : productList) {
            System.out.print("Enter the Discount percentage for " + product.name + " : ");
            double discount = scanner.nextDouble();
            ((Discountable) product).applyDiscount(discount);
        }


        System.out.println("Final Product Details:");
        for (Product product : productList) {
            System.out.println(product.getDetails());
        }

        System.out.print("Please Enter 1 to see , Total price of product after discount : ");
        int value1 = scanner.nextInt();
        if(value1==1){
            System.out.println(calculateTotalPrice(productList));
        }

        scanner.close();

    }
}






