package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return title.equals(position.title) &&
                periodStart.equals(position.periodStart) &&
                periodEnd.equals(position.periodEnd) &&
                shortDescription.equals(position.shortDescription) &&
                longDescription.equals(position.longDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, periodStart, periodEnd, shortDescription, longDescription);
    }
}
