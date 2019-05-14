package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class PositionWorkPeriod {
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private String title;
    private String description;

    public PositionWorkPeriod(LocalDate periodStart, LocalDate periodEnd, String title, String description) {
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionWorkPeriod that = (PositionWorkPeriod) o;
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
