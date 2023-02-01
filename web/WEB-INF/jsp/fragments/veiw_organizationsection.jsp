<%@ page import="model.SectionType" %>
<%@ page import="utils.DateUtils" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="resume" type="model.Resume" scope="request"/>

<c:set var="sectionType" value="${param.sectionType}"/>
<jsp:useBean id="sectionType" type="java.lang.String"/>

<c:if test="${resume.getSection(SectionType.valueOf(sectionType)) != null}">
    <dl class="dl">
    <dt class="dt section_title"><%=SectionType.valueOf(sectionType).getTitle()%>
    </dt>
    <c:set var="sectionExp" value="${resume.getSection(SectionType.valueOf(sectionType))}"/>
    <jsp:useBean id="sectionExp" type="model.OrganizationSection"/>
    <dd class="dd">
        <table>
            <c:forEach var="exp" items="${sectionExp.items}">
                <jsp:useBean id="exp" type="model.Organization"/>
                <tr>
                    <td width="150px"></td>
                    <td><a href="<%=exp.getLink().getUrl()%>"><%=exp.getLink().getName()%>
                    </a></td>
                </tr>
                <c:forEach var="per" items="${exp.periods}">
                    <jsp:useBean id="per" type="model.Organization.Period"/>
                    <tr>
                        <td><%=DateUtils.toMMYYY(per.getFrom()) + " - " + DateUtils.toMMYYY(per.getTo())%>
                        </td>
                        <td>
                            <%=(per.getTitle() == null) ? "" : per.getTitle()%><br/>
                            <%=(per.getDescription() == null) ? "" : per.getDescription()%>
                        </td>
                    </tr>
                </c:forEach>
            </c:forEach>
        </table>
    </dd>
    </dl>
</c:if>