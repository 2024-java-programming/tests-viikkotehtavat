package oop;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.junit.jupiter.api.*;

@DisplayName("Truck Tests")
public class TruckTest {
    private static MethodHandle getDetailsHandle;
    private static MethodHandle setFuelUsedHandle;
    private static MethodHandle getFuelConsumptionHandle;

    @BeforeEach
    public void setUp() throws Exception {
        getDetailsHandle = MethodHandles.lookup().findVirtual(Truck.class, "getDetails", MethodType.methodType(String.class));
        setFuelUsedHandle = MethodHandles.lookup().findVirtual(Truck.class, "setFuelUsed", MethodType.methodType(void.class, Double.class));
        getFuelConsumptionHandle = MethodHandles.lookup().findVirtual(Truck.class, "getFuelConsumption", MethodType.methodType(Double.class));
    }

    @Test
    @DisplayName("Test Truck inheritance")
    public void testInheritance() {
        assertEquals(Vehicle.class, Truck.class.getSuperclass(), "Truck should inherit from Vehicle");
    }

    @Test
    @DisplayName("Test truck details")
    public void testTruckDetails() throws Throwable {
        Truck t = new Truck("Ford", "F-150", 2021, 12005.5);
        String actual = (String) getDetailsHandle.invoke(t);
        assertEquals("2021 Ford F-150", actual, "Truck details format is incorrect");
    }

    @Test
    @DisplayName("Test fuel consumption")
    public void testFuelConsumption() throws Throwable {
        Truck t = new Truck("Ford", "F-150", 2021, 12005.5);
        setFuelUsedHandle.invoke(t, 1656.0);
        Double actual = (Double) getFuelConsumptionHandle.invoke(t);
        assertEquals(13.8, actual, 0.1, "Fuel consumption calculation is incorrect");
    }

    @Test
    @DisplayName("Test zero distance driven")
    public void testZeroDistance() throws Throwable {
        Truck t = new Truck("Ford", "F-150", 2021, 0.0);
        setFuelUsedHandle.invoke(t, 10.0);
        Double actual = (Double) getFuelConsumptionHandle.invoke(t);
        assertTrue(Double.isInfinite(actual) || actual == 0.0, "Fuel consumption with zero distance should be infinite or 0");
    }
}
