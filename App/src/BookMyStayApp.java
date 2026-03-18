/**
 * =====================================================
 * MAIN CLASS - BookMyStayApp
 * =====================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * Description:
 * This class simulates multiple users
 * attempting to book rooms at the same time.
 *
 * It highlights race conditions and
 * demonstrates how synchronization
 * prevents inconsistent allocations.
 *
 * @version 11.0
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        System.out.println("=== Concurrent Booking Simulation ===\n");

        // Initialize shared components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();

        System.out.println("Initial Inventory:");
        inventory.displayInventory();

        // Create multiple booking requests
        System.out.println("\nCreating booking requests...");
        String[][] bookings = {
                {"Alice", "Single"},
                {"Bob", "Double"},
                {"Charlie", "Suite"},
                {"Diana", "Single"},
                {"Eve", "Double"},
                {"Frank", "Single"},
                {"Grace", "Suite"},
                {"Henry", "Double"},
                {"Ivy", "Single"},
                {"Jack", "Single"}
        };

        // Add all requests to queue
        for (String[] booking : bookings) {
            Reservation reservation = new Reservation(booking[0], booking[1]);
            bookingQueue.addRequest(reservation);
            System.out.println("  Added: " + booking[0] + " -> " + booking[1]);
        }

        System.out.println("\nTotal requests in queue: " + bookingQueue.getQueueSize());

        System.out.println("\n=== Starting Concurrent Processing ===\n");

        // Create multiple threads to process bookings concurrently
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService,
                        "Thread-1"
                )
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService,
                        "Thread-2"
                )
        );

        Thread t3 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService,
                        "Thread-3"
                )
        );

        // Start concurrent processing
        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to complete
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
            Thread.currentThread().interrupt();
        }

        System.out.println("\n=== Simulation Complete ===");

        // Display final results
        System.out.println("\nFinal Inventory Status:");
        inventory.displayInventory();

        System.out.println("\nTotal Allocations: " + allocationService.getTotalAllocations());
        System.out.println("Expected Allocations: " + bookings.length);

        // Verify consistency
        int totalRemaining = inventory.getAvailableCount("Single") +
                inventory.getAvailableCount("Double") +
                inventory.getAvailableCount("Suite");

        int totalInitial = 5 + 3 + 2; // Initial inventory
        int totalProcessed = totalInitial - totalRemaining;

        System.out.println("\nConsistency Check:");
        System.out.println("  Initial rooms: " + totalInitial);
        System.out.println("  Remaining rooms: " + totalRemaining);
        System.out.println("  Rooms allocated: " + totalProcessed);
        System.out.println("  Allocations recorded: " + allocationService.getTotalAllocations());

        if (totalProcessed == allocationService.getTotalAllocations()) {
            System.out.println("\n✓ System state is CONSISTENT (No race conditions detected)");
        } else {
            System.out.println("\n✗ System state is INCONSISTENT (Race condition detected!)");
        }
    }
}