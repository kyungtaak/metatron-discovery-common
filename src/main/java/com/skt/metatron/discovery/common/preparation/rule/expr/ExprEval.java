package com.skt.metatron.discovery.common.preparation.rule.expr;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public class ExprEval extends Pair<Object, ExprType>
{
  public static ExprEval bestEffortOf(Object val) {
    if (val instanceof Number) {
      if (val instanceof Byte || val instanceof Short || val instanceof Integer || val instanceof Long) {
        return ExprEval.of(val, ExprType.LONG);
      }
      if (val instanceof Float || val instanceof Double) {
        return ExprEval.of(val, ExprType.DOUBLE);
      }
    }
    return ExprEval.of(val == null ? null : String.valueOf(val), ExprType.STRING);
  }

  public static ExprEval bestEffortOf(Object val, ExprType type) {
    if (val instanceof Number) {
      if (val instanceof Byte || val instanceof Short || val instanceof Integer || val instanceof Long) {
        return ExprEval.of(val, ExprType.LONG);
      }
      if (val instanceof Float || val instanceof Double) {
        return ExprEval.of(val, ExprType.DOUBLE);
      }
    }
    return new ExprEval(val, type != null ? type : ExprType.STRING);
  }

  public static ExprEval of(Object value, ExprType type)
  {
    return new ExprEval(value, type);
  }

  public static ExprEval of(long longValue)
  {
    return of(longValue, ExprType.LONG);
  }

  public static ExprEval of(double longValue) {
    return of(longValue, ExprType.DOUBLE);
  }

  public static ExprEval of(String stringValue)
  {
    return of(stringValue, ExprType.STRING);
  }

  public static ExprEval of(boolean bool)
  {
    return of(bool ? 1L : 0L);
  }

  public ExprEval(Object lhs, ExprType rhs)
  {
    super(lhs, rhs);
  }

  public Object value()
  {
    return lhs;
  }

  public ExprType type()
  {
    return rhs;
  }

  public boolean isNull()
  {
    return lhs == null || rhs == ExprType.STRING && stringValue().isEmpty();
  }

  public boolean isNumeric()
  {
    return rhs == ExprType.LONG || rhs == ExprType.DOUBLE;
  }

  public int intValue()
  {
    return lhs == null ? 0 : ((Number) lhs).intValue();
  }

  public long longValue()
  {
    return lhs == null ? 0L : ((Number) lhs).longValue();
  }

  public float floatValue()
  {
    return lhs == null ? 0F : ((Number) lhs).floatValue();
  }

  public double doubleValue()
  {
    return lhs == null ? 0D : ((Number) lhs).doubleValue();
  }

  public Number numberValue()
  {
    return (Number) lhs;
  }

  public String stringValue()
  {
    return (String) lhs;
  }

  public String asString()
  {
    return lhs == null || rhs == ExprType.STRING ? stringValue() : String.valueOf(lhs);
  }

  public boolean asBoolean() {
    switch (rhs) {
      case DOUBLE:
        return doubleValue() > 0;
      case LONG:
        return longValue() > 0;
      case STRING:
        return Boolean.valueOf(stringValue());
    }
    return false;
  }

  public float asFloat() {
    return isNull() ? 0F : lhs instanceof Number ? ((Number) lhs).floatValue() : Float.valueOf(asString());
  }

  public double asDouble() {
    return isNull() ? 0D : lhs instanceof Number ? ((Number) lhs).doubleValue() : Double.valueOf(asString());
  }

  public long asLong() {
    return isNull() ? 0L : lhs instanceof Number ? ((Number) lhs).longValue() : Long.valueOf(asString());
  }

  public int asInt() {
    return isNull() ? 0 : lhs instanceof Number ? ((Number) lhs).intValue() : Integer.valueOf(asString());
  }

  public ExprEval defaultValue() {
    switch (rhs) {
      case DOUBLE:
        return ExprEval.of(0D);
      case LONG:
        return ExprEval.of(0L);
      case STRING:
        return ExprEval.of(null);
    }
    return ExprEval.of(null);
  }
}

