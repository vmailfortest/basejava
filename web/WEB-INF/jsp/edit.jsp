<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <h3>Секции:</h3>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType,
                                ru.javawebinar.basejava.model.AbstractSection>"/>

            <h3><%=sectionEntry.getKey().getTitle()%>
            </h3><br/>

            <c:set var="sectionType" scope="request" value="${sectionEntry.key}"/>

            <c:choose>

                <c:when test="${sectionType.toString()=='OBJECTIVE'}">
                    <c:set var="objective" scope="request" value="${sectionEntry.value}"/>
                    <jsp:useBean id="objective" type="ru.javawebinar.basejava.model.TextSection" scope="request"/>
                    <dd><input type="text" name="${sectionType}" size=30 value="${objective.content}"></dd>
                </c:when>

                <c:when test="${sectionType.toString()=='PERSONAL'}">
                    <c:set var="personal" scope="request" value="${sectionEntry.value}"/>
                    <jsp:useBean id="personal" type="ru.javawebinar.basejava.model.TextSection" scope="request"/>
                    <dd><input type="text" name="${sectionType}" size=30 value="${personal.content}"></dd>
                </c:when>

                <c:when test="${sectionType.toString()=='ACHIEVEMENT'}">
                    <c:set var="achievement" scope="request" value="${sectionEntry.value}"/>
                    <jsp:useBean id="achievement" type="ru.javawebinar.basejava.model.ListSection" scope="request"/>
                    <textarea rows="5" cols="60" name="${sectionType}"><c:forEach var="achievementElement" items="${achievement.content}"><jsp:useBean id="achievementElement" type="java.lang.String"/>${achievementElement}&#13;&#10;</c:forEach></textarea>
                </c:when>

                <c:when test="${sectionType.toString()=='QUALIFICATIONS'}">
                    <c:set var="qualifications" scope="request" value="${sectionEntry.value}"/>
                    <jsp:useBean id="qualifications" type="ru.javawebinar.basejava.model.ListSection" scope="request"/>
                    <textarea rows="5" cols="60" name="${sectionType}"><c:forEach var="qualificationsElement" items="${qualifications.content}"><jsp:useBean id="qualificationsElement" type="java.lang.String"/>${qualificationsElement}&#13;&#10;</c:forEach></textarea>
                </c:when>

                <c:when test="${sectionType.toString()=='EXPERIENCE'}">
                    <c:set var="experience" scope="request" value="${sectionEntry.value}"/>
                    <jsp:useBean id="experience" type="ru.javawebinar.basejava.model.OrganizationSection" scope="request"/>
                    <c:forEach var="experienceContent" items="${experience.content}">
                        <jsp:useBean id="experienceContent" type="ru.javawebinar.basejava.model.Organization"/>
                        <dd>Организация: <input type="text" name="${sectionType}" size=30 value="${experienceContent.homepage.name}"></dd><br>
                        <dd>Ссылка: <input type="text" name="${sectionType}" size=40 value="${experienceContent.homepage.url}"></dd><br><br>
                        <c:forEach var="experiencePosition" items="${experienceContent.positions}">
                            <jsp:useBean id="experiencePosition" type="ru.javawebinar.basejava.model.Organization.Position"/>
                            <dd>с <input type="text" name="${sectionType}" size=10 value="${experiencePosition.startDate}"></dd>
                            <dd>по <input type="text" name="${sectionType}" size=10 value="${experiencePosition.endDate}"></dd><br>
                            <textarea rows="2" cols="60" name="${sectionType}">${experiencePosition.title}</textarea><br>
                            <textarea rows="5" cols="80" name="${sectionType}">${experiencePosition.description}</textarea><br><br>
                        </c:forEach>
                    </c:forEach>
                </c:when>

                <c:when test="${sectionType.toString()=='EDUCATION'}">
                    <c:set var="education" scope="request" value="${sectionEntry.value}"/>
                    <jsp:useBean id="education" type="ru.javawebinar.basejava.model.OrganizationSection" scope="request"/>
                    <c:forEach var="educationContent" items="${education.content}">
                        <jsp:useBean id="educationContent" type="ru.javawebinar.basejava.model.Organization"/>
                        <dd>Организация: <input type="text" name="${sectionType}.name" size=30 value="${educationContent.homepage.name}"></dd><br>
                        <dd>Ссылка: <input type="text" name="${sectionType}.url" size=40 value="${educationContent.homepage.url}"></dd><br><br>
                        <c:forEach var="educationPosition" items="${educationContent.positions}">
                            <jsp:useBean id="educationPosition" type="ru.javawebinar.basejava.model.Organization.Position"/>
                            <dd>С <input type="text" name="${sectionType}" size=10 value="${educationPosition.startDate}"></dd<br>
                            <dd>по <input type="text" name="${sectionType}" size=10 value="${educationPosition.endDate}"></dd><br>
                            Позиция: <br>
                            <textarea rows="2" cols="60" name="${sectionType}">${educationPosition.title}</textarea><br>
                            Описание: <br>
                            <textarea rows="5" cols="80" name="${sectionType}">${educationPosition.description}</textarea><br><br>
                        </c:forEach>
                    </c:forEach>
                </c:when>

            </c:choose>

        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>