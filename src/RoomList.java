
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

/*
 *  This code is written by Nguyen Hoang Minh
 * 
 */
/**
 *
 * @author luugia
 */
public class RoomList extends Vector<Room> {
    Utinity u = new Utinity();
    Scanner sc = new Scanner(System.in);

    public RoomList() {
        super();
    }
    int roomID;
    int roomIDmax = 0;
    String name;
    int rows;
    int columns;

    public void addFromFile(String filename) {
        try {
            File f = new File(filename);
            if (f.exists() == false) {
                return;
            }
            ArrayList<Integer> intList = new ArrayList<>();
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String detail;
            while ((detail = bfr.readLine()) != null) {
                String[] stk = detail.split("\t");
                roomID = Integer.parseInt(stk[0]);
                intList.add(roomID);
                name = stk[1];
                rows = Integer.parseInt(stk[2]);
                columns = Integer.parseInt(stk[3]);

                Room addRoom = new Room(roomID, name, rows, columns);
                this.add(addRoom);
            }
            roomIDmax = Collections.max(intList);
            fr.close();
            bfr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveToFile(String room) {
        if (this.isEmpty()) {
            System.out.println("Empty room list!!!");
            return;
        }
        try {
            File f = new File(room);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (Room x : this) {
                pw.println(x.getRoomID() + "\t" + x.getName() + "\t"
                        + x.getRows() + "\t" + x.getColumns());
            }
            fw.close();
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private int find(String rname){
        for (int i = 0; i < this.size(); i++){
            if (this.get(i).getName().toLowerCase().equals(rname)){
                return i;
            }
        }
        return -1;
    }
    
    public void addNewRoom(){
        int newRoomID;
        String newname;
        int newRows;
        int newColumns;
        int pos;
        System.out.println("Enter new room information");
        System.out.print("Enter Room number: ");
        newRoomID = this.roomIDmax + 1;
        do {            
            System.out.print("Enter new room name: ");
            newname = sc.nextLine();
            pos = find(newname.toLowerCase());
            if(pos >= 0){
                System.out.println("This room is already exist!!!");
            }
        } while (pos >=0);

        newRows = u.getInt("Enter number row in room: ");
        newColumns = u.getInt("Enter number of columns in room: ");
        
        this.add(new Room(newRoomID, newname, newRows, newColumns));
        System.out.println("New room has been added!!!");
    }
    
    public void display(){
        if(this.isEmpty()){
            System.out.println("Empty room list!!!");
            return; 
        }
        Collections.sort(this);
        System.out.println("Room list");
        System.out.println("-------------------------------------");
        System.out.println("RoomID\tName\t\tNo.Rows\tNo.Cols");
        for (Room x : this){
            x.display();
        }
    }
}
