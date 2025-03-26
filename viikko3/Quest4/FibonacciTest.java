package exercises;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Vector;

import org.junit.jupiter.api.*;

@DisplayName("Fibonacci")
public class FibonacciTest {
    private static MethodHandle getNumbersHandle;
    private static MethodHandle addNextHandle;
    private static MethodHandle compareHandle;
    private static MethodHandle isFibonacciHandle;

    @BeforeEach
    public void setUp() throws Exception {
        try {
            getNumbersHandle = MethodHandles.lookup().findVirtual(Fibonacci.class, "getNumbers", MethodType.methodType(Vector.class));
            addNextHandle = MethodHandles.lookup().findVirtual(Fibonacci.class, "addNext", MethodType.methodType(Integer.class));
            compareHandle = MethodHandles.lookup().findVirtual(Fibonacci.class, "compareSequence", MethodType.methodType(Boolean.class, Vector.class));
            isFibonacciHandle = MethodHandles.lookup().findVirtual(Fibonacci.class, "isFibonacci", MethodType.methodType(Boolean.class, Integer.class));
        } catch (NoSuchMethodException e) {
            fail("Fibonacci class is missing required methods");
        }
    }

    @Test
    @DisplayName("Test if constructor works with method getNumbers()")
    public void testConstructorWithGetNumbers() throws Throwable {
        Fibonacci fb = new Fibonacci(8);
        Vector<Integer> result = (Vector<Integer>) getNumbersHandle.invoke(fb);
        assertEquals(8, result.get(6), "6th index should be 8 for input 8");
        assertEquals(13, result.get(7), "7th index should be 13 for input 8");
    }

    @Test
    @DisplayName("Test if method addNext() works properly")
    public void testAddNext() throws Throwable {
        Fibonacci fb = new Fibonacci(18);
        Integer iresult = (Integer) addNextHandle.invoke(fb);
        assertEquals(1597, iresult, "18th Fibonacci number is not correct");
    }

    @Test
    @DisplayName("Test if method compareSequence() works properly")
    public void testCompareSequence() throws Throwable {
        Fibonacci fb = new Fibonacci(18);
        Vector<Integer> vec = new Vector<>(Arrays.asList(233, 377, 610, 987));
        Boolean result = (Boolean) compareHandle.invoke(fb, vec);
        assertTrue(result, "Sequence {233, 377, 610, 987} is a Fibonacci sequence");
    }

    @Test
    @DisplayName("Test if method isFibonacci() works properly")
    public void testIsFibonacci() throws Throwable {
        Fibonacci fb = new Fibonacci(10);
        Boolean result = (Boolean) isFibonacciHandle.invoke(fb, 267914296);
        assertTrue(result, "Number 267914296 is a Fibonacci number");
        result = (Boolean) isFibonacciHandle.invoke(fb, 14930252);
        assertFalse(result, "Number 14930252 is not a Fibonacci number");
    }
}
