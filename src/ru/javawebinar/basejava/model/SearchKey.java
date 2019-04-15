package ru.javawebinar.basejava.model;

public class SearchKey {
    private int index;
    private String uuid;

    public SearchKey() {
        this.index = -1;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


}
