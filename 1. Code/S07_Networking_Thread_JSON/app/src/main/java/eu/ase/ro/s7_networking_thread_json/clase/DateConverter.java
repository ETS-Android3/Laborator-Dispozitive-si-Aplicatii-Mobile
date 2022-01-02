package eu.ase.ro.s7_networking_thread_json.clase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    private static final String FORMAT_DATE = "dd-MM-yyyy";
    private final SimpleDateFormat simpleDateFormat;

    public DateConverter() {

        simpleDateFormat = new SimpleDateFormat(FORMAT_DATE, Locale.US);
    }


    public Date fromString(String value) {
        try {
            return simpleDateFormat.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    public String toString(Date value) {
        if (value == null) {
            return null;
        }
        return simpleDateFormat.format(value);
    }
}