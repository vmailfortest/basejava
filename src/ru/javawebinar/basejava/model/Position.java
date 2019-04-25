package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class Position {
    private String title;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private String shortDescription;
    private String longDescription;

    public Position(String title, LocalDate periodStart, LocalDate periodEnd, String shortDescription, String longDescription) {
        this.title = title;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }
}
