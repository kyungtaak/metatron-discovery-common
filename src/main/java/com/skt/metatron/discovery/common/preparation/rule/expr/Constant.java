package com.skt.metatron.discovery.common.preparation.rule.expr;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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

  class ArrayExpr<T> implements Constant {

    private final List<T> value;

    public ArrayExpr(List<T> value) {
      this.value = value;
    }

    @Override
    public List<T> getValue() {
      return value;
    }

    @Override
    public String toString() {
      return value == null ? "null" : value.toString();
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      return null;
    }
  }

  class StringExpr implements Constant {

    private final String value;

    public StringExpr(String value) {
      this.value = StringUtils.substring(value, 1, value.length() - 1);
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
      return ExprEval.of(value, ExprType.STRING);
    }
  }


  class StringArrayExpr implements Constant {

    private final List<String> value;

    public StringArrayExpr(List<String> value) {
      this.value = value.stream()
          .map(s -> StringUtils.substring(s, 1, s.length() - 1))
          .collect(Collectors.toList());
    }

    @Override
    public List<String> getValue() {
      return value;
    }

    @Override
    public String toString() {
      return value == null ? "null" : value.toString();
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      return null;
    }
  }
}
