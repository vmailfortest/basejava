package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String ConvertToMonthYear(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MM/yyyy"));
    }
}
