package org.fmek.test;

import org.fmek.EmulateJ2eeContainerConfiguration;
import org.fmek.example.domain.Greeting;
import org.fmek.example.services.CountService;
import org.fmek.example.services.GreetingCrudService;
import org.fmek.example.services.GreetingPostingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.jms.JMSException;
import java.util.List;

/**
 * Created by thma on 11.09.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmulateJ2eeContainerConfiguration.class})
public class GreetingTests {

  @Inject
  private CountService countService;

  @Inject
  private GreetingCrudService greetingCrudService;

  @Inject
  private GreetingPostingService postingService;

  private static final String template = "Hello, %s!";

  @Test
  public void testGreetings() throws JMSException {
    createAndSaveAGreeting();
    createAndSaveAGreeting();

    Greeting g = greetingCrudService.getGreeting(1);
//    assertNotNull(g);
//    assertEquals(1, g.getId());

    List<Greeting> allGreetings =  greetingCrudService.getAllGreetings();
//    assertEquals(2, allGreetings.size());
  }

  private void createAndSaveAGreeting() throws JMSException {
    long id = countService.incrementAndGet();
    Greeting greeting = new Greeting(id, String.format(template, "user-" + id));
    greetingCrudService.store(greeting);
    postingService.sendGreeting(greeting);
  }

}
