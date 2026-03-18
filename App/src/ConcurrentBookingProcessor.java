/**
 * =====================================================
 * CLASS - ConcurrentBookingProcessor
 * =====================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * Description:
 * This class represents a booking processor
 * that can be executed by multiple threads.
 *
 * It demonstrates how shared resources
 * such as booking queues and inventory
 * must be accessed in a thread-safe manner.
 *
 * @version 11.0
 */
public class ConcurrentBookingProcessor implements Runnable {

    /** Shared booking request queue. */
    private BookingRequestQueue bookingQueue;

    /** Shared room inventory. */
    private RoomInventory inventory;

    /** Shared room allocation service. */
    private RoomAllocationService allocationService;

    /** Thread name for identification. */
    private String threadName;

    /**
     * Creates a new booking processor.
     *
     * @param bookingQueue shared booking queue
     * @param inventory shared inventory
     * @param allocationService shared allocation service
     * @param threadName name of the thread
     */
    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService,
            String threadName) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
        this.threadName = threadName;
    }

    /**
     * Executes booking processing logic.
     *
     * This method is called when the thread starts.
     */
    @Override
    public void run() {
        System.out.println(threadName + " started processing bookings.");

        while (true) {
            Reservation reservation = null;

            /*
             * Synchronize on the booking queue to ensure
             * that only one thread can retrieve a request
             * at a time.
             */
            synchronized (bookingQueue) {
                if (!bookingQueue.hasPendingRequests()) {
                    break; // No more requests to process
                }
                reservation = bookingQueue.getNextRequest();
                System.out.println(threadName + " retrieved booking for: " +
                        (reservation != null ? reservation.getGuestName() : "null"));
            }

            if (reservation != null) {
                /*
                 * Allocation also mutates shared inventory.
                 * Synchronization ensures atomic allocation.
                 */
                synchronized (inventory) {
                    allocationService.allocateRoom(reservation, inventory);
                }

                // Small delay to simulate processing time
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(threadName + " was interrupted.");
                    break;
                }
            }
        }

        System.out.println(threadName + " finished processing.");
    }
}