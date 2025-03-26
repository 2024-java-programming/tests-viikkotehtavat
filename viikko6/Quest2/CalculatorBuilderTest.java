package designpatterns;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.junit.jupiter.api.*;

@DisplayName("Calculator Builder Pattern Tests")
public class CalculatorBuilderTest {
    private static MethodHandle addHandle;
    private static MethodHandle subHandle;
    private static MethodHandle mulHandle;
    private static MethodHandle divHandle;
    private static MethodHandle getResultHandle;

    @BeforeEach
    public void setUp() throws Exception {
        Class<?> clazz = Calculator.class;
        addHandle = MethodHandles.lookup().findVirtual(clazz, "add", MethodType.methodType(Calculator.class, Double.class));
        subHandle = MethodHandles.lookup().findVirtual(clazz, "subtract", MethodType.methodType(Calculator.class, Double.class));
        mulHandle = MethodHandles.lookup().findVirtual(clazz, "multiply", MethodType.methodType(Calculator.class, Double.class));
        divHandle = MethodHandles.lookup().findVirtual(clazz, "divide", MethodType.methodType(Calculator.class, Double.class));
        getResultHandle = MethodHandles.lookup().findVirtual(clazz, "getResult", MethodType.methodType(Double.class));
    }

    @Test
    @DisplayName("Test arithmetic operation chaining")
    public void testOperations() throws Throwable {
        Calculator c = new Calculator(1.7);
        c = (Calculator) addHandle.invoke(c, 4.3);
        c = (Calculator) addHandle.invoke(c, 5.9);
        c = (Calculator) divHandle.invoke(c, 1.23);
        c = (Calculator) subHandle.invoke(c, 3.2);
        Double actual = (Double) getResultHandle.invoke(c);
        Double expected = ((1.7 + 4.3 + 5.9) / 1.23) - 3.2; // ~6.4748
        assertEquals(expected, actual, 0.0001, "Arithmetic operation chain 1 returned incorrect result");

        c = new Calculator(103.45);
        c = (Calculator) addHandle.invoke(c, 4.3);
        c = (Calculator) mulHandle.invoke(c, 5.9);
        c = (Calculator) subHandle.invoke(c, 1.23);
        c = (Calculator) divHandle.invoke(c, 3.2);
        actual = (Double) getResultHandle.invoke(c);
        expected = (((103.45 + 4.3) * 5.9) - 1.23) / 3.2; // ~198.2797
        assertEquals(expected, actual, 0.0001, "Arithmetic operation chain 2 returned incorrect result");
    }

    @Test
    @DisplayName("Test division by zero")
    public void testDivisionByZero() throws Throwable {
        Calculator c = new Calculator(5.0);
        assertThrows(Throwable.class, () -> divHandle.invoke(c, 0.0), "divide() should throw an exception for zero divisor");
    }
}
