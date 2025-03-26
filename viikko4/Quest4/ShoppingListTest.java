package exercises;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import org.junit.jupiter.api.*;

@DisplayName("Shopping List")
public class ShoppingListTest {
    private static MethodHandle addItemHandle;
    private static MethodHandle searchByNameHandle;
    private static MethodHandle searchSmallerHandle;
    private static MethodHandle searchGreaterOrEqualHandle;
    private static MethodHandle getTotalHandle;

    @BeforeEach
    public void setUp() throws Exception {
        assertTrue(Modifier.isInterface(Searchable.class.getModifiers()), "Searchable must be an interface");
        assertTrue(Searchable.class.isAssignableFrom(ShoppingList.class), "ShoppingList must implement Searchable");

        try {
            Class<?> shoppingListClass = Class.forName("exercises.ShoppingList");
            addItemHandle = MethodHandles.lookup().findVirtual(shoppingListClass, "addItem", MethodType.methodType(void.class, String.class, Double.class, Integer.class));
            searchByNameHandle = MethodHandles.lookup().findVirtual(shoppingListClass, "searchByName", MethodType.methodType(LinkedList.class, String.class));
            searchSmallerHandle = MethodHandles.lookup().findVirtual(shoppingListClass, "searchSmaller", MethodType.methodType(LinkedList.class, Double.class));
            searchGreaterOrEqualHandle = MethodHandles.lookup().findVirtual(shoppingListClass, "searchGreaterOrEqual", MethodType.methodType(LinkedList.class, Double.class));
            getTotalHandle = MethodHandles.lookup().findVirtual(shoppingListClass, "getTotalByName", MethodType.methodType(Double.class, String.class));
        } catch (Exception e) {
            fail("ShoppingList or its methods are incorrectly declared: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test ShoppingList functionality")
    public void testShoppingListClass() throws Throwable {
        ShoppingList sl = new ShoppingList("GoldFish", 12.15, 2);
        addItemHandle.invoke(sl, "GoldFish", 12.15, 5);
        addItemHandle.invoke(sl, "CatFish", 52.99, 2);

        LinkedList<Product> byName = (LinkedList<Product>) searchByNameHandle.invoke(sl, "GoldFish");
        assertEquals(2, byName.size(), "searchByName should return 2 GoldFish items");

        double expectedTotal = (12.15 * 2) + (12.15 * 5) + (52.99 * 2); // 24.30 + 60.75 + 105.98 = 191.03
        assertEquals(191.03, getTotalHandle.invoke(sl, "Fish"), 0.01, "getTotalByName should sum all items containing 'Fish'");

        LinkedList<Product> greater = (LinkedList<Product>) searchGreaterOrEqualHandle.invoke(sl, 50.0);
        assertEquals(1, greater.size(), "searchGreaterOrEqual should return 1 item >= 50.0");

        LinkedList<Product> smaller = (LinkedList<Product>) searchSmallerHandle.invoke(sl, 90.0);
        assertEquals(2, smaller.size(), "searchSmaller should return 2 items < 90.0");
    }

    @Test
    @DisplayName("Test empty ShoppingList")
    public void testEmptyList() throws Throwable {
        ShoppingList sl = new ShoppingList("Empty", 0.0, 0);
        LinkedList<Product> result = (LinkedList<Product>) searchByNameHandle.invoke(sl, "Empty");
        assertEquals(1, result.size(), "searchByName should return the initial item");

        assertEquals(0.0, getTotalHandle.invoke(sl, "Empty"), "getTotalByName should be 0 for single zero-quantity item");
    }
}
