<%@ page import="model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="resume" type="model.Resume" scope="request"/>

<c:set var="sectionType" value="${param.sectionType}"/>
<jsp:useBean id="sectionType" type="java.lang.String"/>

<c:set var="textSectionValue" value=""/>

<c:if test="${resume.getSection(SectionType.valueOf(sectionType)) != null}">
    <c:set var="textSection" value="${resume.getSection(SectionType.valueOf(sectionType))}"/>
    <jsp:useBean id="textSection" type="model.TextSection"/>
    <c:set var="textSectionValue" value="<%=textSection.getText()%>"/>
</c:if>
<dl class="dl_edit">
    <dt class="dt section_title"><%=SectionType.valueOf(sectionType).getTitle()%></dt>
    <dd class="dd">
     <textarea wrap="soft" rows="2" cols="150"
               name="<%=SectionType.valueOf(sectionType).name()%>">${textSectionValue}</textarea>
    </dd>
</dl>