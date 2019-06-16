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

            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                switch (entry.getKey()){
                    case OBJECTIVE : case PERSONAL :
                        dos.writeUTF(entry.getKey().name());
                        TextSectionWriter(dos, ((TextSection) sections.get(entry.getKey())).getContent());
                        break;
                    case ACHIEVEMENT : case QUALIFICATIONS :
                        dos.writeUTF(entry.getKey().name());
                        ListSectionWriter(dos, ((ListSection) sections.get(entry.getKey())).getContent());
                        break;
                    case EXPERIENCE : case EDUCATION :
                        dos.writeUTF(entry.getKey().name());
                        OrganizationSectionWriter(dos, ((OrganizationSection) sections.get(entry.getKey())).getContent());
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionName = SectionType.valueOf(dis.readUTF());
                switch (sectionName){
                    case OBJECTIVE : case PERSONAL :
                        sections.put(sectionName, TextSectionReader(dis));
                        break;
                    case ACHIEVEMENT : case QUALIFICATIONS :
                        sections.put(sectionName, ListSectionReader(dis));
                        break;
                    case EXPERIENCE : case EDUCATION :
                        sections.put(sectionName, OrganizationSectionReader(dis));
                        break;
                }
            }

            resume.setSections(sections);
            return resume;
        }
    }

    private void TextSectionWriter(DataOutputStream dos, String content) throws IOException {
        dos.writeUTF(content);
    }

    private void ListSectionWriter(DataOutputStream dos, List<String> content) throws IOException {
        dos.writeInt(content.size());
        for (String element : content) {
            dos.writeUTF(element);
        }
    }

    private void OrganizationSectionWriter(DataOutputStream dos, List<Organization> content) throws IOException {
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
    private TextSection TextSectionReader(DataInputStream dis) throws IOException {
        return new TextSection(dis.readUTF());
    }

    private ListSection ListSectionReader(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> content = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            content.add(dis.readUTF());
        }
        return new ListSection(content);
    }

    private OrganizationSection OrganizationSectionReader(DataInputStream dis) throws IOException {
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
        return new OrganizationSection(organizations);
    }
}
