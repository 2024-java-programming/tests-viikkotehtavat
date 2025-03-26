package oop;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.junit.jupiter.api.*;

@DisplayName("Shape Hierarchy Tests")
public class ShapeTest {
    private static MethodHandle getAreaHandle;

    @BeforeEach
    public void setUp() throws Exception {
        getAreaHandle = MethodHandles.lookup().findVirtual(Shape.class, "getArea", MethodType.methodType(Double.class));
    }

    @Test
    @DisplayName("Test Shape inheritance")
    public void testInheritance() {
        assertEquals(Object.class, Shape.class.getSuperclass(), "Shape should inherit from Object");
        assertEquals(Shape.class, Circle.class.getSuperclass(), "Circle should inherit from Shape");
        assertEquals(Shape.class, Rectangle.class.getSuperclass(), "Rectangle should inherit from Shape");
    }

    @Test
    @DisplayName("Test class name printing")
    public void testName() throws Throwable {
        Shape s = new Shape("shape");
        assertEquals("shape", s.toString(), "Shape toString() is incorrect");

        Rectangle r = new Rectangle("rectangle", 5.0, 5.0);
        assertEquals("rectangle", r.toString(), "Rectangle toString() is incorrect");

        Circle c = new Circle("circle", 5.0);
        assertEquals("circle", c.toString(), "Circle toString() is incorrect");
    }

    @Test
    @DisplayName("Test Rectangle area calculation")
    public void testRectangleArea() throws Throwable {
        Rectangle r = new Rectangle("rectangle", 3.0, 4.0);
        Double actual = (Double) getAreaHandle.invoke(r);
        assertEquals(12.0, actual, 0.0001, "Rectangle area calculation is incorrect");
    }

    @Test
    @DisplayName("Test Circle area calculation")
    public void testCircleArea() throws Throwable {
        Circle c = new Circle("circle", 2.0);
        Double actual = (Double) getAreaHandle.invoke(c);
        assertEquals(Math.PI * 4.0, actual, 0.0001, "Circle area calculation is incorrect");
    }

    @Test
    @DisplayName("Test negative dimensions")
    public void testNegativeDimensions() throws Throwable {
        Rectangle r = new Rectangle("rect", -3.0, 4.0);
        assertEquals(0.0, (Double) getAreaHandle.invoke(r), 0.0001, "Rectangle with negative width should return 0");

        Circle c = new Circle("circle", -2.0);
        assertEquals(0.0, (Double) getAreaHandle.invoke(c), 0.0001, "Circle with negative radius should return 0");
    }
}
