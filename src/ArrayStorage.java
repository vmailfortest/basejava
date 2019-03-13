/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        };
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString() == uuid){ return storage[i]; }
        };
        return null;
    }

    void delete(String uuid) {
        boolean removed = false;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString() == uuid){
                storage[i] = null;
                removed = true;

                size--;
            }

            if (removed){
                storage[i] = storage[i+1];
            }
        };

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResume = new Resume[size];
        for (int i = 0; i < size; i++) {
            allResume[i]=storage[i];
        };
        return allResume;
    }

    int size() {
        return size;
    }
}
