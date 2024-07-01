package gr.aueb.cf.finalproject01.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

public class DateStampHelper {
    public static String getDateStamp() {
        // Getting current time in millis
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTime);
        String showTime = String.format("%1$tI:%1$tM:%1$tS%1$Tp", calendar);

        // Current  date
        Date now = new Date();
        long timestamp = now.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String dateString = simpleDateFormat.format(timestamp);

        // Final format
        return  dateString;
    }


}
