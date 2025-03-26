package oamk.stream;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.*;

@DisplayName("Stock Tests")
public class StockTest {
    private static MethodHandle formatProductHandle;
    private static MethodHandle getProductsHandle;
    private static MethodHandle addProductHandle;
    private static MethodHandle toStringHandle;
    private static MethodHandle reportExpiredHandle;
    private static Constructor<?> sConstructor;
    private static Constructor<?> pConstructor;

    @BeforeEach
    public void setUp() throws Exception {
        assertTrue(Modifier.isInterface(OutputFormatter.class.getModifiers()), "OutputFormatter must be an interface");
        assertTrue(Product.class.isRecord(), "Product must be a record");
        assertTrue(OutputFormatter.class.isAssignableFrom(Product.class), "Product must implement OutputFormatter");

        pConstructor = Product.class.getConstructor(String.class, Double.class, String.class, LocalDateTime.class);
        formatProductHandle = MethodHandles.lookup().findVirtual(Product.class, "formatProduct", MethodType.methodType(String.class, DateTimeFormatter.class));

        Class<?> sclazz = Stock.class;
        sConstructor = sclazz.getConstructor();
        getProductsHandle = MethodHandles.lookup().findVirtual(sclazz, "getProducts", MethodType.methodType(List.class));
        addProductHandle = MethodHandles.lookup().findVirtual(sclazz, "addProduct", MethodType.methodType(void.class, Product.class));
        toStringHandle = MethodHandles.lookup().findVirtual(sclazz, "toString", MethodType.methodType(String.class));
        reportExpiredHandle = MethodHandles.lookup().findVirtual(sclazz, "reportExpired", MethodType.methodType(List.class, LocalDateTime.class));
    }

    @Test
    @DisplayName("Test Stock management methods")
    public void testStockMethods() throws Throwable {
        Stock s = new Stock();
        Product p1 = new Product("car", 15000.0, "vehicles", LocalDateTime.of(2060, 12, 31, 23, 59, 59));
        Product p2 = new Product("carrot", 2.99, "vegetables", LocalDateTime.of(2025, 4, 15, 23, 59, 34));
        Product p3 = new Product("beans", 4.99, "cannedfood", LocalDateTime.of(2032, 4, 15, 23, 59, 34));

        addProductHandle.invoke(s, p1);
        addProductHandle.invoke(s, p2);
        addProductHandle.invoke(s, p3);

        List<Product> expected = new LinkedList<>(List.of(p1, p2, p3));
        assertEquals(expected, getProductsHandle.invoke(s), "addProduct or getProducts is incorrect");

        String expectedStr = "List of Products\n" +
                             "----------------\n" +
                             "[car,vehicles,15000.00,2060.12.31]\n" +
                             "[carrot,vegetables,2.99,2025.04.15]\n" +
                             "[beans,cannedfood,4.99,2032.04.15]\n" +
                             "-> Total products: 3\n" +
                             "-> Total price: 15007.98\n";
        assertEquals(expectedStr, toStringHandle.invoke(s), "toString format is incorrect");
    }

    @Test
    @DisplayName("Test Stock expiration reporting with streams")
    public void testStockStream() throws Throwable {
        Stock s = new Stock();
        Product p1 = new Product("fish", 2.99, "food", LocalDateTime.of(2024, 8, 31, 23, 59, 59));
        Product p2 = new Product("fish", 2.99, "food", LocalDateTime.of(2024, 10, 30, 23, 59, 34));
        addProductHandle.invoke(s, p1);
        addProductHandle.invoke(s, p2);

        LocalDateTime checkDate = LocalDateTime.of(2024, 9, 1, 0, 0, 0);
        List<Product> expected = new LinkedList<>(List.of(p1));
        assertEquals(expected, reportExpiredHandle.invoke(s, checkDate), "reportExpired should return expired products");
    }

    @Test
    @DisplayName("Test empty Stock")
    public void testEmptyStock() throws Throwable {
        Stock s = new Stock();
        assertEquals(new LinkedList<>(), getProductsHandle.invoke(s), "Empty stock should return empty list");
        String expectedStr = "List of Products\n" +
                             "----------------\n" +
                             "-> Total products: 0\n" +
                             "-> Total price: 0.00\n";
        assertEquals(expectedStr, toStringHandle.invoke(s), "toString for empty stock is incorrect");
    }
}
