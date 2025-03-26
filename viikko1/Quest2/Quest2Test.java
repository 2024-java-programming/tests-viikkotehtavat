package java_basics;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.*;

@DisplayName("Quest 2: Number Sign Check")
public class Quest2Test {
    private static Quest2 quest2;
    private static ByteArrayOutputStream outContent;

    @BeforeEach
    public void init() {
        quest2 = new Quest2();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void reset() {
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Test positive number")
    public void testPositiveNumber() {
        quest2.checkNum(5);
        assertEquals("pos" + System.lineSeparator(), outContent.toString(), "Should output 'pos' for positive number");
    }

    @Test
    @DisplayName("Test negative number")
    public void testNegativeNumber() {
        quest2.checkNum(-5);
        assertEquals("neg" + System.lineSeparator(), outContent.toString(), "Should output 'neg' for negative number");
    }

    @Test
    @DisplayName("Test zero")
    public void testZero() {
        quest2.checkNum(0);
        assertEquals("zero" + System.lineSeparator(), outContent.toString(), "Should output 'zero' for zero");
    }

    @Test
    @DisplayName("Test null number")
    public void testNullNumber() {
        quest2.checkNum(null);
        assertEquals("zero" + System.lineSeparator(), outContent.toString(), "Should output 'zero' for null");
    }
}
