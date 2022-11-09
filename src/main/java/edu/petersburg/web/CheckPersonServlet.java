package edu.petersburg.web;

import edu.petersburg.dao.PersonCheckDao;
import edu.petersburg.dao.PoolConnectionBuilder;
import edu.petersburg.domain.PersonRequest;
import edu.petersburg.domain.PersonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "CheckPersonServlet", urlPatterns = {"/checkPerson"})
public class CheckPersonServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CheckPersonServlet.class);
    private PersonCheckDao dao;

    @Override
    public void init() throws ServletException {
        logger.info("SERVLET is created");
        dao = new PersonCheckDao();
        dao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        PersonRequest personRequest = new PersonRequest();
        personRequest.setSurName(req.getParameter("surName"));
        personRequest.setGivenName(req.getParameter("givenName"));
        personRequest.setPatronymic(req.getParameter("patronymic"));
        LocalDate dateOfBirth = LocalDate.parse(req.getParameter("dateOfBirth"), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        personRequest.setDateOfBirth(dateOfBirth);
        personRequest.setStreetCode(Integer.parseInt(req.getParameter("streetCode")));
        personRequest.setBuilding(req.getParameter("building"));
        personRequest.setExtension(req.getParameter("extension"));
        personRequest.setApartment(req.getParameter("apartment"));

        try {
            PersonResponse personResponse = dao.checkPerson(personRequest);
            if (personResponse.isRegistered()) {
                resp.getWriter().write("Registered");
            } else {
                resp.getWriter().write("Not registered");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
