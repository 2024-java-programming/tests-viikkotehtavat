package exercises;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashSet;
import java.util.Arrays;

import org.junit.jupiter.api.*;

@DisplayName("Set Operations")
public class SetOperationsTest {
    private static MethodHandle unionHandle;
    private static MethodHandle intersectionHandle;
    private static MethodHandle differenceHandle;

    @BeforeEach
    public void setUp() throws Exception {
        try {
            unionHandle = MethodHandles.lookup().findVirtual(SetOperations.class, "union", MethodType.methodType(HashSet.class));
            intersectionHandle = MethodHandles.lookup().findVirtual(SetOperations.class, "intersection", MethodType.methodType(HashSet.class));
            differenceHandle = MethodHandles.lookup().findVirtual(SetOperations.class, "difference", MethodType.methodType(HashSet.class));
        } catch (NoSuchMethodException e) {
            fail("SetOperations class is missing required methods");
        }
    }

    @Test
    @DisplayName("Test union of sets")
    public void testUnion() throws Throwable {
        HashSet<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
        HashSet<Integer> set2 = new HashSet<>(Arrays.asList(2, 3, 4));
        SetOperations so = new SetOperations(set1, set2);
        HashSet<Integer> result = (HashSet<Integer>) unionHandle.invoke(so);
        assertEquals(new HashSet<>(Arrays.asList(1, 2, 3, 4)), result, "Union operation result is not correct");
    }

    @Test
    @DisplayName("Test intersection of sets")
    public void testIntersection() throws Throwable {
        HashSet<Integer> set1 = new HashSet<>(Arrays.asList(5, 6, 7, 8, 9));
        HashSet<Integer> set2 = new HashSet<>(Arrays.asList(7, 9, 12, 15));
        SetOperations so = new SetOperations(set1, set2);
        HashSet<Integer> result = (HashSet<Integer>) intersectionHandle.invoke(so);
        assertEquals(new HashSet<>(Arrays.asList(7, 9)), result, "Intersection operation result is not correct");
    }

    @Test
    @DisplayName("Test difference of sets")
    public void testDifference() throws Throwable {
        HashSet<Integer> set1 = new HashSet<>(Arrays.asList(5, 6, 7, 8, 9, 10, 11));
        HashSet<Integer> set2 = new HashSet<>(Arrays.asList(7, 9, 11));
        SetOperations so = new SetOperations(set1, set2);
        HashSet<Integer> result = (HashSet<Integer>) differenceHandle.invoke(so);
        assertEquals(new HashSet<>(Arrays.asList(5, 6, 8, 10)), result, "Difference operation result is not correct");
    }

    @Test
    @DisplayName("Test difference with empty set")
    public void testDifferenceWithEmpty() throws Throwable {
        HashSet<Integer> set1 = new HashSet<>(Arrays.asList(5, 6, 7, 8, 9, 10, 11));
        HashSet<Integer> set2 = new HashSet<>();
        SetOperations so = new SetOperations(set1, set2);
        HashSet<Integer> result = (HashSet<Integer>) differenceHandle.invoke(so);
        assertEquals(new HashSet<>(Arrays.asList(5, 6, 7, 8, 9, 10, 11)), result, "Difference operation with empty set is not correct");
    }
}
