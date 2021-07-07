
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class BookingShow extends Vector<Booking> {

    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String RESET = "\033[0m";

    public BookingShow() {
        super();
    }

    int bookingID;
    int IDmax;
    int showID;
    String Customer;
    String seatStatus;
    float totalAmount;
    ArrayList<Integer> idList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    Utinity u = new Utinity();
    RoomList rm = new RoomList();
    showList sL = new showList();
    Utinity ut = new Utinity();
    Booking b = new Booking();
    Show sh = new Show();
    showList sLwithDate = new showList();
    showList show = new showList();

    public void addFromFile(String fname) {
        try {
            File f = new File(fname);
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String detail;
            while ((detail = bfr.readLine()) != null) {
                String[] splitStr = detail.split("\t");
                bookingID = Integer.parseInt(splitStr[0]);
                idList.add(bookingID);
                showID = Integer.parseInt(splitStr[1]);
                Customer = splitStr[2];
                seatStatus = splitStr[3];
                totalAmount = Float.parseFloat(splitStr[4]);
                Booking bk = new Booking(bookingID, showID, Customer, seatStatus, totalAmount);
                this.add(bk);
            }

            IDmax = Collections.max(idList);

            fr.close();
            bfr.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
    }

    public int checkS(int sID) {
        for (int i = 0; i < sLwithDate.size(); i++) {
            if (sLwithDate.get(i).getShowID() == sID) {
                return i;
            }
        }
        return -1;
    }

    public String getSeatStatusShow(int showID, int nrows, int ncols) {
        char[] getseatStatus = new char[nrows * ncols];
        for (int i = 0; i < getseatStatus.length; i++) {
            getseatStatus[i] = '0';
        }
        for (Booking b : this) {
            if (b.getShowID() == showID) {
                for (int i = 0; i < nrows; i++) {
                    for (int j = 0; j < ncols; j++) {
                        int index;
                        index = i * nrows + j;
                        if (b.getSeatStatus().charAt(index) == '1') {
                            getseatStatus[index] = '1';
                        }
                    }
                }
            }
        }
        return String.valueOf(getseatStatus);
    }

    public void bookShow() {
        addFromFile("bookings.txt");

        int newBookingID = this.IDmax + 1;
        int newShowID;
        String newCustomer;
        String newSeatStatus;
        float newTotal;
        rm.addFromFile("Rooms.txt");

        sL.addFromFile("Shows.txt");
        String showDateCheck;
        ArrayList<String> dateArr = new ArrayList<>();

        for (Show s : sL) {
            dateArr.add(s.showDate);

        }

        int posS, seatCount = 0;
        boolean checkDate = false;
        do {
            System.out.print("Enter show date(dd/MM/yyyy): ");
            showDateCheck = sc.next();
            if (dateArr.contains(showDateCheck)) {
                checkDate = true;
            } else {
                System.out.println("No show on that day!!!");
            }
        } while (checkDate == false);

        System.out.println("List of show on " + showDateCheck + " :");
        System.out.println("-------------------------------------");
        System.out.println("ShowID\tFilmID\tRoomID\tDate\t\tPrice\tSlot");
        for (Show s : sL) {
            if (s.getShowDate().equals(showDateCheck)) {
                s.display();
                sLwithDate.add(s);
            }
        }
        do {
            newShowID = u.getInt("Enter show ID: ");
            posS = checkS(newShowID);
            if (posS < 0 || newShowID > 9) {
                System.out.println("This ID is not available, choose another!!!");
            }
        } while (posS < 0 || showID > 9);
        newCustomer = u.getString("Enter customer's name: ");
        RoomList rl = new RoomList();
        rl.addFromFile("Rooms.txt");

        String seatStatus1 = getSeatStatusShow(newShowID, rl.rows, rl.columns);

        char[] mySeatStatus = new char[rl.rows * rl.columns];
        for (int i = 0; i < mySeatStatus.length; i++) {
            mySeatStatus[i] = '0';
        }
        int index;
        for (int i = 0; i < rl.rows; i++) {
            for (int j = 0; j < rl.columns; j++) {
                index = i * rl.rows + j;
                if (seatStatus1.charAt(index) == '1') {
                    if (mySeatStatus[index] == '0') {
                        System.out.print(ANSI_RED_BACKGROUND + seatStatus1.charAt(index) + " " + RESET);
                    } else {
                        System.out.print(ANSI_GREEN_BACKGROUND + seatStatus1.charAt(index) + " " + RESET);
                    }
                } else {
                    System.out.print(seatStatus1.charAt(index) + " ");
                }
            }
            System.out.println();
        }

        System.out.println();

        boolean checkSeat;
        boolean finish = false;
        int rowSeat;
        int colSeat;
        do {
            do {
                rowSeat = u.getInt("Enter rows: ");
                colSeat = u.getInt("Enter columns: ");
                if (seatStatus1.charAt((rowSeat - 1) * rl.rows + (colSeat - 1)) == '1'
                        || mySeatStatus[(rowSeat - 1) * rl.rows + (colSeat - 1)] == '1') {
                    System.out.println("Seat has been booked!!!Choose another seat");
                    checkSeat = false;
                } else {
                    mySeatStatus[(rowSeat - 1) * rl.rows + (colSeat - 1)] = '1';
                    seatCount += 1;
                    System.out.println("Done!!!");
                    checkSeat = true;
                }
            } while (checkSeat == false);

            newTotal = seatCount * sL.price;

            System.out.println();

            for (int i = 0; i < rl.rows; i++) {
                for (int j = 0; j < rl.columns; j++) {
                    index = i * rl.rows + j;
                    if (seatStatus1.charAt(index) == '1') {
                        if (mySeatStatus[index] == '0') {
                            System.out.print(ANSI_RED_BACKGROUND + seatStatus1.charAt(index) + " " + RESET);
                        }
                    } else {
                        if (mySeatStatus[index] == '0') {
                            System.out.print(seatStatus1.charAt(index) + " ");
                        } else {
                            System.out.print(ANSI_GREEN_BACKGROUND + mySeatStatus[index] + " " + RESET);
                        }
                    }
                }
                System.out.println();
            }
            newSeatStatus = String.valueOf(mySeatStatus);

            System.out.println("Book another seat??? (Y/N)");
            boolean ok = true;
            do {
                String ch = sc.next();
                switch (ch.toLowerCase()) {
                    case "y":
                        ok = false;

                        break;

                    case "n":

                        ok = false;
                        finish = true;
                        break;
                    default:
                        System.out.println("Chỉ có y hoặc n thôi bạn ơi");
                }
            } while (ok);

        } while (finish == false);
        this.add(new Booking(newBookingID, newShowID, newCustomer, newSeatStatus, newTotal));
        display();

        sL.clear();
        rm.clear();
        saveToFile("bookings.txt");
        this.clear();
    }

    public void saveToFile(String fname) {
        if (this.isEmpty()) {
            System.out.println("Empty show list@!@#");
            return;
        }
        try {
            File f = new File(fname);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (Booking x : this) {
                pw.println(x.getBookingID() + "\t"
                        + x.getShowID() + "\t"
                        + x.getCustomer() + "\t"
                        + x.getSeatStatus() + "\t"
                        + x.getTotalAmount());
            }
            fw.close();
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int checkSID(int showID) {
        for (Show s : show) {
            if (s.getShowID() == showID) {
                return 1;
            }
        }
        return -1;
    }

    public int checkBID(int bookingID) {
        for (Booking b : this) {
            if (b.getBookingID() == bookingID) {
                return 1;
            }
        }
        return -1;
    }

    public int getshowSlot(int showID) {
        int showSlot = 0;
        for (Show s : show) {
            if (s.getShowID() == showID) {
                showSlot = s.getSlotTime();
            }

            return showSlot;
        }
        return -1;
    }

    public void deleteBooking() {

        addFromFile("Bookings.txt");
        int checkShowID, checkBookingID, checkShowSlot;
        int posSID;
        ArrayList<String> dateArr = new ArrayList<>();
        show.addFromFile("Shows.txt");
        for (Show s : show) {
            dateArr.add(s.showDate);

        }
        String showDateCheck;
        boolean checkDate = false;
        do {
            System.out.print("Enter show date(dd/MM/yyyy): ");
            showDateCheck = sc.next();
            if (dateArr.contains(showDateCheck)) {
                checkDate = true;

            } else {
                System.out.println("No show on that day!!!");
            }
        } while (checkDate == false);
        System.out.println("List of show on " + showDateCheck + " :");
        System.out.println("-------------------------------------");
        System.out.println("ShowID\tFilm Title\t\tRoomID\tPrice\tSlot");
        for (Show s : show) {
            if (s.getShowDate().equals(showDateCheck)) {
                s.display2();
            }
        }
        ArrayList<Integer> IDlist = new ArrayList<>();
        do {
            checkShowID = u.getInt("Enter show ID: ");
            posSID = checkSID(checkShowID);
            if (posSID < 0) {
                System.out.println("No show ID");
            }
            for (Booking b : this) {
                if (b.getShowID() == checkShowID) {
                    IDlist.add(b.getBookingID());
                }
            }
            if (IDlist.isEmpty()) {
                System.out.println("No Booking for that ID yet!!!");
            }
            checkShowSlot = getshowSlot(checkShowID);

        } while (posSID < 0 || IDlist.isEmpty());
        System.out.println("List of bookings of showID = " + checkShowID);
        System.out.println("-------------------------------------");
        System.out.println("ID\tName\t\tPrice");
        for (Booking b : this) {
            if (b.getShowID() == checkShowID) {
                b.display();
            }
        }
        show.clear();
        RoomList rl = new RoomList();
        rl.addFromFile("Rooms.txt");

        int posB;
        do {
            checkBookingID = u.getInt("Enter bookingID: ");
            posB = checkBID(checkBookingID);
            if (posB < 0 || IDlist.contains(checkBookingID) == false) {
                System.out.print("Booking ID is not valid!!!");
                System.out.println();
            }
        } while (posB < 0 || IDlist.contains(checkBookingID) == false);
        String seatStatus1 = getSeatStatusShow(checkShowID, rl.rows, rl.columns);
        char[] mySeatStatus = new char[rl.rows * rl.columns];
        for (int i = 0; i < mySeatStatus.length; i++) {
            mySeatStatus[i] = '0';
        }
        char[] checkSeatStatus = new char[rl.rows * rl.columns];
        for (int i = 0; i < mySeatStatus.length; i++) {
            checkSeatStatus[i] = '0';
        }

        for (Booking b : this) {
            if (b.getBookingID() == checkBookingID) {
                int index2;
                for (int i = 0; i < rl.rows; i++) {
                    for (int j = 0; j < rl.columns; j++) {
                        index2 = i * rl.rows + j;
                        if (b.getSeatStatus().charAt(index2) == '1') {
                            checkSeatStatus[index2] = '2';
                        }
                    }
                }
            }
        }
        int index;
        for (int i = 0; i < rl.rows; i++) {
            for (int j = 0; j < rl.columns; j++) {
                index = i * rl.rows + j;
                if (seatStatus1.charAt(index) == '1') {
                    if (mySeatStatus[index] == '0' && checkSeatStatus[index] == '0') {
                        System.out.print(ANSI_RED_BACKGROUND + seatStatus1.charAt(index) + " " + RESET);
                    } else {
                        if (checkSeatStatus[index] == '2') {
                            System.out.print(ANSI_GREEN_BACKGROUND + seatStatus1.charAt(index) + " " + RESET);
                        }
                    }
                } else {
                    System.out.print(seatStatus1.charAt(index) + " ");
                }
            }
            System.out.println();
        }
        IDlist.clear();
        try {
            for (Booking b : this) {
                if (b.getBookingID() == checkBookingID) {
                    this.remove(b);
                }
            }
        } catch (Exception e) {
        }
        for (Booking b : this) {
            if (b.getShowID() == checkShowID) {
                IDlist.add(b.getBookingID());
            }
        }
//        
//        if (IDlist.isEmpty()) {
//            System.out.println("No booking for this show ID left!!!");
//            return;
//        }
        System.out.println();
        System.out.println("List of bookings of showID = " + checkShowID);
        System.out.println("-------------------------------------");
        System.out.println("ID\tName\t\tPrice");
        for (Booking b : this) {
            if (b.getShowID() == checkShowID) {
                b.display();
            }
        }
        for (Booking b : this) {
            if (b.getBookingID() == checkBookingID) {
                int index2;
                for (int i = 0; i < rl.rows; i++) {
                    for (int j = 0; j < rl.columns; j++) {
                        index2 = i * rl.rows + j;
                        if (b.getSeatStatus().charAt(index2) == '1') {
                            checkSeatStatus[index2] = '2';
                        }
                    }
                }
            }
        }
        int index2;
        for (int i = 0; i < rl.rows; i++) {
            for (int j = 0; j < rl.columns; j++) {
                index2 = i * rl.rows + j;
                if (seatStatus1.charAt(index2) == '1') {
                    if (mySeatStatus[index2] == '0' && checkSeatStatus[index2] == '0') {
                        System.out.print(ANSI_RED_BACKGROUND + seatStatus1.charAt(index2) + " " + RESET);
                    } else {
                        if (checkSeatStatus[index2] == '2') {
                            System.out.print('0' + " " + RESET);
                        }
                    }
                } else {
                    System.out.print(seatStatus1.charAt(index2) + " ");
                }
            }
            System.out.println();

        }
    }

    public void display() {
        if (this.isEmpty()) {
            System.out.println("Empty show list!!!");
            return;
        }
        System.out.println("Show list");
        System.out.println("-------------------------------------");
        System.out.println("ID\tName\t\tPrice");
        for (Booking x : this) {
            x.display();
        }
    }

}
