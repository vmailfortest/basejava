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

                <c:when test="${sectionType.toString()=='OBJECTIVE' || sectionType.toString()=='PERSONAL'}">
                    <c:set var="textSectionValue" scope="request" value="${sectionEntry.value}"/>
                    <jsp:useBean id="textSectionValue" type="ru.javawebinar.basejava.model.TextSection" scope="request"/>
                    <dd><input type="text" name="${sectionType}" size=30 value="${textSectionValue.content}"></dd>
                </c:when>

                <c:when test="${sectionType.toString()=='ACHIEVEMENT' || sectionType.toString()=='QUALIFICATIONS'}">
                    <c:set var="listSectionValue" scope="request" value="${sectionEntry.value}"/>
                    <jsp:useBean id="listSectionValue" type="ru.javawebinar.basejava.model.ListSection" scope="request"/>
                    <textarea rows="5" cols="60" name="${sectionType}"><c:forEach var="listSectionElement" items="${listSectionValue.content}"><jsp:useBean id="listSectionElement" type="java.lang.String"/>${listSectionElement}&#13;&#10;</c:forEach></textarea>
                </c:when>

                <c:when test="${sectionType.toString()=='EXPERIENCE' || sectionType.toString()=='EDUCATION'}">
                    <c:set var="orgSectionValue" scope="request" value="${sectionEntry.value}"/>
                    <jsp:useBean id="orgSectionValue" type="ru.javawebinar.basejava.model.OrganizationSection" scope="request"/>
                    <c:forEach var="organization" items="${orgSectionValue.content}">
                        <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>
                        <dd>Организация: <input type="text" name="${sectionType}" size=30 value="${organization.homepage.name}"></dd><br>
                        <dd>Ссылка: <input type="text" name="${sectionType}" size=40 value="${organization.homepage.url}"></dd><br><br>
                        <c:forEach var="position" items="${organization.positions}">
                            <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                            <dd>с <input type="text" name="${sectionType}" size=10 value="${position.startDate}"></dd>
                            <dd>по <input type="text" name="${sectionType}" size=10 value="${position.endDate}"></dd><br>
                            <textarea rows="2" cols="60" name="${sectionType}">${position.title}</textarea><br>
                            <textarea rows="5" cols="80" name="${sectionType}">${position.description}</textarea><br><br>
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