import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int storageSize = 10000;
    private Resume[] storage = new Resume[storageSize];
    private int size = 0;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume resume) {
        if (size == storageSize) {
            System.out.println("ERROR: Resume storage is full!");
            return;
        }

        int userId = getIndex(resume.getUuid());
        if (userId >= 0) {
            System.out.println("ERROR: Resume is already exists!");
            return;
        }

        storage[size] = resume;
        size++;
    }

    Resume get(String uuid) {
        int userId = getIndex(uuid);
        if (userId < 0) {
            System.out.println("ERROR: Resume not found!");
            return null;
        }
        return storage[userId];
    }

    void update(Resume resume) {
        int userId = getIndex(resume.getUuid());
        if (userId < 0) {
            System.out.println("ERROR: Resume for update not found!");
        }
        storage[userId] = resume;
    }

    void delete(String uuid) {
        int userId = getIndex(uuid);
        if (userId < 0) {
            System.out.println("ERROR: Resume for delete not found!");
            return;
        }

        System.arraycopy(storage, userId + 1, storage, userId, size - userId);
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
