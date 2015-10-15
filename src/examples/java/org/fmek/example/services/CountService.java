package org.fmek.example.services;

import javax.inject.Named;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by thma on 03.09.2015.
 */
@Named
public class CountService {

  private final AtomicLong counter = new AtomicLong();


  public long incrementAndGet() {
    return counter.incrementAndGet();
  }
}
