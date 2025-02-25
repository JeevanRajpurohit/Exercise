package Question8;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMainClass {

    public static final String STATUS_PENDING="PENDING";
    public static final String STATUS_PROCESSING="PROCESSING";
    public static final String STATUS_COMPLETED="COMPLETED";
    public static final String STATUS_CANCELLED="CANCELLED";

    public static void main(String[] args) {

        List<Order> orderList= new ArrayList<>();

        orderList.add(new Order(1,"Rakesh",10000,  LocalDate.of(2025, 2, 22),STATUS_COMPLETED));
        orderList.add(new Order(2,"Sharda",50000,LocalDate.of(2025,1,19),STATUS_PENDING));
        orderList.add(new Order(3,"Raju",50000,LocalDate.of(2025,2,24),STATUS_COMPLETED));
        orderList.add(new Order(4,"Rahul",5000,LocalDate.of(2025,2,21),STATUS_PROCESSING));
        orderList.add(new Order(5,"Abhay",70000,LocalDate.of(2024,9,27),STATUS_CANCELLED));
        orderList.add(new Order(6,"Bhavesh",90000,LocalDate.of(2025,1,29),STATUS_PENDING));
        orderList.add(new Order(7,"Jeevan",110000,LocalDate.of(2025,1,11),STATUS_PROCESSING));
        System.out.println(orderList);

        //8th(a)
        LocalDate dateOfToday = LocalDate.now();
        List<Order>  pastSevenDaysOrder = orderList.stream()
                .filter(order -> ChronoUnit.DAYS.between(order.getOrderedDate(), dateOfToday) <= 7)
                .collect(Collectors.toList());

        System.out.println(" List of orders placed in the last 7 days :");
        pastSevenDaysOrder.forEach(System.out::println);

        //8th(b)
//        double totalRevenue = orderList.stream()
//                .filter(order -> STATUS_COMPLETED.equals(order.getStatus()))
//                .mapToDouble(Order::getTotalAmount)
//                .sum();
        //using reduce
        double totalRevenue = orderList.stream()
                .filter(order ->STATUS_COMPLETED.equals(order.getStatus()))
                .map(Order::getTotalAmount)
                .reduce(0.0, Double::sum);

        System.out.println(totalRevenue);

       // 8th(c)
        List<String> placedOrderAboveThresholdByCustomer = orderList.stream()
                .filter(order -> order.getTotalAmount() > 5000)
                .map(Order::getCustomerName)
                .collect(Collectors.toList());
        System.out.println(placedOrderAboveThresholdByCustomer);


    }
}
