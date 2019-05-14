package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.ResumeTestData;

public class AbstractFileStorageTest extends AbstractStorageTest{
    protected AbstractFileStorageTest(Storage storage) {
        super(storage);
    }

    static {
        RESUME_1.setContacts(ResumeTestData.resume.getContacts());
        RESUME_1.setSections(ResumeTestData.resume.getSections());
        RESUME_2.setContacts(ResumeTestData.resume.getContacts());
        RESUME_2.setSections(ResumeTestData.resume.getSections());
        RESUME_3.setContacts(ResumeTestData.resume.getContacts());
        RESUME_3.setSections(ResumeTestData.resume.getSections());
        RESUME_4.setContacts(ResumeTestData.resume.getContacts());
        RESUME_4.setSections(ResumeTestData.resume.getSections());
    }
}
