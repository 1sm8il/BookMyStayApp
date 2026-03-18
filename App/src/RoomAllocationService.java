import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * =====================================================
 * CLASS - RoomAllocationService
 * =====================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * @version 8.0
 */
public class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public String allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();
        String guestName = reservation.getGuestName();

        if (inventory.getAvailableCount(roomType) > 0) {
            String roomId = generateRoomId(roomType);
            allocatedRoomIds.add(roomId);
            assignedRoomsByType.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);

            int currentCount = inventory.getAvailableCount(roomType);
            inventory.updateAvailability(roomType, currentCount - 1);

            reservation.setRoomId(roomId);

            System.out.println("Booking confirmed for Guest: " + guestName + ", Room ID: " + roomId);
            return roomId;
        } else {
            System.out.println("Sorry, no rooms available for Guest: " + guestName);
            return null;
        }
    }

    private String generateRoomId(String roomType) {
        int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return roomType + "-" + count;
    }
}