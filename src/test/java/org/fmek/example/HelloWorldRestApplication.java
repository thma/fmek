package org.fmek.example;

import org.fmek.EmulateJeeContainerConfiguration;
import org.fmek.example.resources.HelloWorldResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * http://localhost:8080/hello-world/greetings   lists all available Greeting entries
 *
 * http://localhost:8080/hello-world?name=Silke  add's a new Greeting entry for Silke
 *
 * http://localhost:8080/hello-world             add's a new Greeting for Stranger
 */

@SpringBootApplication
public class HelloWorldRestApplication extends ResourceConfig {

  /**
   * Configure the application. Normally all you would need to do it add sources (e.g.
   * config classes) because other settings have sensible defaults. You might choose
   * (for instance) to add default command line arguments, or set an active Spring
   * profile.
   *
   * @param builder a builder for the application context
   * @return the application builder
   * @see org.springframework.boot.builder.SpringApplicationBuilder
   */
  //@Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    //return super.configure(builder);
    return builder.sources(HelloWorldRestApplication.class);
  }

    public HelloWorldRestApplication() {
    register(RequestContextFilter.class);
    register(HelloWorldResource.class);
  }

  public static void main(String[] args) {
    Object[] contextClasses = {HelloWorldRestApplication.class, EmulateJeeContainerConfiguration.class};
    SpringApplication.run(contextClasses, args);

//    new HelloWorldRestApplication().configure(
//        new SpringApplicationBuilder(HelloWorldRestApplication.class)).run(args);

  }

}