package org.fmek.example;

import org.fmek.CdiInterceptorConfiguration;
import org.fmek.JmsConfiguration;
import org.fmek.example.resources.HelloWorldResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * http://localhost:8080/hello-world/greetings   lists all available Greeting entries
 * <p/>
 * http://localhost:8080/hello-world?name=Silke  add's a new Greeting entry for Silke
 * <p/>
 * http://localhost:8080/hello-world             add's a new Greeting for Stranger
 */

@SpringBootApplication
public class HelloWorldRestApplication extends ResourceConfig {

    public HelloWorldRestApplication() {
        register(RequestContextFilter.class);
        register(HelloWorldResource.class);
    }

    public static void main(String[] args) {
        Object[] contextClasses = {JmsConfiguration.class, CdiInterceptorConfiguration.class};
        SpringApplication.run(contextClasses, args);
    }

}