package java_basics;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.*;

@DisplayName("Quest 3: Visitor Greeting")
public class Quest3Test {
    private static Quest3 quest3;
    private static ByteArrayOutputStream outContent;

    @BeforeEach
    public void init() {
        quest3 = new Quest3();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void reset() {
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Test greeting two visitors")
    public void testGreetTwo() {
        quest3.greet(2);
        assertEquals("Hello 1!" + System.lineSeparator() + "Hello 2!" + System.lineSeparator(), outContent.toString(),
            "Should greet 2 visitors correctly");
    }

    @Test
    @DisplayName("Test greeting five visitors")
    public void testGreetFive() {
        quest3.greet(5);
        assertEquals("Hello 1!" + System.lineSeparator() + "Hello 2!" + System.lineSeparator() +
            "Hello 3!" + System.lineSeparator() + "Hello 4!" + System.lineSeparator() +
            "Hello 5!" + System.lineSeparator(), outContent.toString(), "Should greet 5 visitors correctly");
    }

    @Test
    @DisplayName("Test zero visitors")
    public void testZeroVisitors() {
        quest3.greet(0);
        assertEquals("", outContent.toString(), "Should print nothing for zero visitors");
    }

    @Test
    @DisplayName("Test negative visitors")
    public void testNegativeVisitors() {
        quest3.greet(-1);
        assertEquals("", outContent.toString(), "Should print nothing for negative visitors");
    }
}
