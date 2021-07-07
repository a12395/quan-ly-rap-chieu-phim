
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class showList extends Vector<Show> {

    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String RESET = "\033[0m";
    int maxID = 0;
    int showID;
    int filmID;
    int roomID;
    String showDate, filmname;
    float price;
    int slotTime;
    int k = 0;
    Scanner sc = new Scanner(System.in);
    Utinity u = new Utinity();
    filmList fl = new filmList();
    Film f2 = new Film();
    RoomList rl = new RoomList();
    int[] checkShowSlot = new int[9];
    ArrayList<Integer> slotList = new ArrayList<>();

    public showList() {
        super();
    }

    public void addFromFile(String fname) {
        ArrayList<Integer> intlist = new ArrayList<>();
        ArrayList<Integer> fID = new ArrayList<>();
        fl.addFromFile("Films.txt");
        try {
            File f = new File(fname);
            if (!f.exists()) {
                return;
            }

            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String detail;
            while ((detail = bfr.readLine()) != null) {
                String[] stk = detail.split("\t");
                showID = Integer.parseInt(stk[0]);
                intlist.add(showID);
                filmID = Integer.parseInt(stk[1]);
                fID.add(filmID);
                roomID = Integer.parseInt(stk[2]);
                showDate = stk[3];
                price = Float.parseFloat(stk[4]);
                slotTime = Integer.parseInt(stk[5]);
                for (int i = 0; i < fID.size(); i++) {
                    for (Film f3 : fl) {
                        if (f3.getFilmID() == fl.get(i).getFilmID()) {
                            filmname = f3.getName();
                        }
                    }
                }
                Show fShow = new Show(showID, filmID, roomID, showDate, price, slotTime, filmname);
                this.add(fShow);

            }

            maxID = Collections.max(intlist);
            fr.close();
            bfr.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int checkF(int fID) {
        for (int i = 0; i < fl.size(); i++) {
            if (fl.get(i).getFilmID() == fID) {
                return i;
            }
        }
        return -1;
    }

    public int checkS(int slot, int roomID) {
        for (Show b : this) {
            if (b.getRoomID() == roomID) {
                slotList.add(b.getSlotTime());
                for (int i = 0; i < slotList.size(); i++) {
                    if (slotList.get(i) == slot) {
                        return i + 1;
                    }
                }
            }
        }
        return -1;
    }

    public int checkSD(int EditID, int slot, int roomID, String showDate) {
        for (Show b : this) {
            if (b.getShowDate().equals(showDate)) {
                if (b.getRoomID() == roomID) {
                    slotList.add(b.getSlotTime());
                }
            }
        }
        for (Show b : this) {
            for (int i = 0; i < slotList.size(); i++) {
                if (slotList.get(i) == b.getSlotTime()) {
                    if (b.getShowID() == EditID) {
                        slotList.set(i, 0);
                    }
                }
            }
        }

        for (int i = 0; i < slotList.size(); i++) {
            if (slotList.get(i) == slot) {
                return i + 1;
            }
        }

        return -1;
    }

    public int checkSID(int showID) {
        for (Show s : this) {
            if (s.getShowID() == showID) {
                return 1;
            }
        }
        return -1;
    }

    public int checkR(int rID) {
        for (int j = 0; j < rl.size(); j++) {
            if (rl.get(j).getRoomID() == rID) {
                return j;
            }
        }
        return -1;
    }

    public void saveToFile(String sfile) {
        if (this.isEmpty()) {
            System.out.println("Empty show list");
            return;
        }
        try {
            File f = new File(sfile);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (Show x : this) {
                pw.println(x.getShowID() + "\t"
                        + x.getFilmID() + "\t"
                        + x.getRoomID() + "\t"
                        + x.getShowDate() + "\t"
                        + x.getPrice() + "\t"
                        + x.getSlotTime());
            }
            fw.close();
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void showRegister() {

        int count = 0;
        int[] showSLot = {0, 0, 0, 0, 0, 0, 0, 0, 0};
//        fl.addFromFile("Films.txt");
        rl.addFromFile("Rooms.txt");

        if (slotList.size() == 9) {
            System.out.println("Full slot!!!");
            return;
        }

        int newShowID;
        int newFilmID;
        int newRoomID;
        String newDate, filname = "";
        int newSlot;
        float newPrice;
        int posF, posR, posS;

        newShowID = this.maxID + 1;

        fl.display();
        do {
            newFilmID = u.getInt("Enter filmID: ");
            posF = checkF(newFilmID);
            if (posF < 0) {
                System.out.println("Film ID does not exist!!!");
            }
        } while (posF < 0);
        rl.display();
        do {

            newRoomID = u.getInt("Enter room ID: ");
            posR = checkR(newRoomID);
            if (posR < 0) {
                System.out.println("Room ID does not exist!!!");
            }
        } while (posR < 0);

        System.out.println("Enter show date (dd/MM/yyyy): ");
        newDate = sc.nextLine();

        newPrice = u.getFloat("Enter new price: ");
//        for (Show b : this)

//        for (Show b : this) {
//            if (newRoomID == b.getRoomID()) {
//                if (count == 9) {
//                    System.out.println("Full slot");
//                    return;
//                } else {
//                    slotList.add(b.getSlotTime());
//                    for (int i = 0; i < slotList.size(); i++) {
//                        if (slotList.get(i) > 0) {
//                            showSLot[b.getSlotTime() - 1] = 1;
//                            count += 1;
//                            
//
//                        } else {
//                            showSLot[i] = 0;
//                        }
//                    }
//                }
//            }
//        }
        for (k = 0; k < showSLot.length; k++) {
            if (showSLot[k] == 1) {
                System.out.print(ANSI_RED_BACKGROUND + showSLot[k] + " " + RESET);
            } else {
                System.out.print(showSLot[k] + " ");
            }
        }

        System.out.println();
        do {
            newSlot = u.getInt("Enter new slot (from 1 to 9): ");
            posS = checkS(newSlot, newRoomID);
            if (posS > 0 || newSlot > 9) {
                System.out.println("This slot is not available, choose another slot!!!");
            }

        } while (posS > 0 || newSlot > 9);
        this.add(new Show(newShowID, newFilmID, newRoomID, newDate, newPrice, newSlot, filname));

        System.out.println("New show has been added!!!");
        showSLot[newSlot - 1] = 1;

        rl.clear();
        fl.clear();
        slotList.clear();

    }

    public void display() {
        if (this.isEmpty()) {
            System.out.println("Empty show list!!!");
            return;
        }
        System.out.println("Show list");
        System.out.println("-------------------------------------");
        System.out.println("ShowID\tFilmID\tRoomID\tDate\t\tPrice\tSlot");
        for (Show x : this) {
            x.display();
        }
    }

    public int getRoomID(int editShowID) {
        int roomiD = 0;
        for (Show b : this) {
            if (b.getShowID() == editShowID) {
                roomiD = b.getRoomID();
            }
        }
        return roomiD;
    }

    public void EditShow() {
//        fl.clear();
//        this.clear();
//        addFromFile("Shows.txt");
        int[] showSLot = {0, 0, 0, 0, 0, 0, 0, 0, 0};
//        fl.addFromFile("Films.txt");
        int newSlot, newFilmID, posF, posSID, posS, EditID;
        int roomID;
        float newPrice;
        String filmName = "";
        ArrayList<String> dateArr = new ArrayList<>();

        for (Show s : this) {
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
        System.out.println("ShowID\tFilm Tilte\t\tRoomID\tPrice\tSlot");

        for (Show s : this) {
            if (s.getShowDate().equals(showDateCheck)) {
                s.display2();
            }
        }
        do {
            EditID = u.getInt("Enter show ID: ");
            posSID = checkSID(EditID);
            if (posSID < 0) {
                System.out.println("No show ID");
            }
        } while (posSID < 0);
        roomID = getRoomID(EditID);
        fl.display();
        do {
            newFilmID = u.getInt("Enter filmID: ");
            posF = checkF(newFilmID);

            if (posF < 0) {
                System.out.println("Film ID does not exist!!!");
            }
        } while (posF < 0);
        
        for (int i = 0; i < fl.size(); i++) {
            if (fl.get(i).getFilmID() == newFilmID) {
                
                filmName = fl.get(i).getName();
            }

        }

        for (Show b : this) {
            if (showDateCheck.equals(b.getShowDate()) && roomID == b.getRoomID()) {
                slotList.add(b.getSlotTime());
            }
        }
        for (Show b : this) {
            if (showDateCheck.equals(b.getShowDate()) && roomID == b.getRoomID()) {
                for (int i = 0; i < slotList.size(); i++) {
                    if (b.getShowID() == EditID) {
                        if (slotList.get(i) == b.getSlotTime()) {
                            showSLot[b.getSlotTime() - 1] = -1;
                        }
                    } else if (slotList.get(i) > 0) {
                        showSLot[b.getSlotTime() - 1] = 1;
                    } else {
                        showSLot[i] = 0;
                    }

                }
            }
        }
        for (k = 0; k < showSLot.length; k++) {

            switch (showSLot[k]) {
                case -1:
                    System.out.print(ANSI_GREEN_BACKGROUND + "1" + " " + RESET);
                    break;
                case 1:
                    System.out.print(ANSI_RED_BACKGROUND + showSLot[k] + " " + RESET);
                    break;
                default:
                    System.out.print(showSLot[k] + " ");
                    break;
            }
        }
        System.out.println();
        slotList.removeAll(slotList);
        do {
            newSlot = u.getInt("Enter new slot (from 1 to 9): ");
            posS = checkSD(EditID, newSlot, roomID, showDateCheck);
            if (posS > 0 || newSlot > 9) {
                System.out.println("This slot is not available, choose another slot!!!");
            }

        } while (posS > 0 || newSlot > 9);
        newPrice = u.getFloat("Enter new price: ");
        this.removeElementAt(EditID - 1);
        
        Show sh = new Show(EditID, newFilmID, roomID, showDateCheck, newPrice, newSlot, filmName);
        this.add(EditID - 1, sh);

        System.out.println("ShowID\tFilm Tilte\t\tRoomID\tPrice\tSlot");
        for (Show s : this) {
            s.display2();
            
        }
        saveToFile("Shows.txt");
        fl.clear();
//        this.clear();
    }
}
