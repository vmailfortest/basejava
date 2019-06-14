package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            Map<SectionType, AbstractSection> sections = resume.getSections();

            class Helper {
                public void TextSectionWriter(SectionType section) throws IOException {
                    dos.writeUTF(section.name());
                    dos.writeUTF(((TextSection) sections.get(section)).getContent());
                }

                public void ListSectionWriter(SectionType section) throws IOException {
                    dos.writeUTF(section.name());
                    List<String> content = ((ListSection) sections.get(section)).getContent();
                    dos.writeInt(content.size());
                    for (String element : content) {
                        dos.writeUTF(element);
                    }
                }

                public void OrganizationSectionWriter(SectionType section) throws IOException {
                    dos.writeUTF(section.name());
                    List<Organization> content = ((OrganizationSection) sections.get(section)).getContent();
                    dos.writeInt(content.size());
                    for (Organization organization : content) {
                        dos.writeUTF(organization.getHomepage().getName());
                        dos.writeUTF(organization.getHomepage().getUrl());
                        List<Organization.Position> positions = organization.getPositions();
                        dos.writeInt(positions.size());
                        for (Organization.Position position : positions) {
                            dos.writeUTF(position.getStartDate().toString());
                            dos.writeUTF(position.getEndDate().toString());
                            dos.writeUTF(position.getTitle());
                            dos.writeUTF(position.getDescription());
                        }
                    }
                }
            }

            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Helper helper = new Helper();
            helper.TextSectionWriter(SectionType.OBJECTIVE);
            helper.TextSectionWriter(SectionType.PERSONAL);
            helper.ListSectionWriter(SectionType.ACHIEVEMENT);
            helper.ListSectionWriter(SectionType.QUALIFICATIONS);
            helper.OrganizationSectionWriter(SectionType.EXPERIENCE);
            helper.OrganizationSectionWriter(SectionType.EDUCATION);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

            class Helper {
                public void TextSectionReader() throws IOException {
                    sections.put(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
                }

                public void ListSectionReader() throws IOException {
                    SectionType sectionName = SectionType.valueOf(dis.readUTF());
                    int size = dis.readInt();
                    List<String> content = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        content.add(dis.readUTF());
                    }
                    sections.put(sectionName, new ListSection(content));
                }

                public void OrganizationSectionReader() throws IOException {
                    SectionType sectionName = SectionType.valueOf(dis.readUTF());
                    int size = dis.readInt();
                    List<Organization> organizations = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        Organization organization = new Organization(dis.readUTF(), dis.readUTF());
                        int organizationSize = dis.readInt();
                        for (int j = 0; j < organizationSize; j++) {
                            organization.addPosition(
                                    LocalDate.parse(dis.readUTF()),
                                    LocalDate.parse(dis.readUTF()),
                                    dis.readUTF(),
                                    dis.readUTF());
                        }
                        organizations.add(organization);
                    }
                    sections.put(sectionName, new OrganizationSection(organizations));
                }
            }
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            Helper helper = new Helper();
            helper.TextSectionReader();
            helper.TextSectionReader();
            helper.ListSectionReader();
            helper.ListSectionReader();
            helper.OrganizationSectionReader();
            helper.OrganizationSectionReader();

            resume.setSections(sections);
            return resume;
        }
    }
}
