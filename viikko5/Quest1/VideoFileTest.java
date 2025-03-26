package oamk.stream;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.junit.jupiter.api.*;

@DisplayName("VideoFile Tests")
public class VideoFileTest {
    private static MethodHandle getVideoFileDataHandle;
    private static MethodHandle playHandle;
    private static MethodHandle pauseHandle;
    private static MethodHandle stopHandle;
    private static Constructor<?> vConstructor;
    private static Constructor<?> mConstructor;

    @BeforeEach
    public void setUp() throws Exception {
        assertTrue(Modifier.isInterface(Playable.class.getModifiers()), "Playable must be an interface");

        assertTrue(Metadata.class.isRecord(), "Metadata must be a record");
        mConstructor = Metadata.class.getConstructor(String.class, String.class, String.class);

        Class<?> vClass = VideoFile.class;
        assertTrue(Playable.class.isAssignableFrom(vClass), "VideoFile must implement Playable");
        vConstructor = vClass.getConstructor(String.class);
        getVideoFileDataHandle = MethodHandles.lookup().findVirtual(vClass, "getVideoFileData", MethodType.methodType(Metadata.class));
        playHandle = MethodHandles.lookup().findVirtual(vClass, "play", MethodType.methodType(String.class));
        pauseHandle = MethodHandles.lookup().findVirtual(vClass, "pause", MethodType.methodType(String.class, Integer.class));
        stopHandle = MethodHandles.lookup().findVirtual(vClass, "stop", MethodType.methodType(String.class));
    }

    @Test
    @DisplayName("Test getVideoFileData parsing")
    public void testGetVideoFileData() throws Throwable {
        VideoFile v = new VideoFile("Ludwig van Beethoven - Ode an die Freude.mp4");
        Metadata expected = new Metadata("Ludwig van Beethoven", "Ode an die Freude", "mp4");
        assertEquals(expected, getVideoFileDataHandle.invoke(v), "getVideoFileData parsing is incorrect");

        v = new VideoFile("Mozart - Eine kleine Nachtmusik.wmv");
        expected = new Metadata("Mozart", "Eine kleine Nachtmusik", "wmv");
        assertEquals(expected, getVideoFileDataHandle.invoke(v), "getVideoFileData parsing is incorrect");
    }

    @Test
    @DisplayName("Test Playable interface methods")
    public void testPlayableMethods() throws Throwable {
        VideoFile v = new VideoFile("Test Video.mp4");
        assertEquals("Playing videofile: Test Video.mp4", playHandle.invoke(v), "play() is incorrect");
        assertEquals("Videofile Test Video.mp4 paused for 10 seconds", pauseHandle.invoke(v, 10), "pause() is incorrect");
        assertEquals("Videofile Test Video.mp4 stopped", stopHandle.invoke(v), "stop() is incorrect");
    }

    @Test
    @DisplayName("Test malformed filename")
    public void testMalformedFilename() throws Throwable {
        VideoFile v = new VideoFile("InvalidFile.mp4"); // No " - " separator
        Metadata expected = new Metadata("", "InvalidFile", "mp4"); // Reasonable default
        assertEquals(expected, getVideoFileDataHandle.invoke(v), "getVideoFileData should handle malformed filenames gracefully");
    }
}
