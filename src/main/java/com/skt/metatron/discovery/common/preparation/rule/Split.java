package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by seungunchoe on 2017. 3. 5..
 */
public class Split implements Rule, Rule.Factory {

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
   * Number of times
   */
  Integer limit;

  /**
   * Ignore matches between
   */
  Expression quote;

  /**
   * if true, ignore case.
   *
   */
  Boolean ignoreCase;


  public Split() {
  }

  public Split(String col, Expression on, Integer limit, Expression quote, Boolean ignoreCase) {
    this.col = col;
    this.on = on;
    this.limit = limit;
    this.quote = quote;
    this.ignoreCase = ignoreCase;
  }

  @Override
  public String getName() {
    return "split";
  }

  @Override
  public Rule get() {
    return new Split();
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

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Expression getQuote() {
    return quote;
  }

  public void setQuote(Expression quote) {
    this.quote = quote;
  }

  public Boolean getIgnoreCase() {
    return ignoreCase;
  }

  public void setIgnoreCase(Boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
  }

  @Override
  public String toString() {
    String quoteString = null;
    if (quote != null) quoteString = quote.toString(); else quoteString = "";
    return "Split{" +
        "col='" + col + '\'' +
        ", on=" + on +
        ", limit=" + limit +
        ", quote='" + quoteString + '\'' +
        ", ignoreCase=" + ignoreCase +
        '}';
  }
}
