package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private Link homepage;

    private List<Position> positions;

    public Organization(String name, String url, LocalDate periodStart, LocalDate periodEnd, String title, String description) {
        this.homepage = new Link(name, url);
        this.positions = new ArrayList<>();
        positions.add(new Position(periodStart, periodEnd, title, description));
    }

    public void addPosition(LocalDate periodStart, LocalDate periodEnd, String title, String description) {
        positions.add(new Position(periodStart, periodEnd, title, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization organization = (Organization) o;
        return Objects.equals(homepage, organization.homepage) &&
                Objects.equals(positions, organization.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homepage, positions);
    }
}
