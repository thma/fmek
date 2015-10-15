# fmek

Emulating a JEE 7 container with SpringBoot.

> "The Fmeks are a diminutive sapient species native to the planet Fmoo, they are the sworn enemies of the Arquillians."  
-- [aliens.wikia]

## Use cases
-  **unit testing of JEE components**  
    Unit testing JEE components can be quite a hazzle if you want to test across different software layers (without using mocks) or if you want to test container provided features like JTA transactions including two phase commit synchronizing different XA resources.  

    Typical solutions use specialized Junit Testrunners like CDI-Unit CdiRunner or the DeltaSpike CdiTestRunner which provide a Weld CDI container for unit tests. This is great for testing CDI dependency injection. But integrating JAX-RS or JTA with this approach is far from trivial.  
    
    The classical fullblown approach is to use tools like Arquillian which provide the complete JEE infrastructure to your junit tests. The downside with Arquillian is that the assembly of the container can be quite complex and tends to slow down the execution of the junit tests.  
    
    The **FMEK** approach is simple: just provide all required JEE dependencies of your application by a SpringBoot Maven POM. The Junit test will be executed by the SpringJUnit4ClassRunner which sets up the Spring container serving all required components:

        @RunWith(SpringJUnit4ClassRunner.class)
        @ContextConfiguration(classes = {HelloWorldRestApplication.class, 
                                         EmulateJeeContainerConfiguration.class})
        public class GreetingTests {
        
          @Inject
          private CountService countService;
        
          @Inject
          private GreetingCrudService greetingCrudService;
        
          @Inject
          private GreetingPostingService postingService;
        
          private static final String template = "Hello, %s!";
        
          @Test
          @Transactional
          public void testGreetings() throws JMSException {
            createAndSaveAGreeting();
            createAndSaveAGreeting();
            createAndSaveAGreeting();
        
            Greeting g = greetingCrudService.getGreeting(1);
            assertNotNull(g);
            assertEquals(1, g.getId());
            assertEquals("Hello, user-1!", g.getContent());
        
            List<Greeting> allGreetings =  greetingCrudService.getAllGreetings();
            assertEquals(3, allGreetings.size());
          }
        
          private void createAndSaveAGreeting() throws JMSException {
            long id = countService.incrementAndGet();
            Greeting greeting = new Greeting(id, String.format(template, "user-" + id));
            greetingCrudService.store(greeting);
            postingService.sendGreeting(greeting);
          }
        
        }  
    
-  **providing a lightweight JEE 7 runtime container based on SpringBoot**  
    Of course a Spring container is not only useful in a testing environment but can also be used as a full-fledged deployment and runtime environment for production.

    All you need is a main class that is annotated as <code>@SpringBootApplication</code> that starts up the Spring container by calling <code>SpringApplication.run</code>:

        @SpringBootApplication
        public class HelloWorldRestApplication extends ResourceConfig {
        
            public HelloWorldRestApplication() {
              register(RequestContextFilter.class);
              register(HelloWorldResource.class);
            }
        
          public static void main(String[] args) {
            Object[] contextClasses = {HelloWorldRestApplication.class, EmulateJeeContainerConfiguration.class};
            SpringApplication.run(contextClasses, args);
          }
        }
    
    By calling <code>mvn install</code> this main class and all its dependencies are assembled to an executable jar. Thus no application deployment is needed.   
    
    You can even add extended support for application monitoring and managing by adding a dependency to Spring Boot Actuator in your POM File:
    
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

-  **developing Spring application with minimal code dependencies on Spring**  
    If you don't intend to deploy your application to a JEE container it still makes sense to minimize explicit Spring dependencies in your code. As an example have a look at the following service class which exclusively uses JEE standard APIs can be completely managed by Spring:

        package org.fmek.example.services;

        import org.fmek.example.domain.Greeting;
        import javax.inject.Inject;
        import javax.inject.Named;
        import javax.persistence.EntityManager;
        import javax.persistence.TypedQuery;
        import javax.transaction.Transactional;
        import java.util.List;
        
        @Named
        @Transactional
        public class GreetingCrudService {
        
          @Inject
          private EntityManager entityManager;
        
          public void store(Greeting g) {
            entityManager.merge(g);
          }
        
          public List<Greeting> getAllGreetings() {
            TypedQuery<Greeting> q = entityManager.createQuery("SELECT g FROM Greeting g", Greeting.class);
            return q.getResultList();
          }
        
          public Greeting getGreeting(long id) {
            return entityManager.find(Greeting.class, id);
          }
        }

## Supported Features:

- CDI
- JTA
- JPA
- JMS
- JAX-RS
- Servlets

[aliens.wikia]: http://aliens.wikia.com/wiki/Fmek
