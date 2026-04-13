import java.io.*;
import java.util.Map;

/**
 * =====================================================
 * CLASS - FilePersistenceService
 * =====================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * Description:
 * This class is responsible for persisting
 * critical system state to a plain text file.
 *
 * It supports:
 * - Saving room inventory state
 * - Restoring inventory on system startup
 *
 * No database or serialization framework
 * is used in this use case.
 *
 * @version 12.0
 */
public class FilePersistenceService {

    /**
     * Saves room inventory state to a file.
     *
     * Each line follows the format:
     * roomType=availableCount
     *
     * @param inventory centralized room inventory
     * @param filePath path to persistence file
     */
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            Map<String, Integer> availability = inventory.getRoomAvailability();

            for (Map.Entry<String, Integer> entry : availability.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }

            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    /**
     * Loads room inventory state from a file.
     *
     * @param inventory centralized room inventory
     * @param filePath path to persistence file
     */
    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean hasData = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String roomType = parts[0].trim();
                    int count = Integer.parseInt(parts[1].trim());
                    inventory.updateAvailability(roomType, count);
                    hasData = true;
                }
            }

            if (!hasData) {
                System.out.println("No valid inventory data found. Starting fresh.");
            } else {
                System.out.println("Inventory loaded successfully from file.");
            }

        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
            System.out.println("Starting with default inventory.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid data format in persistence file. Starting fresh.");
        }
    }
}