package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import java.io.IOException;
import java.util.List;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        Storage storage = Config.get().getSqlStorage();
//        File STORAGE_DIR = Config.get().getStorageDir();
//        Storage storage = new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer());

        List<Resume> resumeList = storage.getAllSorted();

        StringBuilder resumesTable = new StringBuilder();
        for (Resume resume : resumeList) {
            resumesTable.append("<tr><td>");
            resumesTable.append(resume.getUuid());
            resumesTable.append("</td><td>");
            resumesTable.append(resume.getFullName());
            resumesTable.append("</td></tr>");
        }

        String name = request.getParameter("name");
        if (name == null) {
            response.getWriter().write(
                    "<html>\n" +
                            "<head>\n" +
                            "<title>Таблица</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<table border=\"1\">\n" +
                            "\n" +
                            resumesTable.toString() +
                            "\n" +
                            "</table>\n" +
                            "</body>\n" +
                            "</html>");
        } else {
            boolean isFound = false;
            for (Resume resume : resumeList) {
                if (resume.getFullName().equals(name)) {
                    response.getWriter().write(resume.getUuid() + " : " + resume.getFullName());
                    isFound = true;
                }
            }
            if (!isFound) {
                response.getWriter().write("Resume not found!");
            }

        }
    }
}
