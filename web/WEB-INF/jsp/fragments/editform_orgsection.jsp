<%@ page import="model.SectionType" %>
<%@ page import="utils.DateUtils" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="resume" type="model.Resume" scope="request"/>

<c:set var="sectionType" value="${param.sectionType}"/>
<jsp:useBean id="sectionType" type="java.lang.String"/>

<dl class="dl">
    <dt class="dt section_title"><%=SectionType.valueOf(sectionType).getTitle()%>
        <button type="button" class="add_button2" onclick="addOrgSec('${sectionType}')"><img
                src="img/add.png" width="20px" height="20px" alt="Добавить"/></button>
    </dt>
    <dd class="dd">
        <table id="orgtab${sectionType}">
            <tbody id="orgbody${sectionType}">
            <!------------------------- New organization section record template ------------------------->
            <tr id="orglink${sectionType}" style="display: none">
                <td width="150px"></td>
                <td>
                    <input type="hidden" name="sectionType" value="${sectionType}">
                    <input type="hidden" name="sectionId" value="">
                    <input type="text" name="orgName" size="50" placeholder="Название организации" value=""/><br/>
                    <input type="url" name="orgUrl" size="50" placeholder="URL организации" value=""/>
                </td>
            </tr>
            <tr id="orgper${sectionType}" style="display: none">
                <td>
                    <button type="button" onclick="copyPeriod(this)" class="add_button2"><img
                            src="img/add.png" width="20px" height="20px" alt="Добавить"/></button>
                    <br/>
                    <input type="hidden" name="sectionIdLink" value="">
                    <input type="hidden" name="sectionTypeLink" value="${sectionType}">
                    <input type="text" name="orgPerFrom" size="10" pattern="[0-9]{2}/[0-9]{4}" placeholder="ММ/ГГГГ"
                           title="Поле нужно заполнить так: &laquo;ММ/ГГГГ&raquo;"
                           value=""/><br/>
                    <input type="text" name="orgPerTo" size="10" pattern="([0-9]{2}/[0-9]{4})|сейчас"
                           placeholder="ММ/ГГГГ"
                           title="Поле нужно заполнить так: &laquo;ММ/ГГГГ&raquo; или оставить пустым для указания &laquo;сейчас&raquo;"
                           value=""/>
                </td>
                <td>
                    <input type="text" name="orgTitle" size="150" placeholder="Заголовок" value=""/><br/>
                    <textarea wrap="soft" rows="2" cols="150" placeholder="Описание" name="orgDescr"></textarea>
                </td>
            </tr>
            <!------------------------- New organization section record template ------------------------->

            <c:if test="${resume.getSection(SectionType.valueOf(sectionType)) != null}">
                <c:set var="sectionExp" value="${resume.getSection(SectionType.valueOf(sectionType))}"/>
                <jsp:useBean id="sectionExp" type="model.OrganizationSection"/>
                <%int orgId = 0;%>
                <c:forEach var="exp" items="${sectionExp.sortedItems}">
                    <jsp:useBean id="exp" type="model.Organization"/>
                    <tr>
                        <td width="150px">
                        </td>
                        <td>
                            <input type="hidden" name="sectionType" value="${sectionType}">
                            <input type="hidden" name="sectionId" value="<%=++orgId%>">
                            <input type="text" name="orgName" size="50" placeholder="Название организации"
                                   value="<%=exp.getLink().getName()%>"/><br/>
                            <input type="url" name="orgUrl" size="50" type="url" placeholder="URL организации"
                                   value="<%=exp.getLink().getUrl()%>"/>
                        </td>
                    </tr>
                    <c:forEach var="per" items="${exp.periods}">
                        <jsp:useBean id="per" type="model.Organization.Period"/>
                        <tr>
                            <td>
                                <button type="button" onclick="copyPeriod(this)" class="add_button2"><img
                                        src="img/add.png" width="20px" height="20px" alt="Добавить"/></button>
                                <br/>
                                <input type="hidden" name="sectionIdLink" value="<%=orgId%>">
                                <input type="hidden" name="sectionTypeLink" value="${sectionType}">
                                <input type="text" name="orgPerFrom" size="10" pattern="[0-9]{2}/[0-9]{4}"
                                       placeholder="ММ/ГГГГ"
                                       title="Поле нужно заполнить так: &laquo;ММ/ГГГГ&raquo;"
                                       value="<%=DateUtils.toEditDate(per.getFrom())%>" required/><br/>
                                <input type="text" name="orgPerTo" size="10" pattern="([0-9]{2}/[0-9]{4})|сейчас"
                                       placeholder="ММ/ГГГГ"
                                       title="Поле нужно заполнить так: &laquo;ММ/ГГГГ&raquo; или оставить пустым для указания &laquo;сейчас&raquo;"
                                       value="<%=DateUtils.toEditDate(per.getTo())%>"/>
                            </td>
                            <td>
                                <input type="text" name="orgTitle" size="150" placeholder="Заголовок"
                                       value="<%=(per.getTitle() == null) ? "" : per.getTitle()%>"/><br/>
                                <textarea wrap="soft" rows="2" cols="150" placeholder="Описание"
                                          name="orgDescr"><%=(per.getDescription() == null) ? "" : per.getDescription()%></textarea>
                            </td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </dd>
</dl>
