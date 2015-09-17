package org.fmek;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Allows to obtain Entity Manager via @Inject annotation.
 */
@Configuration
public class EntityManagerProducer {

  @PersistenceContext
  private EntityManager entityManager;

//  @PersistenceUnit
//  EntityManagerFactory emfH2;
//
//  EntityManager emH2 = null;


  @Bean
  public EntityManager createEntityManger() {
//    if (emH2 == null) {
//      emH2 = emfH2.createEntityManager();
//    }
    return entityManager;
  }
}
