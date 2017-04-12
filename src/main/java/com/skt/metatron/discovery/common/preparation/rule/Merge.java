package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Constant;
import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;
import com.skt.metatron.discovery.common.preparation.rule.expr.Identifier;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public class Merge implements Rule, Rule.Factory {

  /**
   * Merge 할 대상 필드 (1개 이상)
   *
   */
  Identifier.IdentifierArrayExpr col;

  /**
   * 대치하려는 값 (String)
   *
   */
  String with;

  /**
   * (Optional) 새로 생성될 컬럼에 대한 이름
   */
  String as;


  public Merge() {
  }

  public Merge(Identifier.IdentifierArrayExpr col, String with, String as) {
    this.col = col;
    this.with = with;
    this.as = as;
  }

  public Identifier.IdentifierArrayExpr getCol() {
    return col;
  }

  public void setCol(Identifier.IdentifierArrayExpr col) {
    this.col = col;
  }

  public String getWith() {
    return with;
  }

  public void setWith(String with) {
    this.with = with;
  }

  public String getAs() {
    return as;
  }

  public void setAs(String as) {
    this.as = as;
  }

  @Override
  public String getName() {
    return "merge";
  }

  @Override
  public Rule get() {
    return new Merge();
  }

  @Override
  public String toString() {
    return "Merge{" +
        "col=" + col +
        ", with=" + with +
        ", as='" + as + '\'' +
        '}';
  }
}
