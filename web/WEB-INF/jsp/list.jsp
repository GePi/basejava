<%@ page import="model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table>
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="model.Resume"/>
            <tr>
                <td>
                    <a href="<%=request.getAttribute("baseUrl")%>?action=view&uuid=${resume.uuid}">${resume.fullName}</a>
                </td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
                </td>
                <td><a href="<%=request.getAttribute("baseUrl")%>?action=edit&uuid=${resume.uuid}">Редактировать</a>
                </td>
                <td><a href="<%=request.getAttribute("baseUrl")%>?action=delete&uuid=${resume.uuid}">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="<%=request.getAttribute("baseUrl")%>?action=create">Создать резюме</a>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>