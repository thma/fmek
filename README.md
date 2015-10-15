# fmek

Emulating a JEE 7 container with SpringBoot.

> "The Fmeks are a diminutive sapient species native to the planet Fmoo, they are the sworn enemies of the Arquillians."  
-- [aliens.wikia]

## Use cases
-  **unit testing of JEE components**  
    Unit testing JEE components can be quite a hazzle if you want to test across different software layers (without using mocks) or if you want to test container provided features like JTA transactions including two phase commit synchronizing different XA resources.
    Typical solutions use specialized Junit Testrunners like CDI-Unit CdiRunner or the DeltaSpike CdiTestRunner which provide a Weld CDI container for unit tests. This is great for testing CDI dependency injection. But integrating JAX-RS or JTA with this approach is far from trivial.
    The classical fullblown approach is to use tools like Arquillian which provide the complete JEE infrastructure to your junit tests. The downside with Arquillian is that the assembly of the container can be quite complex and tends to slow down the execution of the junit tests.
    The **FMEK** approach is simple: just provide all required JEE dependencies of your application by a SpringBoot Maven POM. The Junit test will be executed by the SpringJUnit4ClassRunner.

        @RunWith(SpringJUnit4ClassRunner.class)
        @ContextConfiguration(classes = {HelloWorldRestApplication.class, EmulateJeeContainerConfiguration.class})
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
-  **developing Spring application with minimal code dependencies on Spring**  

## Supported Features:

- CDI
- JTA
- JPA
- JMS
- JAX-RS
- Servlets

[aliens.wikia]: http://aliens.wikia.com/wiki/Fmek
