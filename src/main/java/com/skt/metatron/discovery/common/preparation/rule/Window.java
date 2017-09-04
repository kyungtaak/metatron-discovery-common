package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by seungunchoe on 2017. 3. 13..
 */
public class Window implements Rule, Rule.Factory {

  /**
   * Pivot 대상의 표현식 (Required)
   *
   */
  Expression value;

  /**
   * limit of column (Optional)
   */
  Expression order;

  Expression partition;

  Expression rowsBetween;


  public Window() {
  }

  public Window(Expression value, Expression order, Expression partition, Expression rowsBetween) {
    this.value = value;
    this.order = order;
    this.partition = partition;
    this.rowsBetween = rowsBetween;
  }

  public Expression getValue() {
    return value;
  }

  public void setValue(Expression value) {
    this.value = value;
  }

  public Expression getOrder() {
    return order;
  }

  public void setOrder(Expression order) {
    this.order = order;
  }

  public Expression getPartition() {
    return partition;
  }

  public void setPartition(Expression partition) {
    this.partition = partition;
  }

  public Expression getRowsBetween() {
    return rowsBetween;
  }

  public void setRowsBetween(Expression rowsBetween) {
    this.rowsBetween = rowsBetween;
  }

  @Override
  public String getName() {
    return "window";
  }

  @Override
  public Rule get() {
    return new Window();
  }

  @Override
  public String toString() {
    return "Window{" +
        "value=" + value +
        ", order=" + order +
        ", partition=" + partition +
        ", rowsBetween=" + rowsBetween +
        '}';
  }
}
