package io.github.coffeepython95;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Lang", urlPatterns = {"/api/langs"}) //defines name and default urls lea ding to this servlet, Jetty initializes this servlet, fins servlets
public class LangServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(LangServlet.class);

    private LangRepository repository;
    private ObjectMapper mapper;

    /**
     * Servlet container Jetty needs no-arg constructor
     */
    @SuppressWarnings("unused")
    public LangServlet() {
        this(new LangRepository(), new ObjectMapper());
    }

    LangServlet(LangRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Got request with params: " + req.getParameterMap());
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(), repository.findAll());
    }
}
