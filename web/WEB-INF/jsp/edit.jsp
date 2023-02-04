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
    <form method="post" action="<%=request.getAttribute("baseUrl")%>"
          enctype="application/x-www-form-urlencoded">
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
        <jsp:include page="fragments/editform_orgsection.jsp">
            <jsp:param name="sectionType" value="<%=SectionType.EXPERIENCE.name()%>"/>
        </jsp:include>
        <jsp:include page="fragments/editform_orgsection.jsp">
            <jsp:param name="sectionType" value="<%=SectionType.EDUCATION.name()%>"/>
        </jsp:include>
        <hr id="frm">
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
<script>
    function copyPeriod(butt) {
        let ell = butt.closest("tr");
        let cloneTr = ell.cloneNode(true);

        for (let child of cloneTr.children) {
            if (child.nodeName === 'TD') {
                for (let childChild of child.children) {
                    if (childChild.getAttribute('name') === 'orgPerFrom') {
                        childChild.required = true;
                    }
                    if (childChild.getAttribute('name') === 'orgPerFrom' ||
                        childChild.getAttribute('name') === 'orgPerTo' ||
                        childChild.getAttribute('name') === 'orgTitle' ||
                        childChild.getAttribute('name') === 'orgDescr') {
                        childChild.value = '';
                    }
                }
            }
        }
        ell.parentNode.insertBefore(cloneTr, ell);
    }

    function addOrgSec(section) {
        let bodytag = 'orgbody' + section;
        let orgtab = document.getElementById(bodytag);
        let trlinktag = 'orglink' + section;
        let clonedtrlink = document.getElementById(trlinktag).cloneNode(true);
        let trpertag = 'orgper' + section;
        let clonedtrper = document.getElementById(trpertag).cloneNode(true);

        clonedtrlink.style.display = '';
        clonedtrlink.removeAttribute('id');
        clonedtrper.style.display = '';
        clonedtrper.removeAttribute('id');

        let newSectionId = Date.now();
        for (let child of clonedtrlink.children) {
            if (child.nodeName === 'TD') {
                for (let childChild of child.children) {
                    if (childChild.getAttribute('name') === 'sectionId') {
                        childChild.value = newSectionId
                    }
                }
            }
        }
        for (let child of clonedtrper.children) {
            if (child.nodeName === 'TD') {
                for (let childChild of child.children) {
                    if (childChild.getAttribute('name') === 'sectionIdLink') {
                        childChild.value = newSectionId;
                    }
                    if (childChild.getAttribute('name') === 'orgPerFrom') {
                        childChild.required = true;
                    }
                }
            }
        }
        orgtab.insertBefore(clonedtrper, orgtab.firstChild);
        orgtab.insertBefore(clonedtrlink, orgtab.firstChild);
    }
</script>
</body>
</html>