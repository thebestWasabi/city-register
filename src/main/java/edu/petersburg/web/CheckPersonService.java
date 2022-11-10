package edu.petersburg.web;

import edu.petersburg.domain.PersonRequest;
import edu.petersburg.domain.PersonResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/check")
public class CheckPersonService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonResponse checkPerson(PersonRequest request) {
        System.out.println(request.toString());
        return new PersonResponse();
    }
}
