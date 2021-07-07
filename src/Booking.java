/*
 *  This code is written by Nguyen Hoang Minh
 * 
 */
/**
 *
 * @author luugia
 */
public class Booking {

    int bookingID;
    int showID;
    String Customer;
    String seatStatus;
    float totalAmount;

    public Booking() {
    }

    public Booking(int bookingID, int showID, String Customer, String seatStatus, float totalAmount) {
        this.bookingID = bookingID;
        this.showID = showID;
        this.Customer = Customer;
        this.seatStatus = seatStatus;
        this.totalAmount = totalAmount;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getShowID() {
        return showID;
    }

    public void setShowID(int showID) {
        this.showID = showID;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String Customer) {
        this.Customer = Customer;
    }

    public String getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void display() {
        System.out.printf("%d\t%-10s\t%.2f",bookingID,Customer,totalAmount);
        System.out.println();
    }
}
