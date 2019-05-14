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
    public static void main(String[] args) {
        Resume resume = new Resume("Vasya");

        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        contacts.put(ContactType.SKYPE, "MySkype");
        contacts.put(ContactType.PHONE, "1234567890");

        Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

        String objectiveContent = "Java Engineer";

        String personalContent = "Аналитический склад ума, сильная логика, креативность, инициативность.";

        List<String> achievementContent = new ArrayList<>();
        achievementContent.add("Реализация протоколов по приему платежей.");
        achievementContent.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия.");

        List<String> qualificationsContent = new ArrayList<>();
        qualificationsContent.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix.");
        qualificationsContent.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS.");

        List<Position> experienceContent = new ArrayList<>();
        Position experiencePosition1 = new Position(
                "Wrike",
                "http://wrike.com",
                DateUtil.of(2014, Month.NOVEMBER),
                DateUtil.of(2016, Month.JANUARY),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        experienceContent.add(experiencePosition1);
        Position experiencePosition2 = new Position(
                "Java Online Projects",
                "http://javaops.ru",
                DateUtil.of(2016, Month.MAY),
                LocalDate.now(),
                "Автор проекта",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        experienceContent.add(experiencePosition2);

        List<Position> educationContent = new ArrayList<>();
        Position educationPosition1 = new Position(
                "Coursera",
                "http://coursera.com",
                DateUtil.of(2013, Month.MARCH),
                DateUtil.of(2013, Month.AUGUST),
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                "");
        educationPosition1.addPositionWorkPeriod(
                DateUtil.of(2012, Month.APRIL),
                DateUtil.of(2010, Month.MAY),
                "Base Java course",
                "Java basic course.");
        educationContent.add(educationPosition1);

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
    }
}
