package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Position {
    private Link homepage;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private String title;
    private String description;

    public Position(String name, String url, LocalDate periodStart, LocalDate periodEnd, String title, String description) {
        Objects.requireNonNull(periodStart, "periodStart must not be null");
        Objects.requireNonNull(periodEnd, "periodEnd must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.homepage = new Link(name, url);
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(homepage, position.homepage) &&
                Objects.equals(periodStart, position.periodStart) &&
                Objects.equals(periodEnd, position.periodEnd) &&
                Objects.equals(title, position.title) &&
                Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homepage, periodStart, periodEnd, title, description);
    }
}
