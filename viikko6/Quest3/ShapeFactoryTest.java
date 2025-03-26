package designpatterns;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.*;

@DisplayName("Shape Factory Pattern Tests")
public class ShapeFactoryTest {
    private static MethodHandle getAreaHandle;
    private static MethodHandle getParamsHandle;
    private static Method createMethod;

    @BeforeEach
    public void setUp() throws Exception {
        assertTrue(Modifier.isInterface(Shape.class.getModifiers()), "Shape must be an interface");
        getAreaHandle = MethodHandles.lookup().findVirtual(Shape.class, "getArea", MethodType.methodType(Double.class));
        getParamsHandle = MethodHandles.lookup().findVirtual(Shape.class, "getParams", MethodType.methodType(Double[].class));

        Class<?> factoryClass = ShapeFactory.class;
        createMethod = factoryClass.getMethod("create", String.class, Double[].class);
        assertTrue(Modifier.isStatic(createMethod.getModifiers()), "create() must be static");
    }

    @Test
    @DisplayName("Test Circle implementation")
    public void testCircleClass() throws Throwable {
        assertTrue(Shape.class.isAssignableFrom(Circle.class), "Circle must implement Shape");
        
        Double[] params = {5.0};
        Circle c = new Circle(params);
        assertArrayEquals(params, (Double[]) getParamsHandle.invoke(c), "getParams() for Circle is incorrect");
        assertEquals(Math.PI * 25.0, (Double) getAreaHandle.invoke(c), 0.0001, "getArea() for Circle is incorrect");

        assertThrows(IllegalArgumentException.class, () -> new Circle(new Double[]{6.7, 3.4}), 
            "Circle constructor should throw IllegalArgumentException for wrong number of params");
        assertThrows(IllegalArgumentException.class, () -> new Circle(new Double[]{}), 
            "Circle constructor should throw IllegalArgumentException for empty params");
    }

    @Test
    @DisplayName("Test Rectangle implementation")
    public void testRectangleClass() throws Throwable {
        assertTrue(Shape.class.isAssignableFrom(Rectangle.class), "Rectangle must implement Shape");

        Double[] params = {5.0, 7.0};
        Rectangle r = new Rectangle(params);
        assertArrayEquals(params, (Double[]) getParamsHandle.invoke(r), "getParams() for Rectangle is incorrect");
        assertEquals(35.0, (Double) getAreaHandle.invoke(r), 0.0001, "getArea() for Rectangle is incorrect");

        assertThrows(IllegalArgumentException.class, () -> new Rectangle(new Double[]{4.4, 6.7, 3.4}), 
            "Rectangle constructor should throw IllegalArgumentException for wrong number of params");
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(new Double[]{}), 
            "Rectangle constructor should throw IllegalArgumentException for empty params");
    }

    @Test
    @DisplayName("Test ShapeFactory creation")
    public void testShapeFactory() throws Throwable {
        Double[] circleParams = {2.5};
        Shape circle = (Shape) createMethod.invoke(null, "circle", circleParams);
        assertEquals(Math.PI * 2.5 * 2.5, (Double) getAreaHandle.invoke(circle), 0.0001, "ShapeFactory created Circle with incorrect area");

        Double[] rectParams = {9.14, 8.76};
        Shape rect = (Shape) createMethod.invoke(null, "rectangle", rectParams);
        assertEquals(9.14 * 8.76, (Double) getAreaHandle.invoke(rect), 0.0001, "ShapeFactory created Rectangle with incorrect area");
    }

    @Test
    @DisplayName("Test negative parameters")
    public void testNegativeParams() throws Throwable {
        Double[] params = {-2.5};
        Shape circle = (Shape) createMethod.invoke(null, "circle", params);
        assertEquals(0.0, (Double) getAreaHandle.invoke(circle), 0.0001, "Circle with negative radius should return 0 area");

        params = new Double[]{-1.0, 2.0};
        Shape rect = (Shape) createMethod.invoke(null, "rectangle", params);
        assertEquals(0.0, (Double) getAreaHandle.invoke(rect), 0.0001, "Rectangle with negative width should return 0 area");
    }
}
