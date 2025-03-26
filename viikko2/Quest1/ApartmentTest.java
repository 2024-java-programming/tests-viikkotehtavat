package oop;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.junit.jupiter.api.*;

@DisplayName("Apartment Tests")
public class ApartmentTest {
    private static MethodHandle heatingCostHandle;

    @BeforeEach
    public void setUp() throws Exception {
        heatingCostHandle = MethodHandles.lookup().findVirtual(Apartment.class, "heatingCost", MethodType.methodType(Float.class, Float.class));
    }

    @Test
    @DisplayName("Test heating cost calculation")
    public void testHeatingCost() throws Throwable {
        Apartment apartment = new Apartment(3, 100);
        Float actual = (Float) heatingCostHandle.invoke(apartment, 0.1f);
        assertEquals(30.0f, actual, 0.001f, "Heating cost should be 30.0 for 3 tenants, 100 area, 0.1 price");
    }

    @Test
    @DisplayName("Test heating cost with zero tenants")
    public void testHeatingCostWithZeroTenants() throws Throwable {
        Apartment apartment = new Apartment(0, 100);
        Float actual = (Float) heatingCostHandle.invoke(apartment, 0.1f);
        assertEquals(0.0f, actual, 0.001f, "Heating cost should be 0.0 with zero tenants");
    }

    @Test
    @DisplayName("Test heating cost with area 44")
    public void testHeatingCostWithArea44() throws Throwable {
        Apartment apartment = new Apartment(3, 44);
        Float actual = (Float) heatingCostHandle.invoke(apartment, 0.1f);
        assertEquals(13.2f, actual, 0.001f, "Heating cost should be 13.2 for 3 tenants, 44 area, 0.1 price");
    }

    @Test
    @DisplayName("Test heating cost with negative tenants")
    public void testNegativeTenants() throws Throwable {
        Apartment apartment = new Apartment(-1, 100);
        Float actual = (Float) heatingCostHandle.invoke(apartment, 0.1f);
        assertEquals(0.0f, actual, 0.001f, "Heating cost should be 0.0 or handled for negative tenants");
    }

    @Test
    @DisplayName("Test heating cost with zero area")
    public void testZeroArea() throws Throwable {
        Apartment apartment = new Apartment(3, 0);
        Float actual = (Float) heatingCostHandle.invoke(apartment, 0.1f);
        assertEquals(0.0f, actual, 0.001f, "Heating cost should be 0.0 with zero area");
    }
}
