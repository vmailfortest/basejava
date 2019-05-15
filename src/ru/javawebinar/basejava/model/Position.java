package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Position {
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private String title;
    private String description;

    public Position(LocalDate periodStart, LocalDate periodEnd, String title, String description) {
        Objects.requireNonNull(periodStart, "periodStart must not be null");
        Objects.requireNonNull(periodEnd, "periodEnd must not be null");
        Objects.requireNonNull(title, "title must not be null");

        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position that = (Position) o;
        return Objects.equals(periodStart, that.periodStart) &&
                Objects.equals(periodEnd, that.periodEnd) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(periodStart, periodEnd, title, description);
    }
}
