
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
public class genres {
    int code;
    String genres;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
    
    public void addFromFile(String fname){
        File f = new File(fname);
        if (f.exists() == false) return;
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String detail = bfr.readLine();
            while (detail != null){
                String[] stk = detail.split("\t");
                code = Integer.parseInt(stk[0]);
                genres = stk[1];
            }
            fr.close();
            bfr.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
         
    }
    
}
