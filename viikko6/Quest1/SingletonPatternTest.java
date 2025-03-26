package designpatterns;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.*;

@DisplayName("Singleton Pattern Tests")
public class SingletonPatternTest {
    private static MethodHandle logHandle;
    private static Method getInstanceMethod;

    @BeforeEach
    public void setUp() throws Exception {
        Class<?> loggerClass = Logger.class;
        getInstanceMethod = loggerClass.getMethod("getInstance");
        assertTrue(Modifier.isStatic(getInstanceMethod.getModifiers()), "getInstance() must be static");
        logHandle = MethodHandles.lookup().findVirtual(loggerClass, "log", MethodType.methodType(String.class, String.class));
    }

    @Test
    @DisplayName("Test Singleton behavior")
    public void testSingletonPattern() throws Throwable {
        Logger l1 = (Logger) getInstanceMethod.invoke(null);
        Logger l2 = (Logger) getInstanceMethod.invoke(null);
        assertSame(l1, l2, "Logger instances should be the same (singleton)");
    }

    @Test
    @DisplayName("Test logging functionality")
    public void testLogging() throws Throwable {
        Logger logger = (Logger) getInstanceMethod.invoke(null);
        assertEquals("Logger: Testing", logHandle.invoke(logger, "Testing"), "log() output is incorrect");
        assertEquals("Logger: ", logHandle.invoke(logger, ""), "log() with empty message is incorrect");
    }

    @Test
    @DisplayName("Test logging with null message")
    public void testNullMessage() throws Throwable {
        Logger logger = (Logger) getInstanceMethod.invoke(null);
        assertEquals("Logger: null", logHandle.invoke(logger, (String) null), "log() should handle null message gracefully");
    }
}
