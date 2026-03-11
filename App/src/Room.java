/**
 * =====================================================
 * ABSTRACT CLASS - Room
 * =====================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * This abstract class represents a generic hotel room.
 *
 * @version 3.0
 */
public abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public int getSquareFeet() {
        return squareFeet;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
}