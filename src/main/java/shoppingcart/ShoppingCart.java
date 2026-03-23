package shoppingcart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCart {
    private final List<CartItem> items = new ArrayList<>();

    public void addItem(String name, double price, int quantity) {
        items.add(new CartItem(name, price, quantity));
    }

    public double calculateItemCost(double price, int quantity) {
        return price * quantity;
    }

    public double calculateTotalCost() {
        return items.stream().mapToDouble(CartItem::getTotalCost).sum();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void clear() {
        items.clear();
    }
}