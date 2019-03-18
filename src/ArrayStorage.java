import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    int storageSize = 3;
    Resume[] storage = new Resume[storageSize];
    int size = 0;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {
        if (size == storageSize) {
            System.out.println("ERROR: Resume storage is full!");
            return;
        }

        int userId = getUserId(r.uuid);
        if (userId >= 0) {
            System.out.println("ERROR: Resume is already exists!");
            return;
        }

        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        int userId = getUserId(uuid);
        if (userId < 0) {
            System.out.println("ERROR: Resume not found!");
            return null;
        }
        return storage[userId];
    }

    void update(Resume r) {
        int userId = getUserId(r.uuid);
        if (userId < 0) {
            System.out.println("ERROR: Resume for update not found!");
        }
        storage[userId] = r;
    }

    void delete(String uuid) {
        int userId = getUserId(uuid);
        if (userId < 0) {
            System.out.println("ERROR: Resume for delete not found!");
            return;
        }

        for (int k = userId; k < size - 1; k++) {
            storage[k] = storage[k + 1];
        }
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResume = Arrays.copyOfRange(storage, 0, size);
        return allResume;
    }

    int size() {
        return size;
    }

    int getUserId(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
