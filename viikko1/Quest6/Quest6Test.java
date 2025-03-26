package java_basics;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

@DisplayName("Quest 6: Fibonacci Sequence")
public class Quest6Test {
    private Quest6 q6;

    @BeforeEach
    public void init() {
        q6 = new Quest6();
    }

    @Test
    @DisplayName("Test first element (index 0)")
    public void testFirst() {
        assertEquals(0, q6.fibonacci(0), "Fibonacci(0) should be 0");
    }

    @Test
    @DisplayName("Test second element (index 1)")
    public void testSecond() {
        assertEquals(1, q6.fibonacci(1), "Fibonacci(1) should be 1");
    }

    @Test
    @DisplayName("Test 32nd element (index 31)")
    public void testThirtyTwo() {
        assertEquals(1346269, q6.fibonacci(31), "Fibonacci(31) should be 1346269");
    }

    @Test
    @DisplayName("Test 36th element (index 35)")
    public void testThirtySix() {
        assertEquals(9227465, q6.fibonacci(35), "Fibonacci(35) should be 9227465");
    }

    @Test
    @DisplayName("Test negative index")
    public void testNegative() {
        assertEquals(0, q6.fibonacci(-1), "Fibonacci(-1) should return 0 or handle gracefully");
    }

    @Test
    @DisplayName("Test null index")
    public void testNull() {
        assertEquals(0, q6.fibonacci(null), "Fibonacci(null) should return 0 or handle gracefully");
    }
}
