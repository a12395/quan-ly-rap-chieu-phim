/*
 *  This code is written by Nguyen Hoang Minh
 * 
 */

/**
 *
 * @author luugia
 */
public class Room implements Comparable<Room>{
    int roomID;
    String name;
    int rows;
    int columns;

    public Room() {
    }

    public Room(int roomID, String name, int rows, int columns) {
        this.roomID = roomID;
        this.name = name;
       this.rows = rows;
       this.columns = columns;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
    
    
    public void display(){
        System.out.println(roomID + "\t" + name + "\t" + rows + "\t"
                + columns);
        
    }

    @Override
    public int compareTo(Room o) {
        if (roomID <= o.roomID) {
            return 1;
        } else {
            return -1;
        }
    }
}
