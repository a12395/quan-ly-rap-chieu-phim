
import java.util.Scanner;

public class Utinity {

    Scanner sc = new Scanner(System.in);

    public int getInt(String msg) {
        boolean check;
        int choose = 0;
        do {
            try {
                System.out.print(msg);
//                System.out.println();
                choose = Integer.parseInt(sc.nextLine());
                break;

            } catch (NumberFormatException e) {
                System.out.println("Nhập sai rồi bạn ơi!!! Mời bạn nhập lại");
                check = true;

            }
        } while (check);

        return choose;
    }

    public float getFloat(String msg) {
        boolean check;
        float choose = 0;
        do {
            try {
                System.out.print(msg);
                choose = Float.parseFloat(sc.nextLine());
                break;

            } catch (NumberFormatException e) {
                System.out.println("Nhập sai rồi bạn ơi!!! Mời bạn nhập lại");
                check = true;
            }
        } while (check);
        return choose;
    }

    public String getString(String msg) {
        boolean check;
        String choose = "";
        do {
            try {
                System.out.print(msg);
                choose = sc.nextLine();
                break;

            } catch (NumberFormatException e) {
                System.out.println("Nhập sai rồi bạn ơi!!! Mời bạn nhập lại");
                check = true;
            }
        } while (check);
        return choose;
    }
}
