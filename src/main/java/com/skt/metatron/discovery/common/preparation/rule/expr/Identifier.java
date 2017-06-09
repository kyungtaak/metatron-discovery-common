package com.skt.metatron.discovery.common.preparation.rule.expr;

import java.util.List;


/**
 * Created by kyungtaak on 2017. 3. 8..
 */
public interface Identifier extends Constant {
  class IdentifierExpr implements Identifier {

    private final String value;

    public IdentifierExpr(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
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

  class IdentifierArrayExpr implements Identifier {

    private final List<String> value;

    public IdentifierArrayExpr(List<String> value) {
      this.value = value;
    }

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
