package com.skt.metatron.discovery.common.preparation.rule;

/**
 * Created by Seungunchoe on 2017. 7. 11
 */
public class Move implements Rule, Rule.Factory {

  /**
   * 새로운 컬럼에 넣을 값 (표현식 가능)
   */
  String col;

  String after;

  String before;

  public Move() {
  }

  public Move(String col, String after, String before) {
    this.col = col;
    this.after = after;
    this.before = before;
  }

  public String getCol() {
    return col;
  }

  public void setCol(String col) {
    this.col = col;
  }

  public String getAfter() {
    return after;
  }

  public void setAfter(String after) {
    this.after = after;
  }

  public String getBefore() {
    return before;
  }

  public void setBefore(String before) {
    this.before = before;
  }


  @Override
  public String getName() {
    return "move";
  }

  @Override
  public Rule get() {
    return new Move();
  }

  @Override
  public String toString() {
    return "Move{" +
        "col=" + col +
        ", after=" + after +
        ", before=" + before +
    '}';
  }
}
