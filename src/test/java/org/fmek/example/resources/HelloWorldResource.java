package org.fmek.example.resources;

import org.fmek.example.domain.Greeting;
import org.fmek.example.services.CountService;
import org.fmek.example.services.GreetingCrudService;
import org.fmek.example.services.GreetingPostingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/hello-world")
public class HelloWorldResource {

  private static final String template = "Hello, %s!";

  @Inject
  private GreetingCrudService greetingCrudService;

  @Inject
  private GreetingPostingService greetingPostingService;


  @Transactional
  @RequestMapping(method=RequestMethod.GET)
  public @ResponseBody Greeting sayHello(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) throws JMSException {
    Greeting greeting = new Greeting(String.format(template, name));
    greetingCrudService.store(greeting);
    greetingPostingService.sendGreeting(greeting);
    return greeting;
  }

  @RequestMapping(path = "/greetings", method=RequestMethod.GET)
  public @ResponseBody List<Greeting> allGreetings() {
    return greetingCrudService.getAllGreetings();
  }

}
