package com.skt.metatron.discovery.common.preparation.rule.expr;

import com.google.common.base.Supplier;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expr.NumericBinding;

import java.util.List;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public interface Function {

  String name();

  ExprEval apply(List<Expr> args, NumericBinding bindings);

  interface Factory extends Supplier<Function> {
  }

  interface Library {
  }
}
