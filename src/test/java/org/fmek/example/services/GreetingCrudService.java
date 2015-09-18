package org.fmek.example.services;

import org.apache.commons.logging.Log;
import org.fmek.example.domain.Greeting;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by thma on 04.09.2015.
 */
@Named
@Transactional
public class GreetingCrudService {

  @Inject
  Log log;

  @Inject
  private EntityManager entityManager;

  public void store(Greeting g) {
    log.info("store(" + g + ")");
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
