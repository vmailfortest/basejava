package ru.javawebinar.basejava.storage;

import org.junit.Ignore;
import org.junit.Test;

public class MapUuidStorageTest extends AbstractArrayStorageTest {
    public MapUuidStorageTest() {
        super(new MapStorage());
    }

    @Test
    @Ignore
    public void saveOverflow() {

    }
}
