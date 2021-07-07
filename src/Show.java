

/*
 *  This code is written by Nguyen Hoang Minh
 * 
 */
/**
 *
 * @author luugia
 */
public class Show implements Comparable<Show>{

    int showID;
    int filmID;
    int roomID;
    String showDate;
    float price;
    int slotTime;
    filmList f = new filmList();
    String fname;
    public Show() {
    }

    public Show(int showID, int filmID, int roomID, String showDate, float price, int slotTime, String fname) {
        this.showID = showID;
        this.filmID = filmID;
        this.roomID = roomID;
        this.showDate = showDate;
        this.price = price;
        this.slotTime = slotTime;
        this.fname = fname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
    

    public int getShowID() {
        return showID;
    }

    public void setShowID(int showID) {
        this.showID = showID;
    }

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(int slotTime) {
        this.slotTime = slotTime;
    }

    public void display() {
       
        System.out.println(showID + "\t" + filmID + "\t"
                + roomID + "\t"
                + showDate + "\t"
                + price + "\t"
                + slotTime);
    }
    public void display2(){
        System.out.printf("%d\t%-20s\t%d\t%.1f\t%d", showID,fname,roomID,price,slotTime);
        System.out.println();
    }

    @Override
    public int compareTo(Show o) {
         if (showID <= o.showID) {
            return 1;
        } else {
            return -1;
        }
    }

}
