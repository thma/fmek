package org.fmek.example.resources;

import org.fmek.example.domain.Greeting;
import org.fmek.example.services.GreetingCrudService;
import org.fmek.example.services.GreetingPostingService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


/**
 * Created by thma on 22.09.2015.
 */
@Transactional
@Path("hello-world")
public class HelloWorldResource {

    private static final String template = "Hello, %s!";

    @Inject
    private GreetingCrudService greetingCrudService;

    @Inject
    private GreetingPostingService greetingPostingService;


    @GET
    @Produces(APPLICATION_JSON)
    public Greeting getHello(@QueryParam("name") String name) throws Exception {
        Greeting greeting = new Greeting(String.format(template, name));
        greetingCrudService.store(greeting);
        greetingPostingService.sendGreeting(greeting);
        return greeting;
    }

    @GET
    @Path("/greetings")
    @Produces(APPLICATION_JSON)
    public List<Greeting> allGreetings() {
        return greetingCrudService.getAllGreetings();
    }

}
