package streams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    OrderService ordersService = new OrderService() ;
    Product product;
    Order order;

    @BeforeEach
    public void init(){

        Product p1 = new Product("Tv","IT",2000);
        Product p2 = new Product("Laptop","IT",2400);
        Product p3 = new Product("Phone","IT",400);
        Product p4 = new Product("Lord of The Rings","Book",20);
        Product p5 = new Product("Harry Potter Collection","Book",120);

        Order o1 = new Order("pending", LocalDate.of(2021,06,07));
        o1.addProduct(p1);
        o1.addProduct(p2);
        o1.addProduct(p5);

        Order o2 = new Order("on delivery", LocalDate.of(2021,06,01));
        o2.addProduct(p3);
        o2.addProduct(p1);
        o2.addProduct(p2);

        Order o3 = new Order("pending", LocalDate.of(2021,06,07));
        o3.addProduct(p1);
        o3.addProduct(p2);
        o3.addProduct(p5);

        Order o4 = new Order("on delivery", LocalDate.of(2021,06,01));
        o4.addProduct(p3);
        o4.addProduct(p1);
        o4.addProduct(p2);

        Order o5 = new Order("pending", LocalDate.of(2021,06,07));
        o5.addProduct(p1);
        o5.addProduct(p2);
        o5.addProduct(p5);

        ordersService.saveOrder(o1);
        ordersService.saveOrder(o2);
        ordersService.saveOrder(o3);
        ordersService.saveOrder(o4);
        ordersService.saveOrder(o5);

        product = p3;
        order = o1;
    }

    @Test
    void testCountOrderByStatus() {
        Assertions.assertEquals(3, ordersService.countOrderByStatus("pending"));
    }

    @Test
    void testCollectOrdersWithProductCategory() {
        Assertions.assertEquals(3, ordersService.collectOrdersWithProductCategory("Book").size());
    }

    @Test
    void testCollectProductPriceHigherThanParameter() {
        Assertions.assertEquals(10, ordersService.collectProductPriceHigherThanParameter(1800).size());
    }

    @Test
    void testCountPricesBetweenTwoDates() {
        Assertions.assertEquals(9600, ordersService.countPricesBetweenTwoDates(LocalDate.of(2021, 5, 31), LocalDate.of(2021, 6, 2)));
        Assertions.assertEquals(13560, ordersService.countPricesBetweenTwoDates(LocalDate.of(2021, 6, 8), LocalDate.of(2021, 6, 6)));
    }

    @Test
    void testGetExactProductByName() {
        Assertions.assertEquals(product, ordersService.getExactProductByName("Phone"));
    }

    @Test
    void testGetExactProductByNameNoSuchProduct() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> ordersService.getExactProductByName("Számítógép"));
        Assertions.assertEquals("No such product.", ex.getMessage());
    }

    @Test
    void testGetOrderWithMostExpensiveProduct() {
        Assertions.assertEquals(order, ordersService.getOrderWithMostExpensiveProduct());
    }
}