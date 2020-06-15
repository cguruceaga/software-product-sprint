package com.google.sps.data;

/** An item on a todo list. */
public final class Comment {

  private final long id;
  private final String comment;
  private final long timestamp;
  private final String fname;
  private final String lname;

  public Comment(long id, String comment, String fname, String lname, long timestamp) {
    this.id = id;
    this.comment = comment;
    this.fname = fname;
    this.lname = lname;
    this.timestamp = timestamp;
  }
}