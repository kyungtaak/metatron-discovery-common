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
  Identifier.IdentifierArrayExpr col;

  /**
   * Pivot 대상의 표현식 (Required)
   *
   */
  Expression value;

  /**
   * 복수개의 Group by 대상 컬럼
   *
   */
  Identifier.IdentifierArrayExpr group;

  /**
   * limit of column (Optional)
   */
  Integer limit;


  public Pivot() {
  }

  public Identifier.IdentifierArrayExpr getCol() {
    return col;
  }

  public void setCol(Identifier.IdentifierArrayExpr col) {
    this.col = col;
  }

  public Expression getValue() {
    return value;
  }

  public void setValue(Expression value) {
    this.value = value;
  }

  public Identifier.IdentifierArrayExpr getGroup() {
    return group;
  }

  public void setGroup(Identifier.IdentifierArrayExpr group) {
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
