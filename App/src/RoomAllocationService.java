import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * =====================================================
 * CLASS - RoomAllocationService
 * =====================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * @version 11.0
 */
public class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;
    private int allocationCounter;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
        allocationCounter = 0;
    }

    public synchronized String allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();
        String guestName = reservation.getGuestName();

        if (inventory.getAvailableCount(roomType) > 0) {
            String roomId = generateRoomId(roomType);
            allocatedRoomIds.add(roomId);
            assignedRoomsByType.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);

            int currentCount = inventory.getAvailableCount(roomType);
            inventory.updateAvailability(roomType, currentCount - 1);

            reservation.setRoomId(roomId);

            System.out.println("  [ALLOCATED] Guest: " + guestName +
                    ", Room: " + roomId +
                    ", Type: " + roomType);
            return roomId;
        } else {
            System.out.println("  [FAILED] No rooms available for Guest: " + guestName);
            return null;
        }
    }

    private String generateRoomId(String roomType) {
        allocationCounter++;
        return roomType + "-" + allocationCounter;
    }

    public int getTotalAllocations() {
        return allocatedRoomIds.size();
    }
}