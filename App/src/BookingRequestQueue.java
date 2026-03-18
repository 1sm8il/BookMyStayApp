import java.util.LinkedList;
import java.util.Queue;

/**
 * =====================================================
 * CLASS - BookingRequestQueue
 * =====================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * @version 11.0
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

    public int getQueueSize() {
        return requestQueue.size();
    }
}