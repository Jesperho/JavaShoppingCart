package shoppingcart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
    }

    @Test
    void testCalculateItemCost() {
        assertEquals(10.0, cart.calculateItemCost(5.0, 2));
        assertEquals(0.0, cart.calculateItemCost(0.0, 5));
        assertEquals(25.5, cart.calculateItemCost(8.5, 3));
    }

    @Test
    void testEmptyCartTotal() {
        assertEquals(0.0, cart.calculateTotalCost());
    }

    @Test
    void testSingleItemTotal() {
        cart.addItem("Apple", 1.5, 3);
        assertEquals(4.5, cart.calculateTotalCost(), 0.001);
    }

    @Test
    void testMultipleItemsTotal() {
        cart.addItem("Apple", 1.5, 3);   // 4.50
        cart.addItem("Bread", 2.0, 2);   // 4.00
        cart.addItem("Milk", 1.0, 4);    // 4.00
        assertEquals(12.5, cart.calculateTotalCost(), 0.001);
    }

    @Test
    void testCartItemTotalCost() {
        CartItem item = new CartItem("Book", 12.99, 2);
        assertEquals(25.98, item.getTotalCost(), 0.001);
    }

    @Test
    void testAddItemAndRetrieve() {
        cart.addItem("Apple", 1.5, 3);
        CartItem item = cart.getItems().get(0);
        assertEquals("Apple", item.getName());
        assertEquals(1.5, item.getPrice());
        assertEquals(3, item.getQuantity());
    }
}