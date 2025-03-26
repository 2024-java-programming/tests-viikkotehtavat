package java_basics;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.*;

@DisplayName("Quest 4: Even Numbers")
public class Quest4Test {
    private static Quest4 quest4;
    private static ByteArrayOutputStream outContent;

    @BeforeEach
    public void init() {
        quest4 = new Quest4();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void reset() {
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Test even numbers up to 9")
    public void testOddOut() {
        quest4.oddOut((short) 9);
        assertEquals("2,4,6,8", outContent.toString(), "Should print even numbers up to 9");
    }

    @Test
    @DisplayName("Test even numbers up to 0")
    public void testOddOutZero() {
        quest4.oddOut((short) 0);
        assertEquals("", outContent.toString(), "Should print nothing for limit 0");
    }

    @Test
    @DisplayName("Test even numbers up to 2")
    public void testOddOutTwo() {
        quest4.oddOut((short) 2);
        assertEquals("2", outContent.toString(), "Should print '2' for limit 2");
    }

    @Test
    @DisplayName("Test negative limit")
    public void testNegativeLimit() {
        quest4.oddOut((short) -5);
        assertEquals("", outContent.toString(), "Should print nothing for negative limit");
    }
}
