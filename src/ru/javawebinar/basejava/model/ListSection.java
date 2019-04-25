package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection<List<String>> {

    private List<String> content = new ArrayList<>();

    public ListSection(SectionType sectionType) {
        super(sectionType);
    }

    @Override
    public void setContent(List<String> content) {
        this.content.addAll(content);
    }

    @Override
    public List<String> getContent() {
        return content;
    }
}
