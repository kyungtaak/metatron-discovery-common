package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by seungunchoe on 2017. 3. 5..
 */
public class SplitRows implements Rule, Rule.Factory {

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
   * Ignore matches between
   */
  Expression quote;

  public SplitRows() {
  }

  public SplitRows(String col, Expression on, Expression quote) {
    this.col = col;
    this.on = on;
    this.quote = quote;
  }

  @Override
  public String getName() {
    return "split";
  }

  @Override
  public Rule get() {
    return new SplitRows();
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

  public Expression getQuote() {
    return quote;
  }

  public void setQuote(Expression quote) {
    this.quote = quote;
  }

  @Override
  public String toString() {
    String quoteString = null;
    if (quote != null) quoteString = quote.toString(); else quoteString = "";
    return "SplitRows{" +
        "col='" + col + '\'' +
        ", on=" + on +
        ", quote='" + quoteString + '\'' +
        '}';
  }
}
