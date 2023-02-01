package web;

import model.*;
import org.apache.commons.text.StringEscapeUtils;
import storage.Storage;
import utils.Config;
import utils.JsonParser;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {

    private static final String listUrl = "/WEB-INF/jsp/list.jsp";
    private static final String viewUrl = "/WEB-INF/jsp/view.jsp";
    private static final String editUrl = "/WEB-INF/jsp/edit.jsp";
    private static ServletContext context;
    private Storage storage;
    private volatile String baseUrl = null;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        context = getServletContext();
        storage = Config.getInstance().getStorage();
    }

    private void initAsRequestProcess(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        if (baseUrl == null) {
            synchronized (this) {
                baseUrl = request.getServletPath().substring(1);
            }
        }
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        initAsRequestProcess(request, response);

        String uuid = request.getParameter("uuid");
        boolean isNew = Boolean.parseBoolean(request.getParameter("isNew"));
        String fullName = request.getParameter("fullName");
        Resume r;
        Resume rBeforeUpdate;

        if (!isNew) {
            r = storage.get(uuid);
            var JSONParser = new JsonParser();
            String rStr = JSONParser.write(r, Resume.class);
            rBeforeUpdate = JSONParser.read(rStr, Resume.class);
            r.setFullName(fullName);
        } else {
            if (fullName.trim().isEmpty()) {
                fullName = "Имя не указано";
            }
            r = new Resume(uuid, fullName);
            rBeforeUpdate = null;
        }

        for (var contactType : ContactType.values()) {
            String value = request.getParameter(contactType.name());
            if (value != null && value.trim().length() > 0) {
                value = StringEscapeUtils.escapeHtml4(value.trim());
                r.addContact(contactType, value);
            } else {
                r.getContacts().remove(contactType);
            }
        }


        for (var sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            if (value == null) {
                continue;
            }
            if (value.trim().length() == 0) {
                r.getSections().remove(sectionType);
                continue;
            }
            value = StringEscapeUtils.escapeHtml4(value.trim());
            switch (sectionType) {
                case OBJECTIVE, PERSONAL -> r.addSection(sectionType, new TextSection(value.trim()));
                case ACHIEVEMENT, QUALIFICATION ->
                        r.addSection(sectionType, new ListSection(Arrays.stream(value.split("\n")).toList()));
            }
        }

        if (!isNew) {
            if (!rBeforeUpdate.equals(r)) {
                storage.update(r);
            }
        } else {
            storage.save(r);
        }

        response.sendRedirect(baseUrl);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        initAsRequestProcess(request, response);

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        request.setAttribute("baseUrl", baseUrl);

        if (action == null || (uuid == null && !action.equals("create"))) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher(listUrl).forward(request, response);
            return;
        }

        Resume r;
        boolean isNew = false;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect(baseUrl);
                return;
            }
            case "view", "edit" -> r = storage.get(uuid);
            case "create" -> {
                r = new Resume();
                isNew = true;
                action = "edit";
            }
            default -> throw new IllegalArgumentException("Action " + action + "is illegal");
        }
        request.setAttribute("resume", r);
        request.setAttribute("isNew", isNew);
        request.getRequestDispatcher(("view".equals(action) ? viewUrl : editUrl)).forward(request, response);
    }

    public static ServletContext getContext() {
        return context;
    }
}
