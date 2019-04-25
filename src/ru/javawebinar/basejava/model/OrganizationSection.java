package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection<List<Position>> {

    private List<Position> content = new ArrayList<>();

    public OrganizationSection(SectionType sectionType) {
        super(sectionType);
    }

    @Override
    public void setContent(List<Position> content) {
        this.content.addAll(content);
    }

    @Override
    public List<Position> getContent() {
        return content;
    }
}
