package com.skt.metatron.discovery.common.preparation.rule;

/**
 * Created by seungunchoe on 2017. 3. 3..
 */
public class Rename implements Rule, Rule.Factory {
  String col;
  String to;

  public Rename() {
  }

  public Rename(String col, String to) {
    this.col = col;
    this.to = to;
  }

  @Override
  public String getName() {
    return "rename";
  }

  public String getCol() {
    return col;
  }

  public void setCol(String col) {
    this.col = col;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  @Override
  public Rule get() {
    return new Rename();
  }

  @Override
  public String toString() {
    return "Rename{" +
        "col='" + col + '\'' +
        ", to='" + to + '\'' +
        '}';
  }
}
