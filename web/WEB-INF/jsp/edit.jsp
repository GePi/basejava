<%@ page import="model.ContactType" %>
<%@ page import="model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="model.Resume" scope="request"/>
    <jsp:useBean id="isNew" type="java.lang.Boolean" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="<%=request.getAttribute("baseUrl")%>" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="isNew" value="${isNew}">
        <dl class="dl_edit">
            <dt class="dt">Имя:</dt>
            <dd class="dd"><input type="text" name="fullName" size="50" value="${resume.fullName}" required></dd>
        </dl>

        <span class="section_title">Контакты:</span>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl class="dl_edit">
                <dt class="dt"> ${type.title} </dt>
                <dd class="dd"><input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}">
                </dd>
            </dl>
        </c:forEach>
        <jsp:include page="fragments/editform_textsection.jsp">
            <jsp:param name="sectionType" value="<%=SectionType.OBJECTIVE.name()%>"/>
        </jsp:include>
        <jsp:include page="fragments/editform_textsection.jsp">
            <jsp:param name="sectionType" value="<%=SectionType.PERSONAL.name()%>"/>
        </jsp:include>
        <jsp:include page="fragments/editform_listsection.jsp">
            <jsp:param name="sectionType" value="<%=SectionType.QUALIFICATION.name()%>"/>
        </jsp:include>
        <jsp:include page="fragments/editform_listsection.jsp">
            <jsp:param name="sectionType" value="<%=SectionType.ACHIEVEMENT.name()%>"/>
        </jsp:include>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>