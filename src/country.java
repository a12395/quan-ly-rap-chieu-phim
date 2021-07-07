
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/*
 *  This code is written by Nguyen Hoang Minh
 * 
 */
/**
 *
 * @author luugia
 */
public class country {

    String countryCode;
    String countryName;

    public country() {
    }

    public country(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void addFromFile(String fname) {
        try {
            File f = new File(fname);
            if (!f.exists()) {
                return;
            }
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String detail;
            while ((detail = bfr.readLine()) != null) {
                String[] st = detail.split("\t");
                countryCode = st[0];
                countryName = st[1];
                country ctr = new country(countryCode, countryName);
            }
            fr.close();
            bfr.close();
            
            System.out.println("Countries List");
            System.out.println("-----------------------------------------");
            System.out.println("Country code\tName");
            
            
        } catch (Exception e) {
        }
    }

    @Override
    public String toString() {
        return countryCode + "\t\t" + countryName;
    }

    

}
