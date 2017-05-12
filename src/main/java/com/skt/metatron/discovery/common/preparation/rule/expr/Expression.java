package com.skt.metatron.discovery.common.preparation.rule.expr;

import java.util.List;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public interface Expression {

  Expr getLeft();
  Expr getRight();
  String getOp();

  interface BooleanExpression extends Expression {
    <T extends Expression> List<T> getChildren();
  }

  interface NotExpression extends Expression {
    <T extends Expression> T getChild();
  }

  interface AndExpression extends BooleanExpression {
  }

  interface OrExpression extends BooleanExpression {
  }

  interface Factory<T extends Expression> {
    T or(List<T> children);
    T and(List<T> children);
    T not(T expression);
  }
}
