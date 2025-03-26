package oamk.stream;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.*;

@DisplayName("ReadProductFile Tests")
public class ReadProductTest {
    private static MethodHandle getProductsHandle;
    private static MethodHandle readCSVHandle;
    private static Constructor<?> rConstructor;
    private static Constructor<?> pConstructor;
    private static ByteArrayOutputStream errContent;

    @BeforeEach
    public void setUp() throws Exception {
        errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        assertTrue(Product.class.isRecord(), "Product must be a record");
        assertTrue(OutputFormatter.class.isAssignableFrom(Product.class), "Product must implement OutputFormatter");
        pConstructor = Product.class.getConstructor(String.class, Double.class, String.class, LocalDateTime.class);

        Class<?> rclazz = Class.forName("oamk.stream.ReadProductFile");
        rConstructor = rclazz.getConstructor(String.class);
        getProductsHandle = MethodHandles.lookup().findVirtual(rclazz, "getProducts", MethodType.methodType(List.class));
        readCSVHandle = MethodHandles.lookup().findVirtual(rclazz, "readCSV", MethodType.methodType(void.class));
    }

    @AfterEach
    public void tearDown() {
        System.setErr(System.err); // Reset stderr
    }

    @Test
    @DisplayName("Test constructor with nonexistent file")
    public void testConstructorFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ReadProductFile("nonexistent.csv");
        }, "Constructor should throw IllegalArgumentException for nonexistent file");
    }

    @Test
    @DisplayName("Test reading CSV with missing name (field 1)")
    public void testReadCSVField1() throws Throwable {
        ReadProductFile r = new ReadProductFile("src/test/test1.csv");
        readCSVHandle.invoke(r);
        List<Product> expected = new LinkedList<>();
        expected.add(new Product("shirt", 12.99, "clothes", LocalDateTime.of(2024, 11, 5, 19, 46, 0)));
        assertEquals(expected, getProductsHandle.invoke(r), "test1.csv should only include valid line");
        assertTrue(errContent.toString().contains("skipped line 2: missing parameter"), "Should log missing parameter on line 2");
    }

    @Test
    @DisplayName("Test reading CSV with missing price (field 2)")
    public void testReadCSVField2() throws Throwable {
        ReadProductFile r = new ReadProductFile("src/test/test2.csv");
        readCSVHandle.invoke(r);
        assertEquals(new LinkedList<>(), getProductsHandle.invoke(r), "test2.csv should skip invalid line");
        assertTrue(errContent.toString().contains("skipped line 1: missing parameter"), "Should log missing parameter on line 1");
    }

    @Test
    @DisplayName("Test reading CSV with missing category (field 3)")
    public void testReadCSVField3() throws Throwable {
        ReadProductFile r = new ReadProductFile("src/test/test3.csv");
        readCSVHandle.invoke(r);
        List<Product> expected = new LinkedList<>();
        expected.add(new Product("icecream", 3.49, "food", LocalDateTime.of(2024, 9, 28, 19, 46, 0)));
        expected.add(new Product("icecream", 3.49, "food", LocalDateTime.of(2024, 9, 28, 19, 47, 0)));
        assertEquals(expected, getProductsHandle.invoke(r), "test3.csv should include only valid lines");
        assertTrue(errContent.toString().contains("skipped line 3: missing parameter"), "Should log missing parameter on line 3");
    }

    @Test
    @DisplayName("Test reading CSV with missing date (field 4)")
    public void testReadCSVField4() throws Throwable {
        ReadProductFile r = new ReadProductFile("src/test/test4.csv");
        readCSVHandle.invoke(r);
        List<Product> expected = new LinkedList<>();
        expected.add(new Product("icecream", 3.49, "food", LocalDateTime.of(2024, 9, 28, 19, 46, 0)));
        expected.add(new Product("icecream", 3.49, "food", LocalDateTime.of(2024, 9, 28, 19, 47, 0)));
        expected.add(new Product("icecream", 3.49, "food", LocalDateTime.of(2024, 9, 28, 19, 49, 0)));
        assertEquals(expected, getProductsHandle.invoke(r), "test4.csv should include only valid lines");
        assertTrue(errContent.toString().contains("skipped line 3: missing parameter"), "Should log missing parameter on line 3");
    }

    @Test
    @DisplayName("Test reading fully valid CSV")
    public void testReadValidCSV() throws Throwable {
        ReadProductFile r = new ReadProductFile("products.csv");
        readCSVHandle.invoke(r);
        List<Product> expected = new LinkedList<>();
        expected.add(new Product("icecream", 3.49, "food", LocalDateTime.of(2024, 9, 28, 19, 46, 0)));
        expected.add(new Product("icecream", 3.49, "food", LocalDateTime.of(2024, 9, 28, 19, 47, 0)));
        expected.add(new Product("icecream", 3.49, "food", LocalDateTime.of(2024, 9, 28, 19, 48, 0)));
        assertEquals(expected, getProductsHandle.invoke(r), "products.csv should read all lines correctly");
        assertEquals("", errContent.toString(), "No errors should be logged for valid CSV");
    }
}
