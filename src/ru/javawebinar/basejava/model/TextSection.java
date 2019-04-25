package ru.javawebinar.basejava.model;

public class TextSection extends AbstractSection<String> {

    private String content;

    public TextSection(SectionType sectionType) {
        super(sectionType);
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }
}
