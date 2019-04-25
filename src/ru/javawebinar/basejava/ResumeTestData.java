package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Vasya");

        EnumMap<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        contacts.put(ContactType.SKYPE, "MySkype");
        contacts.put(ContactType.PHONE, "1234567890");

        EnumMap<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

        AbstractSection objective = new TextSection(SectionType.OBJECTIVE);
        AbstractSection personal = new TextSection(SectionType.PERSONAL);
        AbstractSection achievement = new ListSection(SectionType.ACHIEVEMENT);
        AbstractSection qualifications = new ListSection(SectionType.QUALIFICATIONS);
        AbstractSection experience = new OrganizationSection(SectionType.EXPERIENCE);
        AbstractSection education = new OrganizationSection(SectionType.EDUCATION);

        String objectiveContent = "Java Engineer";
        objective.setContent(objectiveContent);

        String personalContent = "Аналитический склад ума, сильная логика, креативность, инициативность.";
        personal.setContent(personalContent);

        List<String> achievementContent = new ArrayList<>();
        achievementContent.add("Реализация протоколов по приему платежей.");
        achievementContent.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия.");
        achievement.setContent(achievementContent);

        List<String> qualificationsContent = new ArrayList<>();
        qualificationsContent.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix.");
        qualificationsContent.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS.");
        qualifications.setContent(qualificationsContent);

        List<Position> experienceContent = new ArrayList<>();
        Position experiencePosition1 = new Position(
                "Wrike",
                LocalDate.of(2014, 11, 01),
                LocalDate.of(2016, 01, 01),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        experienceContent.add(experiencePosition1);
        Position experiencePosition2 = new Position(
                "Java Online Projects",
                LocalDate.of(2016, 05, 01),
                LocalDate.now(),
                "Автор проекта",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        experienceContent.add(experiencePosition2);
        experience.setContent(experienceContent);

        List<Position> educationContent = new ArrayList<>();
        Position educationPosition1 = new Position(
                "Coursera",
                LocalDate.of(2013, 03, 01),
                LocalDate.of(2013, 05, 01),
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                "");
        educationContent.add(educationPosition1);
        education.setContent(educationContent);

        sections.put(objective.getSectionType(), objective);
        sections.put(personal.getSectionType(), personal);
        sections.put(achievement.getSectionType(), achievement);
        sections.put(qualifications.getSectionType(), qualifications);
        sections.put(experience.getSectionType(), experience);
        sections.put(education.getSectionType(), education);

        resume.setContacts(contacts);
        resume.setSections(sections);
    }
}
