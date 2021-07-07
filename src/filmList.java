
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
public class filmList extends Vector<Film> {

    Scanner sc = new Scanner(System.in);

    public filmList() {
        super();
    }
    String name;
    String country;
    int genres;
    int filmID;
    int filmIDmax = 0;
    String year;

    public void addFromFile(String filename) {
        ArrayList<Integer> intList = new ArrayList<>();
        try {
            File f = new File(filename);
            if (f.exists() == false) {
                return;
            }
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String detail;
            while ((detail = bfr.readLine()) != null) {
                String[] stk = detail.split("\t");
                filmID = Integer.parseInt(stk[0]);
                intList.add(filmID);
                genres = Integer.parseInt(stk[1]);
                name = stk[2];
                country = stk[4];
                year = stk[3];
                
                Film addFilm = new Film(filmID, genres, name, country, year);
                this.add(addFilm);
                
            }
            filmIDmax = Collections.max(intList);
            fr.close();
            bfr.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void saveToFile(String Films) {
        if (this.isEmpty()) {
            System.out.println("Empty film list!!!");
            return;
        }
        try {
            File f = new File(Films);
            FileWriter fw = new FileWriter(Films);
            PrintWriter pw = new PrintWriter(fw);
            for (Film x : this) {
                pw.println(x.getFilmID() + "\t" + x.getGenre() + "\t"
                        + x.getName() + "\t" + x.getYear() + "\t"
                        + x.getCountry());
            }
            pw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int find(String fname) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getName().toLowerCase().equals(fname)) {
                return i;
            }
        }
        return -1;
    }
    
    public void addNewFilm() {
        int newID;
        String newName;
        int newGenres;
        String newCountry;
        String newYear;
        Scanner sc = new Scanner(System.in);
        int pos;
        System.out.println("Enter new film information: ");
        
        newID = this.filmIDmax + 1;
        do {

            System.out.print("Enter new film name: ");
            newName = sc.nextLine();
            pos = find(newName.toLowerCase());
            if (pos >= 0) {
                System.out.println("This film is already exist!!!");
            }

        } while (pos >= 0);
        System.out.println("Enter film's genres (1 - 11): \n"
                + "1	Action\n"
                + "2	Adventure\n"
                + "3	Comedy\n"
                + "4	Crime&Ganster\n"
                + "5	Drama\n"
                + "6	Epics/Historical\n"
                + "7	Horror\n"
                + "8	Musicals/dance\n"
                + "9	Science fiction\n"
                + "10	War\n"
                + "11	Westerns");
        newGenres = Integer.parseInt(sc.next());
        System.out.print("Enter film's published year: ");
        newYear = sc.next();
        System.out.print("Enter film's country: ");
        newCountry = sc.next().toLowerCase();
        

        this.add(new Film(newID, newGenres, newName, newCountry.toUpperCase(), newYear));
        System.out.println("New film has been added!!!");
    }

    public void display() {
        if (this.isEmpty()) {
            System.out.println("Empty film list!!!");
            return;
        }
        Collections.sort(this);
        System.out.println("Film List");
        System.out.println("-----------------------------------------");
        System.out.println("Film ID\tGernes\tName\t\t\t\tYear\tCountry");
        for (Film x : this) {
            x.display();
        }
    }
}
