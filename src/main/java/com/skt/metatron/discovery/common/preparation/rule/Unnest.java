package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by seungunchoe on 2017. 3. 5..
 */
public class Unnest implements Rule, Rule.Factory {

  /**
   * Unest 할 대상 필드 (1개 이상)
   *
   */
  String col;

  /**
   * Nest 대상 타입 (map, array)
   *
   */
  String into;

  /**
   * Unnest 할 대상 index (1개 이상)
   *
   */
  Expression idx;

  public Unnest() {
  }

  public Unnest(String col, String into, Expression idx) {
    this.col = col;
    this.into = into;
    this.idx = idx;
  }

  public String getCol() {
    return col;
  }

  public void setCol(String col) {
    this.col = col;
  }

  public String getInto() {
    return into;
  }

  public void setInto(String into) {
    this.into = into;
  }

  public Expression getIdx() {
    return idx;
  }

  public void setIdx(Expression idx) {
    this.idx = idx;
  }

  @Override
  public String getName() {
    return "unnest";
  }

  @Override
  public Rule get() {
    return new Unnest();
  }

  @Override
  public String toString() {
    return "Unnest{" +
        "col='" + col + '\'' +
        ", into=" + into +
        ", idx=" + idx +
        '}';
  }
}
