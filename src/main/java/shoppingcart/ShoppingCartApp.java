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

import java.util.Locale;
import java.util.ResourceBundle;

public class ShoppingCartApp extends Application {

    private ResourceBundle bundle;
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
        bundle = ResourceBundle.getBundle("MessagesBundle", new Locale("en", "US"), new UTF8Control());

        root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.TOP_CENTER);

        // Language selector
        ComboBox<String> langBox = new ComboBox<>();
        langBox.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");
        langBox.setValue("English");
        langBox.setOnAction(e -> changeLanguage(langBox.getValue()));

        // Number of items
        numItemsLabel = new Label(bundle.getString("prompt.num.items"));
        numItemsField = new TextField();
        numItemsField.setPrefWidth(60);
        setItemsButton = new Button(bundle.getString("button.set.items"));
        setItemsButton.setOnAction(e -> createItemFields());
        HBox numRow = new HBox(10, numItemsLabel, numItemsField, setItemsButton);
        numRow.setAlignment(Pos.CENTER);

        // Items area
        itemsBox = new VBox(5);
        itemsBox.setAlignment(Pos.CENTER);

        // Calculate
        calculateButton = new Button(bundle.getString("button.calculate"));
        calculateButton.setOnAction(e -> calculate());
        calculateButton.setVisible(false);

        // Results
        resultsBox = new VBox(3);
        resultsBox.setAlignment(Pos.CENTER);
        totalLabel = new Label();

        root.getChildren().addAll(langBox, numRow, itemsBox, calculateButton, resultsBox, totalLabel);

        stage.setTitle("Shopping Cart");
        stage.setScene(new Scene(root, 500, 450));
        stage.show();
    }

    private void changeLanguage(String lang) {
        Locale locale = switch (lang) {
            case "Finnish" -> new Locale("fi", "FI");
            case "Swedish" -> new Locale("sv", "SE");
            case "Japanese" -> new Locale("ja", "JP");
            case "Arabic" -> new Locale("ar", "AR");
            default -> new Locale("en", "US");
        };
        bundle = ResourceBundle.getBundle("MessagesBundle", locale, new UTF8Control());

        root.setNodeOrientation("Arabic".equals(lang)
                ? NodeOrientation.RIGHT_TO_LEFT
                : NodeOrientation.LEFT_TO_RIGHT);

        numItemsLabel.setText(bundle.getString("prompt.num.items"));
        setItemsButton.setText(bundle.getString("button.set.items"));
        calculateButton.setText(bundle.getString("button.calculate"));

        if (priceLabels != null) {
            for (int i = 0; i < priceLabels.length; i++) {
                priceLabels[i].setText(bundle.getString("prompt.item.price") + " " + (i + 1) + ":");
                quantityLabels[i].setText(bundle.getString("prompt.item.quantity") + " " + (i + 1) + ":");
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
            priceLabels[i] = new Label(bundle.getString("prompt.item.price") + " " + (i + 1) + ":");
            priceFields[i] = new TextField();
            priceFields[i].setPrefWidth(80);

            quantityLabels[i] = new Label(bundle.getString("prompt.item.quantity") + " " + (i + 1) + ":");
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

        totalLabel.setText(bundle.getString("label.total.cost") + " " + cart.calculateTotalCost());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
