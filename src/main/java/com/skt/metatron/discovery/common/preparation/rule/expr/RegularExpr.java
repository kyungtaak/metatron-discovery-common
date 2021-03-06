package com.skt.metatron.discovery.common.preparation.rule.expr;

import org.apache.commons.lang3.StringUtils;

import java.util.List;


/**
 * 정규 표현식인 경우
 */
public class RegularExpr implements Expr {

  private final String value;

  public RegularExpr(String value) {
    this.value = value.substring(1, value.length()).trim();
  }

  public String getValue() {
    return value;
  }

  public String getEscapedValue() {
    return StringUtils.substring(value, 1, value.length() - 1);
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public ExprEval eval(NumericBinding bindings) {
    return ExprEval.bestEffortOf(bindings.get(value));
  }

}
