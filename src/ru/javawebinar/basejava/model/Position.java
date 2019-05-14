package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {
    private Link homepage;

    private List<PositionWorkPeriod> positionWorkPeriodList;

    public Position(String name, String url, LocalDate periodStart, LocalDate periodEnd, String title, String description) {
        Objects.requireNonNull(periodStart, "periodStart must not be null");
        Objects.requireNonNull(periodEnd, "periodEnd must not be null");
        Objects.requireNonNull(title, "title must not be null");

        this.homepage = new Link(name, url);
        this.positionWorkPeriodList = new ArrayList<>();
        positionWorkPeriodList.add(new PositionWorkPeriod(periodStart, periodEnd, title, description));
    }

    public void addPositionWorkPeriod(LocalDate periodStart, LocalDate periodEnd, String title, String description) {
        positionWorkPeriodList.add(new PositionWorkPeriod(periodStart, periodEnd, title, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(homepage, position.homepage) &&
                Objects.equals(positionWorkPeriodList, position.positionWorkPeriodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homepage, positionWorkPeriodList);
    }
}
