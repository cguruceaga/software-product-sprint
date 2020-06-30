package com.google.sps.data;

/** A comment item */
public final class Comment {

  private final long id;
  private final String comment;
  private final String firstName;
  private final String lastName;
  private final long timestamp;

  public Comment(long id, String comment, String firstName, String lastName, long timestamp) {
    this.id = id;
    this.comment = comment;
    this.firstName = firstName;
    this.lastName = lastName;
    this.timestamp = timestamp;
  }
}