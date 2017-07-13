package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by seungunchoe on 2017. 3. 5..
 */
public class Nest implements Rule, Rule.Factory {

  /**
   * Nest 할 대상 필드 (1개 이상)
   *
   */
  Expression col;

  /**
   * Nest 대상 타입 (map, array)
   *
   */
  String into;

  /**
   * (Optional) 새로 생성될 컬럼에 대한 이름
   */
  String as;


  public Nest() {
  }

  public Nest(Expression col, String into, String as) {
    this.col = col;
    this.into = into;
    this.as = as;
  }

  public Expression getCol() {
    return col;
  }

  public void setCol(Expression col) {
    this.col = col;
  }

  public String getInto() {
    return into;
  }

  public void setInto(String into) {
    this.into = into;
  }

  public String getAs() {
    return as;
  }

  public void setAs(String as) {
    this.as = as;
  }

  @Override
  public String getName() {
    return "nest";
  }

  @Override
  public Rule get() {
    return new Nest();
  }

  @Override
  public String toString() {
    return "Nest{" +
        "col=" + col +
        ", into='" + into + '\'' +
        ", as='" + as + '\'' +
        '}';
  }
}
