package com.skt.metatron.discovery.common.preparation.rule.expr;

import com.google.common.math.LongMath;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public interface Expr extends Expression {

  ExprEval eval(NumericBinding bindings);

  interface NumericBinding {
    Object get(String name);
  }

  class FunctionArrayExpr implements Expr {
    final List<FunctionExpr> functions;

    public FunctionArrayExpr(List<FunctionExpr> functions) {
      this.functions = functions;
    }

    public List<FunctionExpr> getFunctions() {
      return functions;
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      return null;
    }

    @Override
    public String toString() {
      return "FunctionArrayExpr{" +
          "functions=" + functions +
          '}';
    }
  }

  class AssignExpr implements Expr {
    final Expr assignee;
    final Expr assigned;

    public AssignExpr(Expr assignee, Expr assigned) {
      this.assignee = assignee;
      this.assigned = assigned;
    }

    public Expr getAssignee() {
      return assignee;
    }

    public Expr getAssigned() {
      return assigned;
    }

    @Override
    public String toString() {
      return "(" + assignee + " = " + assigned + ")";
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      throw new IllegalStateException("cannot evaluated directly");
    }
  }

  class FunctionExpr implements Expr {
    final Function function;
    final String name;
    final List<Expr> args;

    public Expr getLeft() {
      return null;
    }
    public Expr getRight() {
      return null;
    }
    public String getOp() {
      return null;
    }

    public FunctionExpr(Function function, String name, List<Expr> args) {
      this.function = function;
      this.name = name;
      this.args = args;
    }

    public String getName() {
      return name;
    }

    public List<Expr> getArgs() {
      return args;
    }

    @Override
    public String toString() {
      List<String> argsExprs = args.stream()
          .map(expr -> expr.toString())
          .collect(Collectors.toList());

      return name + "(" + StringUtils.join(argsExprs, ",") + ")";
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      return null;
    }
  }

  class UnaryMinusExpr implements Expr {
    final Expr expr;

    public UnaryMinusExpr(Expr expr) {
//      if (expr.toString().contains("."))
//        this.expr = new Constant.DoubleExpr(Double.parseDouble("-" + expr.toString()));
//      else
//        this.expr = new Constant.LongExpr(Long.parseLong("-" + expr.toString()));
      this.expr = expr;
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      ExprEval ret = expr.eval(bindings);
      if (ret.type() == ExprType.LONG) {
        return ExprEval.of(-ret.longValue());
      }
      if (ret.type() == ExprType.DOUBLE) {
        return ExprEval.of(-ret.doubleValue());
      }
      throw new IllegalArgumentException("unsupported type " + ret.type());
    }

    @Override
    public String toString() {
      return "-" + expr.toString();
    }
  }

  class UnaryNotExpr implements Expr, Expression.NotExpression {
    final Expr expr;

    public UnaryNotExpr(Expr expr) {
      this.expr = expr;
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      ExprEval ret = expr.eval(bindings);
      if (ret.type() == ExprType.LONG) {
        return ExprEval.of(ret.asBoolean() ? 0L : 1L);
      }
      if (ret.type() == ExprType.DOUBLE) {
        return ExprEval.of(ret.asBoolean() ? 0.0d : 1.0d);
      }
      throw new IllegalArgumentException("unsupported type " + ret.type());
    }

    @Override
    public String toString() {
      return "!" + expr.toString();
    }

    @Override
    public Expr getChild() {
      return expr;
    }
  }

  abstract class BinaryOpExprBase implements Expr {
    protected String op;
    protected Expr left;
    protected Expr right;

    public BinaryOpExprBase(String op, Expr left, Expr right) {
      this.op = op;
      this.left = left;
      this.right = right;
    }

    public String getOp() {
      return op;
    }

    public Expr getLeft() {
      return left;
    }

    public Expr getRight() {
      return right;
    }

    @Override
    public String toString() {
      return "(" + left + " " + op + " " + right + ")";
    }
  }

  abstract class BinaryNumericOpExprBase extends BinaryOpExprBase {

    public BinaryNumericOpExprBase(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      ExprEval leftVal = left.eval(bindings);
      ExprEval rightVal = right.eval(bindings);
      if (leftVal.type() == ExprType.STRING || rightVal.type() == ExprType.STRING) {
        return evalString(StringUtils.defaultString(leftVal.asString()), StringUtils.defaultString(rightVal.asString()));
      }
      if (leftVal.value() == null || rightVal.value() == null) {
        throw new IllegalArgumentException("null value");
      }
      if (leftVal.type() == ExprType.LONG && rightVal.type() == ExprType.LONG) {
        return ExprEval.of(evalLong(leftVal.longValue(), rightVal.longValue()));
      }
      return ExprEval.of(evalDouble(leftVal.doubleValue(), rightVal.doubleValue()));
    }

    protected ExprEval evalString(String left, String right) {
      throw new IllegalArgumentException("unsupported type " + ExprType.STRING);
    }

    protected abstract long evalLong(long left, long right);

    protected abstract double evalDouble(double left, double right);

    @Override
    public String toString() {
      return "(" + left + " " + op + " " + right + ")";
    }
  }

  class BinMinusExpr extends BinaryNumericOpExprBase {

    public BinMinusExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return left - right;
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return left - right;
    }

  }

  class BinPowExpr extends BinaryNumericOpExprBase {

    public BinPowExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return LongMath.pow(left, (int) right);
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return Math.pow(left, right);
    }
  }

  class BinMulExpr extends BinaryNumericOpExprBase {

    public BinMulExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return left * right;
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return left * right;
    }
  }

  class BinDivExpr extends BinaryNumericOpExprBase {

    public BinDivExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return left / right;
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return left / right;
    }
  }

  class BinModuloExpr extends BinaryNumericOpExprBase {

    public BinModuloExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return left % right;
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return left % right;
    }
  }

  class BinPlusExpr extends BinaryNumericOpExprBase {

    public BinPlusExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected ExprEval evalString(String left, String right) {
      return ExprEval.of(left + right);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return left + right;
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return left + right;
    }
  }

  class BinAsExpr extends BinaryNumericOpExprBase {

    public BinAsExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected ExprEval evalString(String left, String right) {
      return ExprEval.of(left.compareTo(right) < 0 ? 1L : 0L);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return left < right ? 1L : 0L;
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return left < right ? 1.0d : 0.0d;
    }
  }

  class BinLtExpr extends BinaryNumericOpExprBase {

    public BinLtExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected ExprEval evalString(String left, String right) {
      return ExprEval.of(left.compareTo(right) < 0 ? 1L : 0L);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return left < right ? 1L : 0L;
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return left < right ? 1.0d : 0.0d;
    }
  }

  class BinLeqExpr extends BinaryNumericOpExprBase {

    public BinLeqExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected ExprEval evalString(String left, String right) {
      return ExprEval.of(left.compareTo(right) <= 0 ? 1L : 0L);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return left <= right ? 1L : 0L;
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return left <= right ? 1.0d : 0.0d;
    }
  }

  class BinGtExpr extends BinaryNumericOpExprBase {

    public BinGtExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected ExprEval evalString(String left, String right) {
      return ExprEval.of(left.compareTo(right) > 0 ? 1L : 0L);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return left > right ? 1L : 0L;
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return left > right ? 1.0d : 0.0d;
    }
  }

  class BinGeqExpr extends BinaryNumericOpExprBase {

    public BinGeqExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected ExprEval evalString(String left, String right) {
      return ExprEval.of(left.compareTo(right) >= 0 ? 1L : 0L);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return left >= right ? 1L : 0L;
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return left >= right ? 1.0d : 0.0d;
    }
  }

  class BinEqExpr extends BinaryNumericOpExprBase {

    public BinEqExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected ExprEval evalString(String left, String right) {
      return ExprEval.of(left.equals(right) ? 1L : 0L);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return left == right ? 1L : 0L;
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return left == right ? 1.0d : 0.0d;
    }
  }

  class BinNeqExpr extends BinaryNumericOpExprBase {

    public BinNeqExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected ExprEval evalString(String left, String right) {
      return ExprEval.of(!Objects.equals(left, right) ? 1L : 0L);
    }

    @Override
    protected final long evalLong(long left, long right) {
      return left != right ? 1L : 0L;
    }

    @Override
    protected final double evalDouble(double left, double right) {
      return left != right ? 1.0d : 0.0d;
    }
  }

  class BinAndExpr extends BinaryNumericOpExprBase implements Expression.AndExpression {
    public BinAndExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      ExprEval leftVal = left.eval(bindings);
      return leftVal.asBoolean() ? right.eval(bindings) : leftVal;
    }

    @Override
    public List<Expr> getChildren() {
      return Arrays.asList(left, right);
    }

    protected long evalLong(long left, long right) {
      return left > 0 & right > 0 ? 1L : 0L;
    }

    @Override
    protected double evalDouble(double left, double right) {
      return left > 0 & right > 0 ? 1.0d : 0.0d;
    }
  }

  class BinOrExpr extends BinaryNumericOpExprBase implements Expression.OrExpression {
    public BinOrExpr(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    public ExprEval eval(NumericBinding bindings) {
      ExprEval leftVal = left.eval(bindings);
      return leftVal.asBoolean() ? leftVal : right.eval(bindings);
    }

    @Override
    public List<Expr> getChildren() {
      return Arrays.asList(left, right);
    }

    protected long evalLong(long left, long right) {
      return left > 0 | right > 0 ? 1L : 0L;
    }

    @Override
    protected double evalDouble(double left, double right) {
      return left > 0 | right > 0 ? 1.0d : 0.0d;
    }
  }

  class BinAndExpr2 extends BinaryNumericOpExprBase implements Expression.AndExpression {
    public BinAndExpr2(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    protected long evalLong(long left, long right) {
      return left > 0 && right > 0 ? 1L : 0L;
    }

    @Override
    protected double evalDouble(double left, double right) {
      return left > 0 && right > 0 ? 1.0d : 0.0d;
    }

    @Override
    public List<Expr> getChildren() {
      return Arrays.asList(left, right);
    }
  }

  class BinOrExpr2 extends BinaryNumericOpExprBase implements Expression.OrExpression {
    public BinOrExpr2(String op, Expr left, Expr right) {
      super(op, left, right);
    }

    @Override
    protected long evalLong(long left, long right) {
      return left > 0 || right > 0 ? 1L : 0L;
    }

    @Override
    protected double evalDouble(double left, double right) {
      return left > 0 || right > 0 ? 1.0d : 0.0d;
    }

    @Override
    public List<Expr> getChildren() {
      return Arrays.asList(left, right);
    }
  }
}


