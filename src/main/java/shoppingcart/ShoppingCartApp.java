package shoppingcart;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class ShoppingCartApp extends Application {

    private final LocalizationService localizationService = new LocalizationService();
    private final CartService cartService = new CartService();
    private Map<String, String> strings;
    private String currentLanguage = "en_US";

    private ShoppingCart cart = new ShoppingCart();

    private Label numItemsLabel;
    private Label totalLabel;
    private TextField numItemsField;
    private Button setItemsButton;
    private Button calculateButton;
    private VBox itemsBox;
    private VBox resultsBox;
    private VBox root;

    private TextField[] priceFields;
    private TextField[] quantityFields;
    private Label[] priceLabels;
    private Label[] quantityLabels;

    @Override
    public void start(Stage stage) {
        strings = localizationService.loadStrings("en_US");

        root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.TOP_CENTER);

        // Language selector
        ComboBox<String> langBox = new ComboBox<>();
        langBox.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");
        langBox.setValue("English");
        langBox.setOnAction(e -> changeLanguage(langBox.getValue()));

        // Number of items
        numItemsLabel = new Label(strings.get("prompt.num.items"));
        numItemsField = new TextField();
        numItemsField.setPrefWidth(60);
        setItemsButton = new Button(strings.get("button.set.items"));
        setItemsButton.setOnAction(e -> createItemFields());
        HBox numRow = new HBox(10, numItemsLabel, numItemsField, setItemsButton);
        numRow.setAlignment(Pos.CENTER);

        // Items area
        itemsBox = new VBox(5);
        itemsBox.setAlignment(Pos.CENTER);

        // Calculate
        calculateButton = new Button(strings.get("button.calculate"));
        calculateButton.setOnAction(e -> calculate());
        calculateButton.setVisible(false);

        // Results
        resultsBox = new VBox(3);
        resultsBox.setAlignment(Pos.CENTER);
        totalLabel = new Label();

        root.getChildren().addAll(langBox, numRow, itemsBox, calculateButton, resultsBox, totalLabel);

        stage.setTitle("Jesper Holmstrom - Shopping Cart");
        stage.setScene(new Scene(root, 500, 450));
        stage.show();
    }

    private void changeLanguage(String lang) {
        currentLanguage = switch (lang) {
            case "Finnish" -> "fi_FI";
            case "Swedish" -> "sv_SE";
            case "Japanese" -> "ja_JP";
            case "Arabic" -> "ar_AR";
            default -> "en_US";
        };

        strings = localizationService.loadStrings(currentLanguage);

        root.setNodeOrientation("Arabic".equals(lang)
                ? NodeOrientation.RIGHT_TO_LEFT
                : NodeOrientation.LEFT_TO_RIGHT);

        numItemsLabel.setText(strings.get("prompt.num.items"));
        setItemsButton.setText(strings.get("button.set.items"));
        calculateButton.setText(strings.get("button.calculate"));

        if (priceLabels != null) {
            for (int i = 0; i < priceLabels.length; i++) {
                priceLabels[i].setText(strings.get("prompt.item.price") + " " + (i + 1) + ":");
                quantityLabels[i].setText(strings.get("prompt.item.quantity") + " " + (i + 1) + ":");
            }
        }
        resultsBox.getChildren().clear();
        totalLabel.setText("");
    }

    private void createItemFields() {
        itemsBox.getChildren().clear();
        resultsBox.getChildren().clear();
        totalLabel.setText("");
        cart.clear();

        int n;
        try {
            n = Integer.parseInt(numItemsField.getText().trim());
            if (n <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            return;
        }

        priceFields = new TextField[n];
        quantityFields = new TextField[n];
        priceLabels = new Label[n];
        quantityLabels = new Label[n];

        for (int i = 0; i < n; i++) {
            priceLabels[i] = new Label(strings.get("prompt.item.price") + " " + (i + 1) + ":");
            priceFields[i] = new TextField();
            priceFields[i].setPrefWidth(80);

            quantityLabels[i] = new Label(strings.get("prompt.item.quantity") + " " + (i + 1) + ":");
            quantityFields[i] = new TextField();
            quantityFields[i].setPrefWidth(80);

            HBox row = new HBox(8, priceLabels[i], priceFields[i], quantityLabels[i], quantityFields[i]);
            row.setAlignment(Pos.CENTER);
            itemsBox.getChildren().add(row);
        }

        calculateButton.setVisible(true);
    }

    private void calculate() {
        cart.clear();
        resultsBox.getChildren().clear();

        try {
            for (int i = 0; i < priceFields.length; i++) {
                double price = Double.parseDouble(priceFields[i].getText().trim());
                int qty = Integer.parseInt(quantityFields[i].getText().trim());
                cart.addItem("Item " + (i + 1), price, qty);
            }
        } catch (NumberFormatException e) {
            return;
        }

        for (CartItem item : cart.getItems()) {
            resultsBox.getChildren().add(new Label(
                    item.getName() + "  x" + item.getQuantity()
                    + " @ " + item.getPrice() + " = " + item.getTotalCost()));
        }

        double total = cart.calculateTotalCost();
        totalLabel.setText(strings.get("label.total.cost") + " " + total);

        cartService.saveCart(cart.getItems(), total, currentLanguage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}