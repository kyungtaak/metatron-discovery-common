package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by seungunchoe on 2017. 3. 7..
 */
public class Set implements Rule, Rule.Factory {

  String col;
  Expression value;
  Expression row;

  public Set() {
  }

  public Set(String col, Expression value, Expression row) {
    this.col = col;
    this.value = value;
    this.row = row;
  }

  @Override
  public String getName() {
    return "set";
  }

  @Override
  public Rule get() {
    return new Set();
  }

  public String getCol() {
    return col;
  }

  public void setCol(String col) {
    this.col = col;
  }

  public Expression getValue() {
    return value;
  }

  public void setValue(Expression value) {
    this.value = value;
  }

  public Expression getRow() {
    return row;
  }

  public void setRow(Expression row) {
    this.row = row;
  }

  @Override
  public String toString() {
    return "Set{" +
        "col='" + col + '\'' +
        ", value=" + value +
        ", row=" + row +
        '}';
  }
}
