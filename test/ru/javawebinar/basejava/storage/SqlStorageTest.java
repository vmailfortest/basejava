package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(
                Config.get().getStorageUrl(),
                Config.get().getStorageUsername(),
                Config.get().getStoragePassword()));
    }
}
