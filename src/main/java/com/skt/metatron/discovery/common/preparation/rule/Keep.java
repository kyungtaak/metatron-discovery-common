package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public class Keep implements Rule, Rule.Factory {

  Expression row;

  public Keep() {
  }

  public Keep(Expression row) {
    this.row = row;
  }

  @Override
  public String getName() {
    return "keep";
  }

  public Expression getRow() {
    return row;
  }

  public void setRow(Expression row) {
    this.row = row;
  }

  @Override
  public Rule get() {
    return new Keep();
  }

  @Override
  public String toString() {
    return "Keep{" +
        "row=" + row +
        '}';
  }
}
