import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =====================================================
 * CLASS - AddOnServiceManager
 * =====================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * Description:
 * This class manages optional services
 * associated with confirmed reservations.
 *
 * It supports attaching multiple services
 * to a single reservation.
 *
 * @version 7.0
 */
public class AddOnServiceManager {

    /**
     * Maps reservation ID to selected services.
     *
     * Key   -> Reservation ID
     * Value -> List of selected services
     */
    private Map<String, List<AddOnService>> servicesByReservation;

    /**
     * Initializes the service manager.
     */
    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    /**
     * Attaches a service to a reservation.
     *
     * @param reservationId confirmed reservation ID
     * @param service add-on service
     */
    public void addService(String reservationId, AddOnService service) {
        servicesByReservation.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
    }

    /**
     * Calculates total add-on cost for a reservation.
     *
     * @param reservationId reservation ID
     * @return total service cost
     */
    public double calculateTotalServiceCost(String reservationId) {
        List<AddOnService> services = servicesByReservation.get(reservationId);
        if (services == null) {
            return 0.0;
        }

        double totalCost = 0.0;
        for (AddOnService service : services) {
            totalCost += service.getCost();
        }
        return totalCost;
    }

    /**
     * Gets all services for a reservation.
     *
     * @param reservationId reservation ID
     * @return list of services
     */
    public List<AddOnService> getServicesForReservation(String reservationId) {
        return servicesByReservation.getOrDefault(reservationId, new ArrayList<>());
    }

    /**
     * Displays all services for a reservation.
     *
     * @param reservationId reservation ID
     */
    public void displayServicesForReservation(String reservationId) {
        List<AddOnService> services = getServicesForReservation(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected for reservation: " + reservationId);
            return;
        }

        System.out.println("Add-on Services for Reservation " + reservationId + ":");
        for (AddOnService service : services) {
            System.out.println("  - " + service.getServiceName() + ": $" + service.getCost());
        }
        System.out.println("  Total Add-on Cost: $" + calculateTotalServiceCost(reservationId));
    }
}