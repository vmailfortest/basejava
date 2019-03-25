package src.ru.javawebinar.basejava.model;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>{

    // Unique identifier
    private String uuid;

    public String getUuid(){
        return uuid;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume anotherResume){
        return this.getUuid().compareTo(anotherResume.getUuid());
    }
}
