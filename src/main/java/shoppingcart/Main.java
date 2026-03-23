package shoppingcart;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select language:");
        System.out.println("1 = English");
        System.out.println("2 = Finnish");
        System.out.println("3 = Swedish");
        System.out.println("4 = Japanese");

        int langChoice = scanner.nextInt();

        Locale locale;
        if (langChoice == 2) {
            locale = new Locale("fi", "FI");
        } else if (langChoice == 3) {
            locale = new Locale("sv", "SE");
        } else if (langChoice == 4) {
            locale = new Locale("ja", "JP");
        } else {
            locale = new Locale("en", "US");
        }

        ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", locale, new UTF8Control());
        ShoppingCart cart = new ShoppingCart();

        System.out.println(msg.getString("prompt.num.items"));
        int numItems = scanner.nextInt();

        for (int i = 1; i <= numItems; i++) {
            System.out.println(msg.getString("prompt.item.name") + " " + i + ":");
            String name = scanner.next();

            System.out.println(msg.getString("prompt.item.price") + " " + i + ":");
            double price = scanner.nextDouble();

            System.out.println(msg.getString("prompt.item.quantity") + " " + i + ":");
            int quantity = scanner.nextInt();

            cart.addItem(name, price, quantity);
        }

        System.out.println("\n--- " + msg.getString("label.cart.summary") + " ---");
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getName() + " x" + item.getQuantity()
                    + " @ " + item.getPrice() + " = " + item.getTotalCost());
        }

        System.out.println(msg.getString("label.total.cost") + " " + cart.calculateTotalCost());

        scanner.close();
    }
}