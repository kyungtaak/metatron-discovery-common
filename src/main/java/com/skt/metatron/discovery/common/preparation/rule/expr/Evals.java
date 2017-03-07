package com.skt.metatron.discovery.common.preparation.rule.expr;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Objects;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public class Evals {

  static final DateTimeFormatter defaultFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

  static boolean eq(ExprEval leftVal, ExprEval rightVal)
  {
    if (isSameType(leftVal, rightVal)) {
      return Objects.equals(leftVal.value(), rightVal.value());
    }
    if (isAllNumeric(leftVal, rightVal)) {
      return leftVal.doubleValue() == rightVal.doubleValue();
    }
    return false;
  }

  private static boolean isSameType(ExprEval leftVal, ExprEval rightVal)
  {
    return leftVal.type() == rightVal.type();
  }

  static boolean isAllNumeric(ExprEval left, ExprEval right)
  {
    return left.isNumeric() && right.isNumeric();
  }

  static boolean isAllString(ExprEval left, ExprEval right)
  {
    return left.type() == ExprType.STRING && right.type() == ExprType.STRING;
  }

  static void assertNumeric(ExprType type)
  {
    if (type != ExprType.LONG && type != ExprType.DOUBLE) {
      throw new IllegalArgumentException("unsupported type " + type);
    }
  }

  static String evalOptionalString(Expr arg, Expr.NumericBinding binding)
  {
    return arg == null ? null : arg.eval(binding).asString();
  }

  static String getConstantString(Expr arg)
  {
    if (!(arg instanceof Constant.StringExpr)) {
      throw new RuntimeException(arg + " is not constant string");
    }
    return arg.eval(null).stringValue();
  }

  static String getIdentifier(Expr arg)
  {
    if (!(arg instanceof Expr.IdentifierExpr)) {
      throw new RuntimeException(arg + " is not identifier");
    }
    return arg.toString();
  }

  static long getConstantLong(Expr arg)
  {
    Object constant = getConstant(arg);
    if (!(constant instanceof Long)) {
      throw new RuntimeException(arg + " is not a constant long");
    }
    return (Long) constant;
  }

  static Number getConstantNumber(Expr arg)
  {
    Object constant = getConstant(arg);
    if (!(constant instanceof Number)) {
      throw new RuntimeException(arg + " is not a constant number");
    }
    return (Number) constant;
  }

  static Object getConstant(Expr arg)
  {
    if (arg instanceof Constant.StringExpr) {
      return arg.eval(null).stringValue();
    } else if (arg instanceof Constant.LongExpr) {
      return arg.eval(null).longValue();
    } else if (arg instanceof Constant.DoubleExpr) {
      return arg.eval(null).doubleValue();
    } else if (arg instanceof Expr.UnaryMinusExpr) {
      Expr.UnaryMinusExpr minusExpr = (Expr.UnaryMinusExpr)arg;
      if (minusExpr.expr instanceof Constant.LongExpr) {
        return -minusExpr.expr.eval(null).longValue();
      } else if (minusExpr.expr instanceof Constant.DoubleExpr) {
        return -minusExpr.expr.eval(null).doubleValue();
      }
    }
    throw new RuntimeException(arg + " is not a constant");
  }

  static boolean isConstant(Expr arg)
  {
    if (arg instanceof Constant) {
      return true;
    } else if (arg instanceof Expr.UnaryMinusExpr) {
      return ((Expr.UnaryMinusExpr)arg).expr instanceof Constant;
    }
    return false;
  }

  static Object[] getConstants(List<Expr> args)
  {
    Object[] constants = new Object[args.size()];
    for (int i = 0; i < constants.length; i++) {
      constants[i] = getConstant(args.get(i));
    }
    return constants;
  }

  static ExprEval castTo(ExprEval eval, ExprType castTo)
  {
    if (eval.type() == castTo) {
      return eval;
    }
    switch (castTo) {
      case DOUBLE:
        return ExprEval.of(eval.asDouble());
      case LONG:
        return ExprEval.of(eval.asLong());
      case STRING:
        return ExprEval.of(eval.asString());
    }
    throw new IllegalArgumentException("not supported type " + castTo);
  }

//  public static Object castTo(ExprEval eval, ValueType castTo)
//  {
//    switch (castTo) {
//      case FLOAT:
//        return eval.asFloat();
//      case DOUBLE:
//        return eval.asDouble();
//      case LONG:
//        return eval.asLong();
//      case STRING:
//        return eval.asString();
//      default:
//        throw new IllegalArgumentException("not supported type " + castTo);
//    }
//  }
//
//  public static com.google.common.base.Function<Comparable, Number> asNumberFunc(ValueType type)
//  {
//    switch (type) {
//      case FLOAT:
//        return new com.google.common.base.Function<Comparable, Number>()
//        {
//          @Override
//          public Number apply(Comparable input)
//          {
//            return input == null ? 0F : (Float) input;
//          }
//        };
//      case DOUBLE:
//        return new com.google.common.base.Function<Comparable, Number>()
//        {
//          @Override
//          public Number apply(Comparable input)
//          {
//            return input == null ? 0D : (Double) input;
//          }
//        };
//      case LONG:
//        return new com.google.common.base.Function<Comparable, Number>()
//        {
//          @Override
//          public Number apply(Comparable input)
//          {
//            return input == null ? 0L : (Long) input;
//          }
//        };
//      case STRING:
//        return new com.google.common.base.Function<Comparable, Number>()
//        {
//          @Override
//          public Number apply(Comparable input)
//          {
//            String string = (String) input;
//            return Strings.isNullOrEmpty(string)
//                ? 0L
//                : StringUtils.isNumeric(string) ? Long.valueOf(string) : Double.valueOf(string);
//          }
//        };
//    }
//    throw new UnsupportedOperationException("Unsupported type " + type);
//  }
//
  static DateTime toDateTime(ExprEval arg)
  {
    switch (arg.type()) {
      case STRING:
        String string = arg.stringValue();
        return StringUtils.isNumeric(string) ? new DateTime(Long.valueOf(string)) : defaultFormat.parseDateTime(string);
      default:
        return new DateTime(arg.longValue());
    }
  }
}
