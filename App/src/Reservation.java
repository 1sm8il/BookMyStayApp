/**
 * =====================================================
 * CLASS - Reservation
 * =====================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * @version 9.0
 */
public class Reservation {

    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = null;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}