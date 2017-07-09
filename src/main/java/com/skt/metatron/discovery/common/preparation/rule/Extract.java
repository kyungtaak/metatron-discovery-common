package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public class Extract implements Rule, Rule.Factory {

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
  String quote;

  /**
   * if true, ignore case.
   *
   */
  Boolean ignoreCase;


  public Extract() {
    limit = 0;
  }

  public Extract(String col, Expression on, Integer limit, String quote, Boolean ignoreCase) {
    this.col = col;
    this.on = on;
    if (limit == null)
      limit = 0;
    this.limit = limit;
    this.quote = quote;
    this.ignoreCase = ignoreCase;
  }

  @Override
  public String getName() {
    return "extract";
  }

  @Override
  public Rule get() {
    return new Extract();
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

  public String getQuote() {
    return quote;
  }

  public void setQuote(String quote) {
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
    return "Extract{" +
        "col='" + col + '\'' +
        ", on=" + on +
        ", limit=" + limit +
        ", quote='" + quote + '\'' +
        ", ignoreCase=" + ignoreCase +
        '}';
  }
}
