/**
 * =====================================================
 * MAIN CLASS - BookMyStayApp
 * =====================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * Description:
 * This class demonstrates how system state
 * can be restored after an application restart.
 *
 * Inventory data is loaded from a file
 * before any booking operations occur.
 *
 * @version 12.0
 */
public class BookMyStayApp {

    // Persistence file path
    private static final String INVENTORY_FILE = "inventory.dat";

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        System.out.println("System Recovery");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        // Load existing inventory from file (if exists)
        persistenceService.loadInventory(inventory, INVENTORY_FILE);

        // Display current inventory
        inventory.displayInventory();

        // Save inventory to file
        persistenceService.saveInventory(inventory, INVENTORY_FILE);

        // Demonstrate recovery after "restart"
        System.out.println("\n--- Simulating System Restart ---\n");

        // Create new inventory (simulating fresh start)
        RoomInventory newInventory = new RoomInventory();

        // Load from file
        System.out.println("System Recovery");
        persistenceService.loadInventory(newInventory, INVENTORY_FILE);

        // Display recovered inventory
        newInventory.displayInventory();
    }
}