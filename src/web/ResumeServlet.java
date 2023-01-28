package web;

import utils.Config;
import storage.Storage;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {
    private static ServletContext context;

    public void init() {
        context = getServletContext();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        if (name == null) {
            response.getWriter().write("Hello, dudes!");
        } else {
            response.getWriter().printf("Hello, %s!", name);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        Storage sqlStorage = Config.getInstance().getStorage();
        if (uuid == null) {
            response.getWriter().write(
                    ResumeHtmlFormatter.formHtmlPage(
                            ResumeHtmlFormatter.resumeListToHtml(sqlStorage.getAllSorted(), request.getRequestURL().toString())));
        } else {
            response.getWriter().write(
                    ResumeHtmlFormatter.formHtmlPage(
                            ResumeHtmlFormatter.resumeToHtml(sqlStorage.get(uuid), request.getRequestURL().toString())));
        }
    }

    public static ServletContext getContext() {
        return context;
    }
}
