import java.util.HashMap;
import java.util.Map;

/**
 * =====================================================
 * CLASS - RoomInventory
 * =====================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * @version 12.0
 */
public class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }

    public int getAvailableCount(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        System.out.println("Single: " + getAvailableCount("Single"));
        System.out.println("Double: " + getAvailableCount("Double"));
        System.out.println("Suite: " + getAvailableCount("Suite"));
    }
}