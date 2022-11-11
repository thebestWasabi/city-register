package edu.petersburg.web;

import edu.petersburg.dao.PersonCheckDao;
import edu.petersburg.dao.PoolConnectionBuilder;
import edu.petersburg.domain.PersonRequest;
import edu.petersburg.domain.PersonResponse;
import edu.petersburg.exception.PersonCheckException;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/check")
@Singleton
public class CheckPersonService {

    public static final Logger logger = LoggerFactory.getLogger(CheckPersonService.class);
    private PersonCheckDao dao;

    @PostConstruct
    public void init() {
        logger.info("SERVICE is created");
        dao = new PersonCheckDao();
        dao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException {
        logger.info(request.toString());
        return dao.checkPerson(request);
    }
}
