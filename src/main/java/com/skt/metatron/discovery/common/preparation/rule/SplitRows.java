package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by kyungtaak on 2017. 3. 5..
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
  String quote;

  public SplitRows() {
  }

  public SplitRows(String col, Expression on, String quote) {
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

  public String getQuote() {
    return quote;
  }

  public void setQuote(String quote) {
    this.quote = quote;
  }

  @Override
  public String toString() {
    return "SplitRows{" +
        "col='" + col + '\'' +
        ", on=" + on +
        ", quote='" + quote + '\'' +
        '}';
  }
}
