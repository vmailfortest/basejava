package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(String directory) {
        super(directory);
        setSerializationStrategy(new ObjectIOStreamSerialization());
    }

    @Override
    protected void doWrite(Resume resume, OutputStream os) throws IOException {
        serializationStrategy.doWrite(resume, os);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return serializationStrategy.doRead(is);
    }
}
