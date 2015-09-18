package org.fmek.example;

import org.fmek.EmulateJeeContainerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * http://localhost:8080/hello-world/greetings   lists all available Greeting entries
 *
 * http://localhost:8080/hello-world?name=Silke  add's a new Greeting entry for Silke
 *
 * http://localhost:8080/hello-world             add's a new Greeting for Stranger
 */

@SpringBootApplication
public class HelloWorldRestApplication {

  public static void main(String[] args) {
    //Object[] contextClasses = {HelloWorldRestApplication.class, EmulateJeeContainerConfiguration.class};
    SpringApplication.run(EmulateJeeContainerConfiguration.class, args);
  }

}