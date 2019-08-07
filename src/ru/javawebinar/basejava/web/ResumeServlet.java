package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        boolean resumeExists = false;
        try {
            resume = storage.get(uuid);
            resumeExists = true;
        } catch (NotExistStorageException e) {
            resume = new Resume(uuid, fullName);
        }
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

//        Enumeration<String> parameterNames = request.getParameterNames();
        List<String> parametersList = Collections.list(request.getParameterNames());

        for (SectionType sectionType : SectionType.values()) {
            String content = request.getParameter(sectionType.name());

            if (content != null && content.trim().length() != 0) {
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(sectionType, new TextSection(content));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType, new ListSection(Arrays.asList(content.split("\r\n"))));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> orgContent = new ArrayList<>();

                        for (int i = 0; i < parametersList.size(); i++) {
                            if (parametersList.get(i).contains(".name.") && parametersList.get(i).contains(sectionType.name())) {
                                String name = request.getParameterValues(parametersList.get(i))[0];
                                String url = request.getParameterValues(parametersList.get(i + 1))[0];
                                Organization organization = new Organization(name, url);
                                orgContent.add(organization);
                            }
                            if (parametersList.get(i).contains(".start.") && parametersList.get(i).contains(sectionType.name())) {
                                LocalDate start = LocalDate.parse(request.getParameterValues(parametersList.get(i))[0]);
                                LocalDate end = LocalDate.parse(request.getParameterValues(parametersList.get(i + 1))[0]);
                                String title = request.getParameterValues(parametersList.get(i + 2))[0];
                                String desc = request.getParameterValues(parametersList.get(i + 3))[0];
                                orgContent.get(orgContent.size()-1).addPosition(start, end, title, desc);
                            }
                        }
                        resume.addSection(sectionType, new OrganizationSection(orgContent));
                        break;
                }
            } else {
                resume.getSections().remove(sectionType);
            }
        }

        if (resumeExists) {
            storage.update(resume);
        } else {
            storage.save(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            case "create":
                resume = new Resume("");
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

}
