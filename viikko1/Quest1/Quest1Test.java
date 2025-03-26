package java_basics;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.*;

@DisplayName("Quest 1: Multiplication Sequence")
public class Quest1Test {
    private static Quest1 quest1;
    private static ByteArrayOutputStream outContent;

    @BeforeEach
    public void init() {
        quest1 = new Quest1();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void reset() {
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Test multiplying by zero")
    public void testMultiplyByZero() {
        quest1.multiply(5, 0);
        assertEquals("1" + System.lineSeparator(), outContent.toString(), "Should output 1 for (5 * 0) * 2 + 1");
    }

    @Test
    @DisplayName("Test multiplying two numbers")
    public void testMultiply() {
        quest1.multiply(5, 3);
        assertEquals("31" + System.lineSeparator(), outContent.toString(), "Should output 31 for (5 * 3) * 2 + 1");
    }

    @Test
    @DisplayName("Test multiplying with negative number")
    public void testNegativeNumber() {
        quest1.multiply(5, -3);
        assertEquals("-29" + System.lineSeparator(), outContent.toString(), "Should output -29 for (5 * -3) * 2 + 1");
    }

    @Test
    @DisplayName("Test null parameter")
    public void testNullParameter() {
        quest1.multiply(null, 3);
        assertEquals("1" + System.lineSeparator(), outContent.toString(), "Should handle null as 0, output 1");
    }
}
