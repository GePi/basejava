package utils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import static java.time.temporal.ChronoUnit.MONTHS;

public class DateUtils {
    public static final DateTimeFormatter TO_MMYYY_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    public static final boolean AS_BEGIN_OF_MONTH = false;
    public static final boolean AS_END_OF_MONTH = true;
    private static final int MONTHS_NEARBY = 0;
    private static final String NOW_STRING = "сейчас";

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate of(String userInputDate, boolean asEndOfMonth) {
        if (userInputDate.trim().isEmpty() || userInputDate.trim().toLowerCase().contains("сей")) {
            if (asEndOfMonth) {
                return NOW;
            } else {
                return null;
            }
        }
        return (asEndOfMonth) ? YearMonth.parse(userInputDate, TO_MMYYY_FORMATTER).atEndOfMonth() : YearMonth.parse(userInputDate, TO_MMYYY_FORMATTER).atDay(1);
    }

    public static LocalDate lastDayOf(int year, int month) {
        return LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth());
    }

    public static String toEditDate(LocalDate date) {
        return (NOW.equals(date)) ? NOW_STRING : date.format(TO_MMYYY_FORMATTER);
    }

    public static String toDisplayDateFrom(LocalDate date) {
        return date.format(TO_MMYYY_FORMATTER);
    }

    public static String toDisplayDateTo(LocalDate date) {
        return (NOW.equals(date) || isNearNow(date)) ? NOW_STRING : date.format(TO_MMYYY_FORMATTER);
    }

    private static boolean isNearNow(LocalDate date) {
        return MONTHS.between(date, LocalDate.now()) == MONTHS_NEARBY;
    }
}