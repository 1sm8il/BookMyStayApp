import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * =====================================================
 * CLASS - RoomAllocationService
 * =====================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Description:
 * This class is responsible for confirming
 * booking requests and assigning rooms.
 *
 * It ensures:
 * - Each room ID is unique
 * - Inventory is updated immediately
 * - No room is double-booked
 *
 * @version 6.0
 */
public class RoomAllocationService {

    /**
     * Stores all allocated room IDs to
     * prevent duplicate assignments.
     */
    private Set<String> allocatedRoomIds;

    /**
     * Stores assigned room IDs by room type.
     *
     * Key   -> Room type
     * Value -> Set of assigned room IDs
     */
    private Map<String, Set<String>> assignedRoomsByType;

    /**
     * Initializes allocation tracking structures.
     */
    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    /**
     * Confirms a booking request by assigning
     * a unique room ID and updating inventory.
     *
     * @param reservation booking request
     * @param inventory centralized room inventory
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();
        String guestName = reservation.getGuestName();

        // Check if room is available
        if (inventory.getAvailableCount(roomType) > 0) {
            // Generate unique room ID
            String roomId = generateRoomId(roomType);

            // Record the allocation
            allocatedRoomIds.add(roomId);

            // Track by room type
            assignedRoomsByType.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);

            // Update inventory immediately
            int currentCount = inventory.getAvailableCount(roomType);
            inventory.updateAvailability(roomType, currentCount - 1);

            // Confirm booking
            System.out.println("Booking confirmed for Guest: " + guestName + ", Room ID: " + roomId);
        } else {
            System.out.println("Sorry, no rooms available for Guest: " + guestName);
        }
    }

    /**
     * Generates a unique room ID for the given room type.
     *
     * @param roomType type of room
     * @return unique room ID
     */
    private String generateRoomId(String roomType) {
        // Get the count of already assigned rooms for this type
        int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return roomType + "-" + count;
    }

    /**
     * Checks if a room ID has already been allocated.
     *
     * @param roomId the room ID to check
     * @return true if already allocated
     */
    public boolean isRoomAllocated(String roomId) {
        return allocatedRoomIds.contains(roomId);
    }

    /**
     * Gets all allocated room IDs for a specific room type.
     *
     * @param roomType the room type
     * @return set of allocated room IDs
     */
    public Set<String> getAllocatedRoomsByType(String roomType) {
        return assignedRoomsByType.getOrDefault(roomType, new HashSet<>());
    }
}