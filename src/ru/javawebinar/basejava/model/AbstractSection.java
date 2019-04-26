package ru.javawebinar.basejava.model;

public abstract class AbstractSection<C> {

    public abstract void setContent(C content);

    public abstract C getContent();
}
