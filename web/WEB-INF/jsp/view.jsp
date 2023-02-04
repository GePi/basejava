<%@ page import="model.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Не стреляйте в пианиста, он верстает как умет -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?action=edit&uuid=${resume.uuid}"><img src="img/edit.png" alt="Редактировать" width="20px" height="20px"></a></h2>
    <p class="dl_edit">
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry" type="java.util.Map.Entry<model.ContactType,java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <jsp:include page="fragments/view_textsection.jsp">
        <jsp:param name="sectionType" value="<%=SectionType.OBJECTIVE.name()%>"/>
    </jsp:include>
    <jsp:include page="fragments/view_textsection.jsp">
        <jsp:param name="sectionType" value="<%=SectionType.PERSONAL.name()%>"/>
    </jsp:include>
    <jsp:include page="fragments/view_listsection.jsp">
        <jsp:param name="sectionType" value="<%=SectionType.QUALIFICATION.name()%>"/>
    </jsp:include>
    <jsp:include page="fragments/view_listsection.jsp">
        <jsp:param name="sectionType" value="<%=SectionType.ACHIEVEMENT.name()%>"/>
    </jsp:include>
    <jsp:include page="fragments/veiw_organizationsection.jsp">
        <jsp:param name="sectionType" value="<%=SectionType.EXPERIENCE.name()%>"/>
    </jsp:include>
    <jsp:include page="fragments/veiw_organizationsection.jsp">
        <jsp:param name="sectionType" value="<%=SectionType.EDUCATION.name()%>"/>
    </jsp:include>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>