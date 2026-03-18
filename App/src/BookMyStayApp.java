/**
 * =====================================================
 * MAIN CLASS - BookMyStayApp
 * =====================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * Description:
 * This class demonstrates how optional
 * services can be attached to a confirmed
 * booking.
 *
 * Services are added after room allocation
 * and do not affect inventory.
 *
 * @version 7.0
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        System.out.println("Add-On Service Selection\n");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process bookings in FIFO order
        while (bookingQueue.hasPendingRequests()) {
            Reservation currentRequest = bookingQueue.getNextRequest();
            allocationService.allocateRoom(currentRequest, inventory);
        }

        System.out.println();

        // Create add-on services
        AddOnService breakfast = new AddOnService("Breakfast", 25.0);
        AddOnService spa = new AddOnService("Spa", 100.0);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 50.0);

        // Add services to reservations
        // Abhi wants Breakfast and Airport Pickup
        serviceManager.addService(r1.getRoomId(), breakfast);
        serviceManager.addService(r1.getRoomId(), airportPickup);

        // Subha wants Spa
        serviceManager.addService(r2.getRoomId(), spa);

        // Vanmathi wants all services
        serviceManager.addService(r3.getRoomId(), breakfast);
        serviceManager.addService(r3.getRoomId(), spa);
        serviceManager.addService(r3.getRoomId(), airportPickup);

        // Display services for each reservation
        serviceManager.displayServicesForReservation(r1.getRoomId());
        System.out.println();

        serviceManager.displayServicesForReservation(r2.getRoomId());
        System.out.println();

        serviceManager.displayServicesForReservation(r3.getRoomId());
    }
}