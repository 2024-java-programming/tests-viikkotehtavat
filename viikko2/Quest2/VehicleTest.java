package oop;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.junit.jupiter.api.*;

@DisplayName("Vehicle Tests")
public class VehicleTest {
    private static MethodHandle setFuelUsedHandle;
    private static MethodHandle getFuelConsumptionHandle;

    @BeforeEach
    public void setUp() throws Exception {
        setFuelUsedHandle = MethodHandles.lookup().findVirtual(Vehicle.class, "setFuelUsed", MethodType.methodType(void.class, Double.class));
        getFuelConsumptionHandle = MethodHandles.lookup().findVirtual(Vehicle.class, "getFuelConsumption", MethodType.methodType(Double.class));
    }

    @Test
    @DisplayName("Test fuel consumption with constructor")
    public void testConstructor() throws Throwable {
        Vehicle v = new Vehicle(150.0);
        setFuelUsedHandle.invoke(v, 7.6);
        Double actual = (Double) getFuelConsumptionHandle.invoke(v);
        assertEquals(5.1, actual, 0.1, "Fuel consumption from constructor is incorrect");
    }

    @Test
    @DisplayName("Test fuel consumption with setters")
    public void testDoubleFuelConsumption() throws Throwable {
        Vehicle v = new Vehicle(114.0);
        setFuelUsedHandle.invoke(v, 6.4);
        Double actual = (Double) getFuelConsumptionHandle.invoke(v);
        assertEquals(5.6, actual, 0.1, "Fuel consumption with setters is incorrect");
    }

    @Test
    @DisplayName("Test zero distance driven")
    public void testZeroDistance() throws Throwable {
        Vehicle v = new Vehicle(0.0);
        setFuelUsedHandle.invoke(v, 5.0);
        Double actual = (Double) getFuelConsumptionHandle.invoke(v);
        assertTrue(Double.isInfinite(actual) || actual == 0.0, "Fuel consumption with zero distance should be infinite or 0");
    }
}
