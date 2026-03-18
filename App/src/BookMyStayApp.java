/**
 * =====================================================
 * MAIN CLASS - BookMyStayApp
 * =====================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Description:
 * This class demonstrates how confirmed
 * bookings can be cancelled safely.
 *
 * Inventory is restored and rollback
 * history is maintained.
 *
 * @version 10.0
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();
        CancellationService cancellationService = new CancellationService();

        // Create initial bookings
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Allocate rooms
        String roomId1 = allocationService.allocateRoom(r1, inventory);
        String roomId2 = allocationService.allocateRoom(r2, inventory);
        String roomId3 = allocationService.allocateRoom(r3, inventory);

        // Register bookings for cancellation tracking
        if (roomId1 != null) {
            cancellationService.registerBooking(roomId1, "Single");
        }
        if (roomId2 != null) {
            cancellationService.registerBooking(roomId2, "Double");
        }
        if (roomId3 != null) {
            cancellationService.registerBooking(roomId3, "Suite");
        }

        System.out.println();

        // Cancel a booking (demonstrating rollback)
        if (roomId1 != null) {
            cancellationService.cancelBooking(roomId1, inventory);
        }

        // Show rollback history
        cancellationService.showRollbackHistory();

        // Display updated availability
        System.out.println("\nUpdated Single Room Availability: " + inventory.getAvailableCount("Single"));
    }
}