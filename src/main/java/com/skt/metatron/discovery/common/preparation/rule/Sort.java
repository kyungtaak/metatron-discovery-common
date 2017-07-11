package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by Seungunchoe on 2017. 7. 11
 */
public class Sort implements Rule, Rule.Factory {

  /**
   * 새로운 컬럼에 넣을 값 (표현식 가능)
   */
  Expression order;

  public Sort() {
  }

  public Sort(Expression order) {

    this.order = order;
  }

  public Expression getOrder() {
    return order;
  }

  public void setOrder(Expression order) {
    this.order = order;
  }

  @Override
  public String getName() {
    return "sort";
  }

  @Override
  public Rule get() {
    return new Sort();
  }

  @Override
  public String toString() {
    return "Sort{" +
        "order=" + order + '}';
  }
}
