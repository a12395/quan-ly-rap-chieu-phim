
import java.util.Scanner;


public class main {

    public static void main(String[] args) {
        String filmFile = "Films.txt";
        String roomFile = "Rooms.txt";
        String showFile = "Shows.txt";
        Scanner sc = new Scanner(System.in);
        Utinity u = new Utinity();
        filmList fl = new filmList();
        showList s1 = new showList();
        RoomList rl = new RoomList();
        BookingShow bks = new BookingShow();

//        fl.addFromFile(filmFile);
        rl.addFromFile(roomFile);
        
        boolean done = true;
        do {

            int choose = u.getInt("IA1501 Group 3: "
                    + "\n - HE150061 Nguyen Hoang Minh"
                    + "\n - HE150062 Vu Thanh Dat"
                    + "\n - HE153787 Trinh Quang Vinh"
                    + "\n 1 - Register a film"
                    + "\n 2 - Register a show"
                    + "\n 3 - Book seat for new show"
                    + "\n 4 - Edit show"
                    + "\n 5 - Delete booking"
                    + "\n 6 - Exit"
                    + "\n Please enter 1, 2, 3, 4, 5 or 6: ");

            switch (choose) {
                case 1:
                    fl.addNewFilm();
                    fl.display();
                    fl.saveToFile(filmFile);
                    break;
                case 2:
                    s1.showRegister();
                    s1.display();
                    s1.saveToFile(showFile);
                    s1.clear();
                    s1.addFromFile(showFile);
                    break;
                case 3:
                    
                    bks.bookShow();
                    break;
                case 4:
                    s1.addFromFile(showFile);
//                    s1.addFromFile(showFile);
                    s1.EditShow();
//                    s1.saveToFile(showFile);
                    s1.clear();
//                    s1.addFromFile(showFile);
                    break;
                case 5:
                    bks.deleteBooking();
                    bks.saveToFile("bookings.txt");
                    bks.clear();
                    break;
                case 6:
                    done = false;
                    break;
                default:
                    System.out.println("We don't have that choice!!!");
            }
            System.out.print("Hoàn thành!!!Bạn có muốn tiếp tục? (Y/N) ");
            boolean ok = true;
            do {
                String ch = sc.next();
                switch (ch.toLowerCase()) {
                    case "y":
                        ok = false;
                        break;
                    case "n":
                        ok = false;
                        done = false;
                        break;
                    default:
                        System.out.println("Chỉ có y hoặc n thôi bạn ơi");
                }
            } while (ok);
        } while (done);
    }
}
