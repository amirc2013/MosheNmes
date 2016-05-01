import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Amir on 5/1/2016.
 */
public class Tester {
    public static void main(String[] args){
        DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1620);
        calendar.set(Calendar.MONTH, 05);
        calendar.set(Calendar.DATE, 11);
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        String d = df.format((new java.sql.Date(calendar.getTime().getTime()))).toString();
        System.out.println(d);
    }
}
