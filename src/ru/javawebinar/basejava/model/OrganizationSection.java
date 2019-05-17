package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> content = new ArrayList<>();

    public OrganizationSection(List<Organization> content) {
        Objects.requireNonNull(content, "Content must not be null");
        this.content = content;
    }

    public void setContent(List<Organization> content) {
        this.content.addAll(content);
    }

    public List<Organization> getContent() {
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