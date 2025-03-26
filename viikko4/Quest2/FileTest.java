package exercises;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.*;

@DisplayName("Media Player System")
public class FileTest {
    private static MethodHandle playHandle;
    private static MethodHandle pauseHandle;
    private static MethodHandle stopHandle;

    @BeforeEach
    public void setUp() throws Exception {
        assertTrue(Modifier.isInterface(Playable.class.getModifiers()), "Playable must be an interface");

        try {
            Class<?> vClass = Class.forName("exercises.VideoFile");
            MethodType type = MethodType.methodType(String.class);
            playHandle = MethodHandles.lookup().findVirtual(vClass, "play", type);
            type = MethodType.methodType(String.class, Integer.class);
            pauseHandle = MethodHandles.lookup().findVirtual(vClass, "pause", type);
            type = MethodType.methodType(String.class);
            stopHandle = MethodHandles.lookup().findVirtual(vClass, "stop", type);
        } catch (Exception e) {
            fail("VideoFile class is missing or methods are incorrectly declared: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test VideoFile playback functionality")
    public void testPlayableInterface() throws Throwable {
        VideoFile v = new VideoFile();
        v.fileName = "Ludwig van Beethoven - Ode an die Freude.mp4"; // Assuming public field as per README

        String expected = "Playing videofile: Ludwig van Beethoven - Ode an die Freude.mp4";
        assertEquals(expected, playHandle.invoke(v), "play() method output is incorrect");

        expected = "Videofile Ludwig van Beethoven - Ode an die Freude.mp4 paused for 20 seconds";
        assertEquals(expected, pauseHandle.invoke(v, 20), "pause() method output is incorrect");

        expected = "Videofile Ludwig van Beethoven - Ode an die Freude.mp4 stopped";
        assertEquals(expected, stopHandle.invoke(v), "stop() method output is incorrect");

        v.fileName = "Sash! - La Primavera.mp4";
        expected = "Videofile Sash! - La Primavera.mp4 stopped";
        assertEquals(expected, stopHandle.invoke(v), "stop() method with different file is incorrect");
    }

    @Test
    @DisplayName("Test pause with negative seconds")
    public void testPauseEdgeCase() throws Throwable {
        VideoFile v = new VideoFile();
        v.fileName = "Test Video.mp4";
        String expected = "Videofile Test Video.mp4 paused for 0 seconds"; // Assuming reasonable default
        assertEquals(expected, pauseHandle.invoke(v, -5), "pause() should handle negative seconds gracefully");
    }
}
