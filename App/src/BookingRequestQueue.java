import java.util.LinkedList;
import java.util.Queue;

/**
 * =====================================================
 * CLASS - BookingRequestQueue
 * =====================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * @version 10.0
 */
public class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}