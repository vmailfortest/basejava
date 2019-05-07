package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    private List<String> content = new ArrayList<>();

    public ListSection(List<String> content) {
        Objects.requireNonNull(content, "Content must not be null");
        this.content = content;
    }

    public void setContent(List<String> content) {
        this.content.addAll(content);
    }

    public List<String> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}