package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public class Delete implements Rule, Rule.Factory {

  /**
   * Condition
   */
  Expression row;

  public Delete() {
  }

  public Delete(Expression row) {
    this.row = row;
  }

  @Override
  public String getName() {
    return "delete";
  }

  public Expression getRow() {
    return row;
  }

  public void setRow(Expression row) {
    this.row = row;
  }

  @Override
  public Rule get() {
    return new Delete();
  }

  @Override
  public String toString() {
    return "Delete{" +
        "row=" + row +
        '}';
  }
}
