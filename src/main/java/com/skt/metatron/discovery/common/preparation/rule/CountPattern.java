package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public class CountPattern implements Rule, Rule.Factory {

  /**
   * 단일 컬럼
   *
   */
  String col;

  /**
   * 패턴(RegularExpr)/문자열(StringExpr)
   *
   */
  Expression on;

  /**
   * 패턴/문자열에 일치한 위치 이후
   *
   */
  Expression after;

  /**
   * 패턴/문자열에 일치한 위치 이전
   *
   */
  Expression before;

  /**
   * if true, ignore case.
   *
   */
  Boolean ignoreCase;


  public CountPattern() {
  }

  @Override
  public String getName() {
    return "countpattern";
  }

  @Override
  public Rule get() {
    return new CountPattern();
  }

  public String getCol() {
    return col;
  }

  public void setCol(String col) {
    this.col = col;
  }

  public Expression getOn() {
    return on;
  }

  public void setOn(Expression on) {
    this.on = on;
  }

  public Expression getAfter() {
    return after;
  }

  public void setAfter(Expression after) {
    this.after = after;
  }

  public Expression getBefore() {
    return before;
  }

  public void setBefore(Expression before) {
    this.before = before;
  }

  public Boolean getIgnoreCase() {
    return ignoreCase;
  }

  public void setIgnoreCase(Boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
  }

  @Override
  public String toString() {
    return "CountPattern{" +
        "col='" + col + '\'' +
        ", on=" + on +
        ", after=" + after +
        ", before=" + before +
        ", ignoreCase=" + ignoreCase +
        '}';
  }
}
