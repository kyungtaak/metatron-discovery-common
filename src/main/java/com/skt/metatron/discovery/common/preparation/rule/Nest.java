package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Identifier;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public class Nest implements Rule, Rule.Factory {

  /**
   * Nest 할 대상 필드 (1개 이상)
   *
   */
  Identifier.IdentifierArrayExpr col;

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

  public Nest(Identifier.IdentifierArrayExpr col, String into, String as) {
    this.col = col;
    this.into = into;
    this.as = as;
  }

  public Identifier.IdentifierArrayExpr getCol() {
    return col;
  }

  public void setCol(Identifier.IdentifierArrayExpr col) {
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
