package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {

    private List<Position> content = new ArrayList<>();

    public OrganizationSection(List<Position> content) {
        this.content = content;
    }

    public void setContent(List<Position> content) {
        this.content.addAll(content);
    }

    public List<Position> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}