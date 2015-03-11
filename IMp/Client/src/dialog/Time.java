package dialog;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is simple realisation getting time in current moment.
 *
 * @author Dozmorov Nikolay
 * @version 0.1
 */
public class Time implements Serializable {

    /** Hours of creation */
    final private int hours;

    /** Minutes if creation */
    final private int minutes;

    /** Seconds of creation */
    final private int seconds;

    /**
     * Default constructor. Save current time.
     */
    public Time() {
        //Get current time and then get H, M, S.
        Date date = Calendar.getInstance().getTime();

        hours = date.getHours();
        minutes = date.getMinutes();
        seconds = date.getSeconds();
    }

    /**
     * Get time as String.
     *
     * @return String with time value.
     */
    public String toString() {
        return new String(hours + ":" + minutes + ":" + seconds);
    }
}
