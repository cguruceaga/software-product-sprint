package com.google.sps.data;

/** An item on a todo list. */
public final class Comment {

  private final long id;
  private final String comment;
  private final String firstname;
  private final String lastname;
  private final long timestamp;

  public Comment(long id, String comment, String firstname, String lastname, long timestamp) {
    this.id = id;
    this.comment = comment;
    this.firstname = firstname;
    this.lastname = lastname;
    this.timestamp = timestamp;
  }
}