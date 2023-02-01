package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {
    public static final DateTimeFormatter toMMYYYFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate lastDayOf(int year, int month) {
        return LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth());
    }

    public static String toMMYYY(LocalDate date) {
        return (NOW.equals(date)) ? "сейчас" : date.format(toMMYYYFormatter);
    }
}
