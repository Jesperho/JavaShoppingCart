package shoppingcart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CartService {

    public void saveCart(List<CartItem> items, double totalCost, String language) {
        String insertRecord = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";
        String insertItem = "INSERT INTO cart_items (cart_record_id, item_number, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            int cartRecordId;
            try (PreparedStatement stmt = conn.prepareStatement(insertRecord, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, items.size());
                stmt.setDouble(2, totalCost);
                stmt.setString(3, language);
                stmt.executeUpdate();

                ResultSet keys = stmt.getGeneratedKeys();
                if (!keys.next()) throw new SQLException("No generated key for cart_record");
                cartRecordId = keys.getInt(1);
            }

            try (PreparedStatement stmt = conn.prepareStatement(insertItem)) {
                for (int i = 0; i < items.size(); i++) {
                    CartItem item = items.get(i);
                    stmt.setInt(1, cartRecordId);
                    stmt.setInt(2, i + 1);
                    stmt.setDouble(3, item.getPrice());
                    stmt.setInt(4, item.getQuantity());
                    stmt.setDouble(5, item.getTotalCost());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            System.err.println("Failed to save cart: " + e.getMessage());
        }
    }
}