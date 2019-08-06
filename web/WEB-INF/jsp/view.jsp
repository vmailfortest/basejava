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

        <c:when test="${sectionType.toString()=='OBJECTIVE' || sectionType.toString()=='PERSONAL'}">
            <c:set var="textSectionValue" scope="request" value="${sectionEntry.value}"/>
            <jsp:useBean id="textSectionValue" type="ru.javawebinar.basejava.model.TextSection" scope="request"/>
            <%=textSectionValue.getContent()%><br/><br/>
        </c:when>

        <c:when test="${sectionType.toString()=='ACHIEVEMENT' || sectionType.toString()=='QUALIFICATIONS'}">
            <c:set var="listSectionValue" scope="request" value="${sectionEntry.value}"/>
            <jsp:useBean id="listSectionValue" type="ru.javawebinar.basejava.model.ListSection" scope="request"/>
            <c:forEach var="listSectionContent" items="${listSectionValue.content}">
                <jsp:useBean id="listSectionContent" type="java.lang.String"/>
                <li><%=listSectionContent%><br/></li>
            </c:forEach>
        </c:when>

        <c:when test="${sectionType.toString()=='EXPERIENCE' || sectionType.toString()=='EDUCATION'}">
            <c:set var="orgSectionValue" scope="request" value="${sectionEntry.value}"/>
            <jsp:useBean id="orgSectionValue" type="ru.javawebinar.basejava.model.OrganizationSection" scope="request"/>
            <c:forEach var="organization" items="${orgSectionValue.content}">
                <jsp:useBean id="organization"
                             type="ru.javawebinar.basejava.model.Organization"/>
                <%=organization.getHomepage().toHtml()%><br/>
                <table>
                    <c:forEach var="position" items="${organization.positions}">
                        <jsp:useBean id="position"
                                     type="ru.javawebinar.basejava.model.Organization.Position"/>
                        <tr>
                            <td width="200" style="vertical-align: top">
                                <%=position.dateToHtml()%>
                            </td>
                            <td><b><%=position.getTitle()%></b><br/><%=position.getDescription()%>
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