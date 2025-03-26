package exercises;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.*;

@DisplayName("Shape Drawing System")
public class ShapeTest {
    private static MethodHandle circleAreaHandle;
    private static MethodHandle rectangleAreaHandle;

    @BeforeEach
    public void setUp() throws Exception {
        assertTrue(Modifier.isAbstract(Shape.class.getModifiers()), "Shape must be an abstract class");
        assertEquals("exercises.Shape", Circle.class.getSuperclass().getName(), "Circle must extend Shape");
        assertEquals("exercises.Shape", Rectangle.class.getSuperclass().getName(), "Rectangle must extend Shape");

        try {
            Class<?> circleClass = Class.forName("exercises.Circle");
            circleAreaHandle = MethodHandles.lookup().findVirtual(circleClass, "calculateArea", MethodType.methodType(Double.class));

            Class<?> rectangleClass = Class.forName("exercises.Rectangle");
            rectangleAreaHandle = MethodHandles.lookup().findVirtual(rectangleClass, "calculateArea", MethodType.methodType(Double.class));
        } catch (Exception e) {
            fail("Shape classes or calculateArea() methods are missing: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Circle Area Calculation")
    public void testCircleArea() throws Throwable {
        Circle circle = new Circle("Circle", 5.0);
        assertEquals(Math.PI * 25.0, circleAreaHandle.invoke(circle), "Circle area calculation is incorrect");

        circle = new Circle("Circle", 7.0);
        assertEquals(Math.PI * 49.0, circleAreaHandle.invoke(circle), "Circle area calculation is incorrect");
    }

    @Test
    @DisplayName("Test Rectangle Area Calculation")
    public void testRectangleArea() throws Throwable {
        Rectangle rectangle = new Rectangle("Rectangle", 4.0, 6.0);
        assertEquals(24.0, rectangleAreaHandle.invoke(rectangle), "Rectangle area calculation is incorrect");

        rectangle = new Rectangle("Rectangle", 4.7, 6.2);
        assertEquals(4.7 * 6.2, rectangleAreaHandle.invoke(rectangle), "Rectangle area calculation is incorrect");
    }

    @Test
    @DisplayName("Test Circle Area with Zero Radius")
    public void testCircleAreaZeroRadius() throws Throwable {
        Circle circle = new Circle("Circle", 0.0);
        assertEquals(0.0, circleAreaHandle.invoke(circle), "Circle area with zero radius is incorrect");
    }

    @Test
    @DisplayName("Test Rectangle with Negative Dimensions")
    public void testRectangleNegativeDimensions() throws Throwable {
        Rectangle rectangle = new Rectangle("Rectangle", -4.0, 6.0);
        assertEquals(0.0, rectangleAreaHandle.invoke(rectangle), "Rectangle should handle negative dimensions gracefully (e.g., return 0)");
    }
}
