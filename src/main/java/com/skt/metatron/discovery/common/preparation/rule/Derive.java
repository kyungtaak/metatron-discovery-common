package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by kyungtaak on 2017. 3. 13..
 */
public class Derive implements Rule, Rule.Factory {

  /**
   * 새로운 컬럼에 넣을 값 (표현식 가능)
   */
  Expression value;

  /**
   * (Optional) 새로 생성될 컬럼에 대한 이름
   */
  String as;

  public Derive() {
  }

  public Derive(Expression value, String as) {
    this.value = value;
    this.as = as;
  }

  public Expression getValue() {
    return value;
  }

  public void setValue(Expression value) {
    this.value = value;
  }

  public String getAs() {
    return as;
  }

  public void setAs(String as) {
    this.as = as;
  }

  @Override
  public String getName() {
    return "derive";
  }

  @Override
  public Rule get() {
    return new Derive();
  }

  @Override
  public String toString() {
    return "Derive{" +
        "value=" + value +
        ", as='" + as + '\'' +
        '}';
  }
}
