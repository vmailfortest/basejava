package ru.javawebinar.basejava.model;

public abstract class AbstractSection<C> {
    protected SectionType sectionType;
    protected String sectionTitle;

    public AbstractSection(SectionType sectionType) {
        this.sectionType = sectionType;
        this.sectionTitle = sectionType.getTitle();
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public abstract void setContent(C content);

    public abstract C getContent();
}
