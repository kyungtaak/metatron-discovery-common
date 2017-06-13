package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by kyungtaak on 2017. 3. 13..
 */
public class Aggregate implements Rule, Rule.Factory {

  /**
   * Pivot 대상의 표현식 (Required)
   *
   */
  Expression value;

  /**
   * 복수개의 Group by 대상 컬럼
   *
   */
  Expression group;


  public Aggregate() {
  }

  public Expression getValue() {
    return value;
  }

  public void setValue(Expression value) {
    this.value = value;
  }

  public Expression getGroup() {
    return group;
  }

  public void setGroup(Expression group) {
    this.group = group;
  }

  @Override
  public String getName() {
    return "aggregate";
  }

  @Override
  public Rule get() {
    return new Aggregate();
  }

  @Override
  public String toString() {
    return "Aggregate{" +
        "value=" + value +
        ", group=" + group +
        '}';
  }
}
