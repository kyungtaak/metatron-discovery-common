package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Identifier;

/**
 * Created by kyungtaak on 2017. 3. 3..
 */
public class Unpivot implements Rule, Rule.Factory {

  /**
   * 복수개의 Unpivot 대상 컬럼 (Required)
   *
   */
  Identifier.IdentifierArrayExpr col;

  /**
   * unpivot 시 개별 칼럼 구성 숫자 (col 개수보다 작거나 같아야함)
   */
  Integer groupEvery;

  public Unpivot() {
  }

  public Unpivot(Identifier.IdentifierArrayExpr col, Integer groupEvery) {
    this.col = col;
    this.groupEvery = groupEvery;
  }

  public Identifier.IdentifierArrayExpr getCol() {
    return col;
  }

  public void setCol(Identifier.IdentifierArrayExpr col) {
    this.col = col;
  }

  public Integer getGroupEvery() {
    return groupEvery;
  }

  public void setGroupEvery(Integer groupEvery) {
    this.groupEvery = groupEvery;
  }

  @Override
  public String getName() {
    return "unpivot";
  }

  @Override
  public Rule get() {
    return new Unpivot();
  }

  @Override
  public String toString() {
    return "Unpivot{" +
        "col=" + col +
        ", groupEvery=" + groupEvery +
        '}';
  }
}
