package ua.com.shyrkov;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "sample-servlet", urlPatterns = "/sample")
public class SampleServlet extends HttpServlet {

    private static final long serialVersionUID = -8948379822734246956L;
    private static final Logger log = LoggerFactory.getLogger(SampleServlet.class);
    private Map<String, String> usersInfo;

    public SampleServlet() {
        this.usersInfo = new ConcurrentHashMap<>();
    }

    @Override
    public void init() {
        log.info(getServletName() + " initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter responseBody = resp.getWriter();

        resp.setContentType("text/html");
        responseBody.println("<h1 align=\"center\">Sample Servlet GET method processing</h1>");
        responseBody.println("<h3 align=\"center\">Request from: " + req.getRemoteHost() + "</h3>");

        String remoteAddress = req.getRemoteAddr();
        String userAgent = req.getHeader("User-Agent");
        usersInfo.put(remoteAddress, userAgent);
        for (String key : usersInfo.keySet()) {
            if (remoteAddress.equals(key) && userAgent.equals(usersInfo.get(key))) {
                responseBody.println("<h3 align=\"center\"><b>Remote address: " + key + " :: User agent: " + usersInfo
                        .get(key) + "</b></h3>");
            } else
                responseBody.println("<h3 align=\"center\">Remote address: " + key + " :: User agent: " + usersInfo
                        .get(key) + "</h3>");
        }
    }

    @Override
    public void destroy() {
        log.info(getServletName() + " destroyed");
    }
}
