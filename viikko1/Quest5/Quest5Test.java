package java_basics;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.*;

@DisplayName("Quest 5: Factorial")
public class Quest5Test {
    private static Quest5 quest5;
    private static ByteArrayOutputStream outContent;

    @BeforeEach
    public void init() {
        quest5 = new Quest5();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void reset() {
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Test factorial of 5")
    public void testFactorialFive() {
        quest5.factorial(5);
        assertEquals("120" + System.lineSeparator(), outContent.toString(), "Factorial of 5 should be 120");
    }

    @Test
    @DisplayName("Test factorial of 0")
    public void testFactorialZero() {
        quest5.factorial(0);
        assertEquals("not allowed" + System.lineSeparator(), outContent.toString(), "Should output 'not allowed' for 0");
    }

    @Test
    @DisplayName("Test factorial of 21")
    public void testFactorialTwentyOne() {
        quest5.factorial(21);
        assertEquals("not allowed" + System.lineSeparator(), outContent.toString(), "Should output 'not allowed' for 21");
    }

    @Test
    @DisplayName("Test factorial of -1")
    public void testFactorialNegativeOne() {
        quest5.factorial(-1);
        assertEquals("not allowed" + System.lineSeparator(), outContent.toString(), "Should output 'not allowed' for -1");
    }

    @Test
    @DisplayName("Test factorial of 12")
    public void testFactorialTwelve() {
        quest5.factorial(12);
        assertEquals("479001600" + System.lineSeparator(), outContent.toString(), "Factorial of 12 should be 479001600");
    }

    @Test
    @DisplayName("Test factorial of 20")
    public void testFactorialTwenty() {
        quest5.factorial(20);
        assertEquals("2432902008176640000" + System.lineSeparator(), outContent.toString(), "Factorial of 20 should be 2432902008176640000");
    }
}
