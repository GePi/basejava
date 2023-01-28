package web;

import model.Resume;

import java.util.List;

public class ResumeHtmlFormatter {
    public static String formHtmlPage(StringBuilder htmlPart) {
        return "<!DOCTYPE html>" +
                "<html lang=\"ru\">" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<link rel=\"stylesheet\" href=\"css/style.css\">" +
                "<title>Резюме</title>" +
                "</head>" +
                "<body>" +
                htmlPart +
                "</body>" +
                "</html>";
    }

    public static StringBuilder resumeListToHtml(List<Resume> resumes, String resumeDetailUrl) {
        StringBuilder html = new StringBuilder();
        html.append("<table class=\"tg\">")
                .append("<thead>")
                .append("<tr>")
                .append("<th class=\"header_footer\">Список Резюме</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");
        for (var r : resumes) {
            html.append("<tr><td>");
            html.append("<a href=\"").append(resumeDetailUrl).append("?uuid=").append(r.getUuid()).append("\">").append(r.getFullName()).append("</a>");
            html.append("</td></tr>");
        }
        html.append("</tbody>");
        html.append("</table>");
        return html;
    }

    public static StringBuilder resumeToHtml(Resume r, String backUrl) {
        StringBuilder html = new StringBuilder();
        html.append("<table class=\"tg\">");
        html.append("<thead>");
        html.append("<tr>");
        html.append("<th class=\"header_footer\" colspan=\"2\">");
        html.append(r.getFullName());
        html.append("</th>");
        html.append("</tr>");
        html.append("</thead>");

        html.append("<tbody>");
        for (var contact : r.getContacts().entrySet()) {
            html.append("<tr>");
            html.append("<td class=\"typeofall\">").append(contact.getKey().getTitle()).append("</td>");
            html.append("<td>").append(contact.getValue()).append("</td>");
            html.append("</tr>");
        }

        for (var section : r.getSections().entrySet()) {
            html.append("<tr>");
            html.append("<td class=\"typeofall\" colspan=\"2\">").append(section.getKey().getTitle()).append("</td>");
            html.append("</tr>");
            html.append("<tr>");
            html.append("<td colspan=\"2\">").append(section.getValue()).append("</td>");
            html.append("</tr>");
        }

        html.append("<tr>");
        html.append("<td class=\"header_footer\" colspan=\"2\"><a href=\"").append(backUrl).append("\"> К списку резюме </a > </td>");
        html.append("</tr>");
        html.append("</tbody>");
        html.append("</table>");
        return html;
    }
}
