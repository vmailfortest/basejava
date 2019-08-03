<%@ page import="java.time.format.DateTimeFormatter" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <p>

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
            <%=objective.getContent()%><br/><br/>
        </c:when>

        <c:when test="${sectionType.toString()=='PERSONAL'}">
            <c:set var="personal" scope="request" value="${sectionEntry.value}"/>
            <jsp:useBean id="personal" type="ru.javawebinar.basejava.model.TextSection" scope="request"/>
            <%=personal.getContent()%><br/><br/>
        </c:when>

        <c:when test="${sectionType.toString()=='ACHIEVEMENT'}">
            <c:set var="achievement" scope="request" value="${sectionEntry.value}"/>
            <jsp:useBean id="achievement" type="ru.javawebinar.basejava.model.ListSection" scope="request"/>
            <c:forEach var="achievementContent" items="${achievement.content}">
                <jsp:useBean id="achievementContent" type="java.lang.String"/>
                <li><%=achievementContent%><br/></li>
            </c:forEach>
        </c:when>

        <c:when test="${sectionType.toString()=='QUALIFICATIONS'}">
            <c:set var="qualifications" scope="request" value="${sectionEntry.value}"/>
            <jsp:useBean id="qualifications" type="ru.javawebinar.basejava.model.ListSection" scope="request"/>
            <c:forEach var="qualificationsContent" items="${qualifications.content}">
                <jsp:useBean id="qualificationsContent" type="java.lang.String"/>
                <li><%=qualificationsContent%><br/></li>
            </c:forEach>
        </c:when>

        <c:when test="${sectionType.toString()=='EXPERIENCE'}">
            <c:set var="experience" scope="request" value="${sectionEntry.value}"/>
            <jsp:useBean id="experience" type="ru.javawebinar.basejava.model.OrganizationSection" scope="request"/>
            <c:forEach var="experienceContent" items="${experience.content}">
                <jsp:useBean id="experienceContent"
                             type="ru.javawebinar.basejava.model.Organization"/>
                <%=experienceContent.getHomepage().toHtml()%><br/>
                <table>
                    <c:forEach var="experiencePosition" items="${experienceContent.positions}">
                        <jsp:useBean id="experiencePosition"
                                     type="ru.javawebinar.basejava.model.Organization.Position"/>
                        <tr>
                            <td width="200" style="vertical-align: top">
                                <%=experiencePosition.dateToHtml()%>
                            </td>
                            <td><b><%=experiencePosition.getTitle()%></b><br/><%=experiencePosition.getDescription()%>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:forEach>
        </c:when>

        <c:when test="${sectionType.toString()=='EDUCATION'}">
            <c:set var="education" scope="request" value="${sectionEntry.value}"/>
            <jsp:useBean id="education" type="ru.javawebinar.basejava.model.OrganizationSection" scope="request"/>
            <c:forEach var="educationContent" items="${education.content}">
                <jsp:useBean id="educationContent"
                             type="ru.javawebinar.basejava.model.Organization"/>
                <%=educationContent.getHomepage().toHtml()%><br/>
                <table>
                    <c:forEach var="educationPosition" items="${educationContent.positions}">
                        <jsp:useBean id="educationPosition"
                                     type="ru.javawebinar.basejava.model.Organization.Position"/>
                        <tr>
                            <td width="200" style="vertical-align: top">
                                <%=educationPosition.getStartDate()%>
                            </td>
                            <td><b><%=educationPosition.getTitle()%></b><br/><%=educationPosition.getDescription()%>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:forEach>
        </c:when>


    </c:choose>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>