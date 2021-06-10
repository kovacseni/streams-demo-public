package streams;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {

    private List<Order> orders = new ArrayList<>();


    public void saveOrder(Order order) {
        orders.add(order);
    }

    public long countOrderByStatus(String status) {
        return orders.stream()
                .filter(stat -> stat.getStatus().equals(status))
                .count();
    }

    public List<Order> collectOrdersWithProductCategory(String category) {
        return orders.stream()
                .filter(order -> order
                        .getProducts()
                        .stream()
                        .anyMatch(product -> product.getCategory().equals(category)))
                .collect(Collectors.toList());
    }

    public List<Product> collectProductPriceHigherThanParameter(int price) {
        return orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getPrice() > price)
                .collect(Collectors.toList());
    }

    public double countPricesBetweenTwoDates(LocalDate date1, LocalDate date2) {
        boolean date1IsBeforeThanDate2 = date1.isBefore(date2);
        if (date1IsBeforeThanDate2) {
            return orders.stream()
                    .filter(order -> order.getOrderDate().isAfter(date1) && order.getOrderDate().isBefore(date2))
                    .flatMap(order -> order.getProducts().stream())
                    .mapToDouble(Product::getPrice)
                    .sum();
        } else {
            return orders.stream()
                    .filter(order -> order.getOrderDate().isBefore(date1) && order.getOrderDate().isAfter(date2))
                    .flatMap(order -> order.getProducts().stream())
                    .mapToDouble(Product::getPrice)
                    .sum();
        }
    }

    public Product getExactProductByName(String name) {
        return orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such product."));
    }

    public Order getOrderWithMostExpensiveProduct() {
        double max = orders.stream().flatMap(order -> order.getProducts().stream())
                .max(Comparator.comparing(Product::getPrice))
                .stream()
                .mapToDouble(Product::getPrice)
                .max()
                .getAsDouble();

        return orders.stream()
                .filter(order -> order
                        .getProducts()
                        .stream()
                        .anyMatch(product -> product.getPrice() == max))
                .findFirst()
                .get();
    }
}
