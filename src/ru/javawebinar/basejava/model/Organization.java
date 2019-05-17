package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homepage;

    private List<Position> positions;

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homepage = new Link(name, url);
        this.positions = new ArrayList<>();
        positions.add(new Position(startDate, endDate, title, description));
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

    public static class Position implements Serializable {
        private LocalDate startDate;
        private LocalDate endDate;
        private String title;
        private String description;

        public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");

            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position that = (Position) o;
            return Objects.equals(startDate, that.startDate) &&
                    Objects.equals(endDate, that.endDate) &&
                    Objects.equals(title, that.title) &&
                    Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }
    }
}
