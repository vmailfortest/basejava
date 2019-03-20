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

        int resumeIndex = getIndex(resume.getUuid());
        if (resumeIndex >= 0) {
            System.out.println("ERROR: Resume is already exists!");
            return;
        }

        storage[size] = resume;
        size++;
    }

    Resume get(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex < 0) {
            System.out.println("ERROR: Resume not found!");
            return null;
        }
        return storage[resumeIndex];
    }

    void update(Resume resume) {
        int resumeIndex = getIndex(resume.getUuid());
        if (resumeIndex < 0) {
            System.out.println("ERROR: Resume for update not found!");
        }
        storage[resumeIndex] = resume;
    }

    void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex < 0) {
            System.out.println("ERROR: Resume for delete not found!");
            return;
        }

        storage[resumeIndex] = storage[size - 1];
        storage[size - 1] = null;
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
