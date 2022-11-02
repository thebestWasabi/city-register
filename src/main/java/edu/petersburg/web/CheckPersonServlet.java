package edu.petersburg.web;

import edu.petersburg.dao.PersonCheckDao;
import edu.petersburg.domain.PersonRequest;
import edu.petersburg.domain.PersonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "CheckPersonServlet", urlPatterns = {"/checkPerson"})
public class CheckPersonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String surName = req.getParameter("surName");
        String givenName = req.getParameter("givenName");
        PersonRequest personRequest = new PersonRequest();
        personRequest.setSurName(surName);
        personRequest.setGivenName(givenName);
        personRequest.setPatronymic("Николаевич");
        personRequest.setDateOfBirth(LocalDate.of(1995, 3, 18));
        personRequest.setStreetCode(1);
        personRequest.setBuilding("10");
        personRequest.setExtension("2");
        personRequest.setApartment("121");

        try {
            PersonCheckDao dao = new PersonCheckDao();
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
