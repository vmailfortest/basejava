package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static Resume generateResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        contacts.put(ContactType.PHONE, "1234567890");
        contacts.put(ContactType.SKYPE, "MySkype");
        contacts.put(ContactType.HOMEPAGE, "http://homepage.com");

        Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

        String objectiveContent = "Java Engineer";

        String personalContent = "Clever and creative.";

        List<String> achievementContent = new ArrayList<>();
        achievementContent.add("Protocols for payments");
        achievementContent.add("Created JavaEE framework");

        List<String> qualificationsContent = new ArrayList<>();
        qualificationsContent.add("Maven + plugin development, Gradle, Ngnix.");
        qualificationsContent.add("Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS.");

        List<Organization> experienceContent = new ArrayList<>();
        Organization experienceOrganization1 = new Organization(
                "Java Online Projects",
                "http://javaops.ru"
        );
        experienceOrganization1.addPosition(
                DateUtil.of(2016, Month.MAY),
                LocalDate.now(),
                "Автор проекта",
                "Создание, организация и проведение Java онлайн проектов и стажировок."
        );
        experienceContent.add(experienceOrganization1);
        Organization experienceOrganization2 = new Organization(
                "Wrike",
                "http://wrike.com"
        );
        experienceOrganization2.addPosition(
                DateUtil.of(2014, Month.NOVEMBER),
                DateUtil.of(2016, Month.JANUARY),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        experienceContent.add(experienceOrganization2);

        List<Organization> educationContent = new ArrayList<>();
        Organization educationOrganization1 = new Organization(
                "Coursera",
                "http://coursera.com"
        );
        educationOrganization1.addPosition(
                DateUtil.of(2013, Month.MARCH),
                DateUtil.of(2013, Month.AUGUST),
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                ""
        );
        educationOrganization1.addPosition(
                DateUtil.of(2012, Month.APRIL),
                DateUtil.of(2010, Month.MAY),
                "Base Java course",
                "Java basic course.");
        educationContent.add(educationOrganization1);

        AbstractSection objective = new TextSection(objectiveContent);
        AbstractSection personal = new TextSection(personalContent);
        AbstractSection achievement = new ListSection(achievementContent);
        AbstractSection qualifications = new ListSection(qualificationsContent);
        AbstractSection experience = new OrganizationSection(experienceContent);
        AbstractSection education = new OrganizationSection(educationContent);

        sections.put(SectionType.OBJECTIVE, objective);
        sections.put(SectionType.PERSONAL, personal);
        sections.put(SectionType.ACHIEVEMENT, achievement);
        sections.put(SectionType.QUALIFICATIONS, qualifications);
        sections.put(SectionType.EXPERIENCE, experience);
        sections.put(SectionType.EDUCATION, education);

        resume.setContacts(contacts);
        resume.setSections(sections);

        return resume;
    }
}
