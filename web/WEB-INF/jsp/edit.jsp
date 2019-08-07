<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
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

        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType"/>

            <h3><%=sectionType.getTitle()%>
            </h3><br/>

            <c:choose>

                <c:when test="${sectionType.toString()=='OBJECTIVE' || sectionType.toString()=='PERSONAL'}">
                    <c:set var="textSectionValue" scope="request" value="${resume.getSection(sectionType)}"/>
                    <dd><input type="text" name="${sectionType}" size=30 value=""></dd>
                    <c:if test="${textSectionValue != null}">
                        <jsp:useBean id="textSectionValue" type="ru.javawebinar.basejava.model.TextSection"
                                     scope="request"/>
                        <script type="text/javascript"> document.getElementsByName('${sectionType}')[0].value = '${textSectionValue.content}';</script>
                    </c:if>
                </c:when>

                <c:when test="${sectionType.toString()=='ACHIEVEMENT' || sectionType.toString()=='QUALIFICATIONS'}">
                    <c:set var="listSectionResult" scope="request" value=""/>
                    <textarea rows="5" cols="60" name="${sectionType}"></textarea>
                    <c:set var="listSectionValue" scope="request" value="${resume.getSection(sectionType)}"/>
                    <c:if test="${listSectionValue != null}">
                    <jsp:useBean id="listSectionValue" type="ru.javawebinar.basejava.model.ListSection" scope="request"/>
                        <c:forEach var="listSectionElement" items="${listSectionValue.content}">
                            <jsp:useBean id="listSectionElement" type="java.lang.String"/>
                            <c:set var="listSectionResult" scope="request"
                                   value="${listSectionResult+=listSectionElement}\n"/>
                        </c:forEach>
                        <script type="text/javascript"> document.getElementsByName('${sectionType}')[0].value = '${listSectionResult}';</script>
                    </c:if>
                </c:when>

                <c:when test="${sectionType.toString()=='EXPERIENCE' || sectionType.toString()=='EDUCATION'}">
                    <div id="addOrganization">
                        <p class="setting">
                            <input type="button" id="addOrg" value="Add Organization" onclick="${sectionType}AddOrg();">
                            <br>
                            <span id="${sectionType}.newNameSpan"></span><br>
                            <span id="${sectionType}.newUrlSpan"></span><br>
                            <span id="${sectionType}.newStartSpan"></span><br>
                            <span id="${sectionType}.newEndSpan"></span><br>
                            <span id="${sectionType}.newTitleSpan"></span><br>
                            <span id="${sectionType}.newDescSpan"></span><br>
                            <br><br>
                        </p>
                    </div>
                    <script type="text/javascript">
                        function ${sectionType}AddOrg() {
                            var newOrgName = document.createElement("input");
                            newOrgName.setAttribute("type", "text");
                            newOrgName.setAttribute("name", "${sectionType}.name.new");
                            newOrgName.setAttribute("size", "30");
                            newOrgName.setAttribute("placeholder", "Название");
                            document.getElementById("${sectionType}.newNameSpan").appendChild(newOrgName);
                            var newOrgUrl = document.createElement("input");
                            newOrgUrl.setAttribute("type", "text");
                            newOrgUrl.setAttribute("name", "${sectionType}.link.new");
                            newOrgUrl.setAttribute("size", "30");
                            newOrgUrl.setAttribute("placeholder", "Ссылка");
                            document.getElementById("${sectionType}.newUrlSpan").appendChild(newOrgUrl);
                            var newPosStart = document.createElement("input");
                            newPosStart.setAttribute("type", "text");
                            newPosStart.setAttribute("name", "${sectionType}.start.new");
                            newPosStart.setAttribute("size", "30");
                            newPosStart.setAttribute("placeholder", "Период с");
                            document.getElementById("${sectionType}.newStartSpan").appendChild(newPosStart);
                            var newPosEnd = document.createElement("input");
                            newPosEnd.setAttribute("type", "text");
                            newPosEnd.setAttribute("name", "${sectionType}.end.new");
                            newPosEnd.setAttribute("size", "30");
                            newPosEnd.setAttribute("placeholder", "Период до");
                            document.getElementById("${sectionType}.newEndSpan").appendChild(newPosEnd);
                            var newPosTitle = document.createElement("textarea");
                            newPosTitle.setAttribute("type", "text");
                            newPosTitle.setAttribute("name", "${sectionType}.title.new");
                            newPosTitle.setAttribute("size", "30");
                            newPosTitle.setAttribute("placeholder", "Позиция");
                            document.getElementById("${sectionType}.newTitleSpan").appendChild(newPosTitle);
                            var newPosDesc = document.createElement("textarea");
                            newPosDesc.setAttribute("type", "text");
                            newPosDesc.setAttribute("name", "${sectionType}.desc.new");
                            newPosDesc.setAttribute("size", "30");
                            newPosDesc.setAttribute("placeholder", "Описание");
                            document.getElementById("${sectionType}.newDescSpan").appendChild(newPosDesc);
                        }
                    </script>

                    <input hidden type="text" name="${sectionType}" value="content">
                    <c:set var="orgSectionValue" scope="request" value="${resume.getSection(sectionType)}"/>
                    <c:if test="${orgSectionValue != null}">
                    <jsp:useBean id="orgSectionValue" type="ru.javawebinar.basejava.model.OrganizationSection" scope="request"/>
                    <c:forEach var="organization" items="${orgSectionValue.content}">
                        <p hidden>${orgCounter=orgCounter+1}</p>
                        <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>
                        <dd>Организация: <input type="text" name="${sectionType}.name.${orgCounter}" size=30 value="${organization.homepage.name}"></dd><br>
                        <dd>Ссылка: <input type="text" name="${sectionType}.link.${orgCounter}" size=40 value="${organization.homepage.url}"></dd><br><br>
                        <c:forEach var="position" items="${organization.positions}">
                            <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                            <p hidden>${posCounter=posCounter+1}</p>
                            <dd>с <input type="text" name="${sectionType}.start.${posCounter}" size=10 value="${position.startDate}"></dd>
                            <dd>по <input type="text" name="${sectionType}.end.${posCounter}" size=10 value="${position.endDate}"></dd><br>
                            <textarea rows="2" cols="60" name="${sectionType}.title.${posCounter}">${position.title}</textarea><br>
                            <textarea rows="5" cols="80" name="${sectionType}.desc.${posCounter}">${position.description}</textarea><br><br>
                        </c:forEach>
                    </c:forEach>
                    </c:if>
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