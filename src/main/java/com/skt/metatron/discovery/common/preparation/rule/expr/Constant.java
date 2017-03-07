package com.skt.metatron.discovery.common.preparation.rule.expr;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public interface Constant extends Expr {

  Object getValue();

  class LongExpr implements Constant {
    private final long value;

    public LongExpr(long value) {
      this.value = value;
    }

    public Object getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      return ExprEval.of(value, ExprType.LONG);
    }
  }

  class StringExpr implements Constant {

    private final String value;

    public StringExpr(String value) {
      this.value = value;
    }

    public Object getValue() {
      return StringUtils.substring(value, 1, value.length() - 1);
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      return ExprEval.of(value, ExprType.STRING);
    }
  }

  class DoubleExpr implements Constant {

    private final double value;

    public DoubleExpr(double value) {
      this.value = value;
    }

    public Object getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      return ExprEval.of(value, ExprType.DOUBLE);
    }
  }
}
