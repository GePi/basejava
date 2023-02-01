<%@ page import="model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="resume" type="model.Resume" scope="request"/>

<c:set var="sectionType" value="${param.sectionType}"/>
<jsp:useBean id="sectionType" type="java.lang.String"/>

<c:if test="${resume.getSection(SectionType.valueOf(sectionType)) != null}">
    <c:set var="textSection" value="${resume.getSection(SectionType.valueOf(sectionType))}"/>
    <jsp:useBean id="textSection" type="model.TextSection"/>
    <dl class="dl">
        <dt class="dt section_title"><%=SectionType.valueOf(sectionType).getTitle()%>
        </dt>
        <dd class="dd"><%=textSection.getText()%>
        </dd>
    </dl>
</c:if>