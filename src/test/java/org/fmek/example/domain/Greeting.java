package org.fmek.example.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Greeting {

  @Id
  private long id;
  private String content;

  public Greeting() {

  }

  public Greeting(long id, String content) {
    this.id = id;
    this.content = content;
  }

  public long getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public String toString() {
    return "[" + id + "] " + content;
  }

}