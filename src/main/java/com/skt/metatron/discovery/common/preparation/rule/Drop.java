package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by kyungtaak on 2017. 3. 3..
 */
public class Drop implements Rule, Rule.Factory {

  Expression col;

  public Drop() {
  }

  public Drop(Expression col) {
    this.col = col;
  }

  @Override
  public String getName() {
    return "drop";
  }

  public Expression getCol() {
    return col;
  }

  public void setCol(Expression col) {
    this.col = col;
  }

  @Override
  public Rule get() {
    return new Drop();
  }

  @Override
  public String toString() {
    return "Drop{" +
        "col=" + col +
        '}';
  }
}
