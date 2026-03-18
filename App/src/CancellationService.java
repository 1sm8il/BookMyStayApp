import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * =====================================================
 * CLASS - CancellationService
 * =====================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Description:
 * This class is responsible for handling
 * booking cancellations.
 *
 * It ensures that:
 * - Cancelled room IDs are tracked
 * - Inventory is restored correctly
 * - Invalid cancellations are prevented
 *
 * A stack is used to model rollback behavior.
 *
 * @version 10.0
 */
public class CancellationService {

    /** Stack that stores recently released room IDs. */
    private Stack<String> releasedRoomIds;

    /** Maps reservation ID to room type. */
    private Map<String, String> reservationRoomTypeMap;

    /** Initializes cancellation tracking structures. */
    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    /**
     * Registers a confirmed booking.
     *
     * This method simulates storing confirmation
     * data that will later be required for cancellation.
     *
     * @param reservationId confirmed reservation ID
     * @param roomType allocated room type
     */
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    /**
     * Cancels a confirmed booking and
     * restores inventory safely.
     *
     * @param reservationId reservation to cancel
     * @param inventory centralized room inventory
     */
    public void cancelBooking(String reservationId, RoomInventory inventory) {
        // Validate reservation exists
        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation " + reservationId + " not found.");
            return;
        }

        // Get room type for this reservation
        String roomType = reservationRoomTypeMap.get(reservationId);

        // Remove from active reservations
        reservationRoomTypeMap.remove(reservationId);

        // Push to stack for rollback tracking (LIFO)
        releasedRoomIds.push(reservationId);

        // Restore inventory (increment count)
        int currentCount = inventory.getAvailableCount(roomType);
        inventory.updateAvailability(roomType, currentCount + 1);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    /**
     * Displays recently cancelled reservations.
     *
     * This method helps visualize rollback order.
     */
    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");

        if (releasedRoomIds.isEmpty()) {
            System.out.println("No cancellations recorded.");
            return;
        }

        // Create a temporary stack to preserve original
        Stack<String> tempStack = new Stack<>();

        while (!releasedRoomIds.isEmpty()) {
            String reservationId = releasedRoomIds.pop();
            System.out.println("Released Reservation ID: " + reservationId);
            tempStack.push(reservationId);
        }

        // Restore original stack
        while (!tempStack.isEmpty()) {
            releasedRoomIds.push(tempStack.pop());
        }
    }

    /**
     * Gets the count of released rooms for a specific type.
     *
     * @param roomType the room type
     * @return count of released rooms
     */
    public int getReleasedCount(String roomType) {
        int count = 0;
        for (String reservationId : releasedRoomIds) {
            if (reservationId.startsWith(roomType)) {
                count++;
            }
        }
        return count;
    }
}