package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;
import com.skt.metatron.discovery.common.preparation.rule.expr.Identifier;

/**
 * Created by kyungtaak on 2017. 3. 13..
 */
public class Pivot implements Rule, Rule.Factory {

  /**
   * 복수개의 Pivot 대상 컬럼 (Required)
   *
   */
  Expression col;

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

  /**
   * limit of column (Optional)
   */
  Integer limit;


  public Pivot() {
  }

  public Expression getCol() {
    return col;
  }

  public void setCol(Expression col) {
    this.col = col;
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

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  @Override
  public String getName() {
    return "pivot";
  }

  @Override
  public Rule get() {
    return new Pivot();
  }

  @Override
  public String toString() {
    return "Pivot{" +
        "col=" + col +
        ", value=" + value +
        ", group=" + group +
        ", limit=" + limit +
        '}';
  }
}
