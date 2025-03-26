package exercises;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.*;

@DisplayName("Data Organizer")
public class DataOrganizerTest {
    private static MethodHandle groupHandle;

    @BeforeEach
    public void setUp() throws Exception {
        try {
            MethodType methodType = MethodType.methodType(HashMap.class, ArrayList.class);
            groupHandle = MethodHandles.lookup().findVirtual(DataOrganizer.class, "groupAndSort", methodType);
        } catch (NoSuchMethodException e) {
            fail("DataOrganizer class is missing the 'groupAndSort' method");
        }
        assertNotNull(groupHandle, "MethodHandle for 'groupAndSort' could not be initialized");
    }

    @Test
    @DisplayName("Test 1: Group and Sort")
    public void testGroupAndSort1() throws Throwable {
        DataOrganizer dd = new DataOrganizer();
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        HashMap<String, ArrayList<Integer>> result = (HashMap<String, ArrayList<Integer>>) groupHandle.invoke(dd, numbers);
        assertEquals(Arrays.asList(2, 4), result.get("Even"), "Even group should be sorted ascending");
        assertEquals(Arrays.asList(5, 3, 1), result.get("Odd"), "Odd group should be sorted descending");
    }

    @Test
    @DisplayName("Test 2: Group and Sort")
    public void testGroupAndSort2() throws Throwable {
        DataOrganizer dd = new DataOrganizer();
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(14, 15, 18, 20, 22));
        HashMap<String, ArrayList<Integer>> result = (HashMap<String, ArrayList<Integer>>) groupHandle.invoke(dd, numbers);
        assertEquals(Arrays.asList(14, 18, 20, 22), result.get("Even"), "Even group should be sorted ascending");
        assertEquals(Arrays.asList(15), result.get("Odd"), "Odd group should be sorted descending");
    }

    @Test
    @DisplayName("Test 3: Empty input")
    public void testEmptyInput() throws Throwable {
        DataOrganizer dd = new DataOrganizer();
        ArrayList<Integer> numbers = new ArrayList<>();
        HashMap<String, ArrayList<Integer>> result = (HashMap<String, ArrayList<Integer>>) groupHandle.invoke(dd, numbers);
        assertEquals(new ArrayList<>(), result.get("Even"), "Empty input should return empty even list");
        assertEquals(new ArrayList<>(), result.get("Odd"), "Empty input should return empty odd list");
    }
}
