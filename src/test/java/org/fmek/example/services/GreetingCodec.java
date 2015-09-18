package org.fmek.example.services;

import org.fmek.example.domain.Greeting;

import javax.inject.Named;
import java.util.StringTokenizer;

/**
 * Created by thma on 18.09.2015.
 */
@Named
public class GreetingCodec {

  public Greeting stringToGreeting(String input) {
    Greeting result = new Greeting();
    StringTokenizer stringTokenizer = new StringTokenizer(input, ":");
    Long id = new Long(stringTokenizer.nextToken());
    String content = stringTokenizer.nextToken();
    result.setId(id);
    result.setContent(content);
    return result;
  }

  public String greetingToString(Greeting greeting) {
    return greeting.toString();
  }
}
