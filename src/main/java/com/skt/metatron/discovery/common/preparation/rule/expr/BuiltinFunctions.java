/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.skt.metatron.discovery.common.preparation.rule.expr;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expr.NumericBinding;
import com.skt.metatron.discovery.common.preparation.rule.expr.Function.Factory;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public interface BuiltinFunctions extends Function.Library {

  abstract class SingleParam implements Function {
    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 1) {
        throw new RuntimeException("function '" + name() + "' needs 1 argument");
      }
      Expr expr = args.get(0);
      return eval(expr.eval(bindings));
    }

    protected abstract ExprEval eval(ExprEval param);
  }

  abstract class DoubleParam implements Function {
    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 2) {
        throw new RuntimeException("function '" + name() + "' needs 2 arguments");
      }
      Expr expr1 = args.get(0);
      Expr expr2 = args.get(1);
      return eval(expr1.eval(bindings), expr2.eval(bindings));
    }

    protected abstract ExprEval eval(ExprEval x, ExprEval y);
  }

  abstract class TripleParam implements Function {
    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 3) {
        throw new RuntimeException("function '" + name() + "' needs 3 arguments");
      }
      Expr expr0 = args.get(0);
      Expr expr1 = args.get(1);
      Expr expr2 = args.get(2);
      return eval(expr0.eval(bindings), expr1.eval(bindings), expr2.eval(bindings));
    }

    protected abstract ExprEval eval(ExprEval x, ExprEval y, ExprEval z);
  }

  abstract class NamedParams implements Function, Factory {

    private Map<String, Expr> constantNamedParam; // cache

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      List<Expr> prefix = Lists.newArrayList();
      int i = 0;
      for (; i < args.size(); i++) {
        Expr expr = args.get(i);
        if (expr instanceof Expr.AssignExpr) {
          break;
        }
        prefix.add(args.get(i));
      }
      Map<String, Expr> params;
      if (constantNamedParam != null) {
        params = constantNamedParam;
      } else {
        params = Maps.newHashMapWithExpectedSize(args.size() - i);
        boolean allConstants = true;
        for (; i < args.size(); i++) {
          Expr expr = args.get(i);
          if (!(expr instanceof Expr.AssignExpr)) {
            throw new RuntimeException("function '" + name() + "' requires named parameters");
          }
          Expr.AssignExpr assign = (Expr.AssignExpr) expr;
          params.put(Evals.getIdentifier(assign.assignee), assign.assigned);
          allConstants &= Evals.isConstant(assign.assigned);
        }
        if (allConstants) {
          constantNamedParam = params;
        }
      }
      return eval(prefix, params, bindings);
    }

    protected abstract ExprEval eval(List<Expr> exprs, Map<String, Expr> params, NumericBinding bindings);
  }

  abstract class SingleParamMath extends SingleParam {
    @Override
    protected ExprEval eval(ExprEval param) {
      if (param.type() == ExprType.LONG) {
        return eval(param.longValue());
      } else if (param.type() == ExprType.DOUBLE) {
        return eval(param.doubleValue());
      }
      return ExprEval.of(null, ExprType.STRING);
    }

    protected ExprEval eval(long param) {
      return eval((double) param);
    }

    protected ExprEval eval(double param) {
      return eval((long) param);
    }
  }

  abstract class DoubleParamMath extends DoubleParam {
    @Override
    protected ExprEval eval(ExprEval x, ExprEval y) {
      if (x.type() == ExprType.STRING && y.type() == ExprType.STRING) {
        return ExprEval.of(null, ExprType.STRING);
      }
      if (x.type() == ExprType.LONG && y.type() == ExprType.LONG) {
        return eval(x.longValue(), y.longValue());
      } else {
        return eval(x.doubleValue(), y.doubleValue());
      }
    }

    protected ExprEval eval(long x, long y) {
      return eval((double) x, (double) y);
    }

    protected ExprEval eval(double x, double y) {
      return eval((long) x, (long) y);
    }
  }

  abstract class TripleParamMath extends TripleParam {
    @Override
    protected ExprEval eval(ExprEval x, ExprEval y, ExprEval z) {
      if (x.type() == ExprType.STRING && y.type() == ExprType.STRING && z.type() == ExprType.STRING) {
        return ExprEval.of(null, ExprType.STRING);
      }
      if (x.type() == ExprType.LONG && y.type() == ExprType.LONG && z.type() == ExprType.LONG) {
        return eval(x.longValue(), y.longValue(), z.longValue());
      } else {
        return eval(x.doubleValue(), y.doubleValue(), z.doubleValue());
      }
    }

    protected ExprEval eval(long x, long y, long z) {
      return eval((double) x, (double) y, (double) z);
    }

    protected ExprEval eval(double x, double y, double z) {
      return eval((long) x, (long) y, (long) z);
    }
  }

  class Regex implements Function, Factory {
    private Matcher matcher;
    private int index = -1;

    @Override
    public String name() {
      return "regex";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (matcher == null) {
        if (args.size() != 2 && args.size() != 3) {
          throw new RuntimeException("function '" + name() + "' needs 2 or 3 arguments");
        }
        Expr expr2 = args.get(1);
        matcher = Pattern.compile(Evals.getConstantString(expr2)).matcher("");
        if (args.size() == 3) {
          Expr expr3 = args.get(2);
          index = Ints.checkedCast(Evals.getConstantLong(expr3));
        }
      }
      ExprEval eval = args.get(0).eval(bindings);
      Matcher m = matcher.reset(eval.asString());
      if (index < 0) {
        return ExprEval.of(m.matches());
      }
      return ExprEval.of(m.matches() ? matcher.group(index) : null);
    }

    @Override
    public Function get() {
      return new Regex();
    }
  }

  class Abs extends SingleParamMath {
    @Override
    public String name() {
      return "abs";
    }

    @Override
    protected ExprEval eval(long param) {
      return ExprEval.of(Math.abs(param));
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.abs(param));
    }
  }

  class Acos extends SingleParamMath {
    @Override
    public String name() {
      return "acos";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.acos(param));
    }
  }

  class Asin extends SingleParamMath {
    @Override
    public String name() {
      return "asin";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.asin(param));
    }
  }

  class Atan extends SingleParamMath {
    @Override
    public String name() {
      return "atan";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.atan(param));
    }
  }

  class Cbrt extends SingleParamMath {
    @Override
    public String name() {
      return "cbrt";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.cbrt(param));
    }
  }

  class Ceil extends SingleParamMath {
    @Override
    public String name() {
      return "ceil";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.ceil(param));
    }
  }

  class Cos extends SingleParamMath {
    @Override
    public String name() {
      return "cos";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.cos(param));
    }
  }

  class Cosh extends SingleParamMath {
    @Override
    public String name() {
      return "cosh";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.cosh(param));
    }
  }

  class Exp extends SingleParamMath {
    @Override
    public String name() {
      return "exp";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.exp(param));
    }
  }

  class Expm1 extends SingleParamMath {
    @Override
    public String name() {
      return "expm1";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.expm1(param));
    }
  }

  class Floor extends SingleParamMath {
    @Override
    public String name() {
      return "floor";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.floor(param));
    }
  }

  class GetExponent extends SingleParamMath {
    @Override
    public String name() {
      return "getExponent";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.getExponent(param));
    }
  }

  class Log extends SingleParamMath {
    @Override
    public String name() {
      return "log";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.log(param));
    }
  }

  class Log10 extends SingleParamMath {
    @Override
    public String name() {
      return "log10";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.log10(param));
    }
  }

  class Log1p extends SingleParamMath {
    @Override
    public String name() {
      return "log1p";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.log1p(param));
    }
  }

  class NextUp extends SingleParamMath {
    @Override
    public String name() {
      return "nextUp";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.nextUp(param));
    }
  }

  class Rint extends SingleParamMath {
    @Override
    public String name() {
      return "rint";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.rint(param));
    }
  }

  class Round extends SingleParamMath {
    @Override
    public String name() {
      return "round";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.round(param));
    }
  }

  class Signum extends SingleParamMath {
    @Override
    public String name() {
      return "signum";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.signum(param));
    }
  }

  class Sin extends SingleParamMath {
    @Override
    public String name() {
      return "sin";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.sin(param));
    }
  }

  class Sinh extends SingleParamMath {
    @Override
    public String name() {
      return "sinh";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.sinh(param));
    }
  }

  class Sqrt extends SingleParamMath {
    @Override
    public String name() {
      return "sqrt";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.sqrt(param));
    }
  }

  class Tan extends SingleParamMath {
    @Override
    public String name() {
      return "tan";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.tan(param));
    }
  }

  class Tanh extends SingleParamMath {
    @Override
    public String name() {
      return "tanh";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.tanh(param));
    }
  }

  class ToDegrees extends SingleParamMath {
    @Override
    public String name() {
      return "toDegrees";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.toDegrees(param));
    }
  }

  class ToRadians extends SingleParamMath {
    @Override
    public String name() {
      return "toRadians";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.toRadians(param));
    }
  }

  class Ulp extends SingleParamMath {
    @Override
    public String name() {
      return "ulp";
    }

    @Override
    protected ExprEval eval(double param) {
      return ExprEval.of(Math.ulp(param));
    }
  }

  class Atan2 extends DoubleParamMath {
    @Override
    public String name() {
      return "atan2";
    }

    @Override
    protected ExprEval eval(double y, double x) {
      return ExprEval.of(Math.atan2(y, x));
    }
  }

  class CopySign extends DoubleParamMath {
    @Override
    public String name() {
      return "copySign";
    }

    @Override
    protected ExprEval eval(double x, double y) {
      return ExprEval.of(Math.copySign(x, y));
    }
  }

  class Hypot extends DoubleParamMath {
    @Override
    public String name() {
      return "hypot";
    }

    @Override
    protected ExprEval eval(double x, double y) {
      return ExprEval.of(Math.hypot(x, y));
    }
  }

  class Remainder extends DoubleParamMath {
    @Override
    public String name() {
      return "remainder";
    }

    @Override
    protected ExprEval eval(double x, double y) {
      return ExprEval.of(Math.IEEEremainder(x, y));
    }
  }

  class Max extends DoubleParamMath {
    @Override
    public String name() {
      return "max";
    }

    @Override
    protected ExprEval eval(long x, long y) {
      return ExprEval.of(Math.max(x, y));
    }

    @Override
    protected ExprEval eval(double x, double y) {
      return ExprEval.of(Math.max(x, y));
    }
  }

  class Min extends DoubleParamMath {
    @Override
    public String name() {
      return "min";
    }

    @Override
    protected ExprEval eval(long x, long y) {
      return ExprEval.of(Math.min(x, y));
    }

    @Override
    protected ExprEval eval(double x, double y) {
      return ExprEval.of(Math.min(x, y));
    }
  }

  class NextAfter extends DoubleParamMath {
    @Override
    public String name() {
      return "nextAfter";
    }

    @Override
    protected ExprEval eval(double x, double y) {
      return ExprEval.of(Math.nextAfter(x, y));
    }
  }

  class Pow extends DoubleParamMath {
    @Override
    public String name() {
      return "pow";
    }

    @Override
    protected ExprEval eval(double x, double y) {
      return ExprEval.of(Math.pow(x, y));
    }
  }

  class Scalb extends DoubleParam {
    @Override
    public String name() {
      return "scalb";
    }

    @Override
    protected ExprEval eval(ExprEval x, ExprEval y) {
      return ExprEval.of(Math.scalb(x.doubleValue(), y.intValue()));
    }
  }

  class ConditionFunc implements Function {
    @Override
    public String name() {
      return "if";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() < 3) {
        throw new RuntimeException("function 'if' needs at least 3 argument");
      }
      if (args.size() % 2 == 0) {
        throw new RuntimeException("function 'if' needs default value");
      }

      for (int i = 0; i < args.size() - 1; i += 2) {
        if (args.get(i).eval(bindings).asBoolean()) {
          return args.get(i + 1).eval(bindings);
        }
      }
      return args.get(args.size() - 1).eval(bindings);
    }
  }

  class CastFunc implements Function, Factory {
    private ExprType castTo;

    @Override
    public String name() {
      return "cast";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 2) {
        throw new RuntimeException("function '" + name() + "' needs 2 argument");
      }
      if (castTo == null) {
        castTo = ExprType.bestEffortOf(Evals.getConstantString(args.get(1)));
      }
      return Evals.castTo(args.get(0).eval(bindings), castTo);
    }

    @Override
    public Function get() {
      return new CastFunc();
    }
  }

  class TimestampFromEpochFunc extends NamedParams {

    private DateTimeFormatter formatter;

    @Override
    public String name() {
      return "timestamp";
    }

    @Override
    protected ExprEval eval(List<Expr> args, Map<String, Expr> params, NumericBinding bindings) {
      if (args.isEmpty()) {
        throw new RuntimeException("function '" + name() + " needs at least 1 generic argument");
      }
      if (formatter == null) {
        initialize(args, params, bindings);
      }
      ExprEval value = args.get(0).eval(bindings);
      if (value.type() != ExprType.STRING) {
        throw new IllegalArgumentException("first argument should be string type but got " + value.type() + " type");
      }

      DateTime date;
      try {
        date = formatter.parseDateTime(value.stringValue());
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("invalid value " + value.stringValue(), e);
      }
      return toValue(date);
    }

    protected void initialize(List<Expr> args, Map<String, Expr> params, NumericBinding bindings) {
      String format = args.size() > 1 ?
          Evals.getConstantString(args.get(1)).trim() :
          Evals.evalOptionalString(params.get("format"), bindings);
      String language = args.size() > 2 ?
          Evals.getConstantString(args.get(2)).trim() :
          Evals.evalOptionalString(params.get("locale"), bindings);
      String timezone = args.size() > 3 ?
          Evals.getConstantString(args.get(3)).trim() :
          Evals.evalOptionalString(params.get("timezone"), bindings);

      formatter = format == null ? DateTimeFormat.fullDateTime() : DateTimeFormat.forPattern(format);
      if(language != null) {
        formatter.withLocale(Locale.forLanguageTag(language));
      }
      if (timezone != null) {
        formatter.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone(timezone)));
      }
    }

    protected ExprEval toValue(DateTime date) {
      return ExprEval.of(date.getMillis(), ExprType.LONG);
    }

    @Override
    public Function get() {
      return new TimestampFromEpochFunc();
    }
  }

  class UnixTimestampFunc extends TimestampFromEpochFunc {
    @Override
    public String name() {
      return "unix_timestamp";
    }

    @Override
    protected final ExprEval toValue(DateTime date) {
      return ExprEval.of(date.getMillis() / 1000, ExprType.LONG);
    }

    @Override
    public Function get() {
      return new UnixTimestampFunc();
    }
  }

  class TimestampValidateFunc extends TimestampFromEpochFunc {
    @Override
    public String name() {
      return "timestamp_validate";
    }

    @Override
    protected final ExprEval eval(List<Expr> args, Map<String, Expr> params, NumericBinding bindings) {
      try {
        super.eval(args, params, bindings);
      } catch (Exception e) {
        return ExprEval.of(false);
      }
      return ExprEval.of(true);
    }

    @Override
    public Function get() {
      return new TimestampValidateFunc();
    }
  }

  class TimeExtractFunc extends TimestampFromEpochFunc {

    private DateTimeFormatter outputFormat;

    @Override
    public String name() {
      return "time_extract";
    }

    @Override
    protected void initialize(List<Expr> args, Map<String, Expr> params, NumericBinding bindings) {
      super.initialize(args, params, bindings);
      String format = Evals.evalOptionalString(params.get("out.format"), bindings);
      String language = Evals.evalOptionalString(params.get("out.locale"), bindings);
      String timezone = Evals.evalOptionalString(params.get("out.timezone"), bindings);

      outputFormat = format == null ? DateTimeFormat.fullDateTime() : DateTimeFormat.forPattern(format);
      if(language != null) {
        outputFormat.withLocale(Locale.forLanguageTag(language));
      }
      if (timezone != null) {
        outputFormat.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone(timezone)));
      }
    }

    @Override
    protected final ExprEval toValue(DateTime date) {
      return ExprEval.of(date.toString(outputFormat));
    }

    @Override
    public Function get() {
      return new TimeExtractFunc();
    }
  }

  class IsNullFunc implements Function {
    @Override
    public String name() {
      return "isnull";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 1) {
        throw new RuntimeException("function 'is_null' needs 1 argument");
      }
      ExprEval eval = args.get(0).eval(bindings);
      return ExprEval.of(eval.isNull());
    }
  }

  class NvlFunc implements Function {
    @Override
    public String name() {
      return "nvl";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 2) {
        throw new RuntimeException("function 'nvl' needs 2 arguments");
      }
      ExprEval eval = args.get(0).eval(bindings);
      if (eval.isNull()) {
        return args.get(1).eval(bindings);
      }
      return eval;
    }
  }

  class Coalesce extends NvlFunc {
    @Override
    public String name() {
      return "coalesce";
    }
  }

  class DateDiffFunc implements Function {
    @Override
    public String name() {
      return "datediff";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() < 2) {
        throw new RuntimeException("function 'datediff' need at least 2 arguments");
      }
      DateTime t1 = Evals.toDateTime(args.get(0).eval(bindings));
      DateTime t2 = Evals.toDateTime(args.get(1).eval(bindings));
      return ExprEval.of(Days.daysBetween(t1.withTimeAtStartOfDay(), t2.withTimeAtStartOfDay()).getDays());
    }
  }

  class CaseWhenFunc implements Function {
    @Override
    public String name() {
      return "case";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() < 3) {
        throw new RuntimeException("function 'case' needs at least 3 arguments");
      }
      final ExprEval leftVal = args.get(0).eval(bindings);
      for (int i = 1; i < args.size() - 1; i += 2) {
        if (Evals.eq(leftVal, args.get(i).eval(bindings))) {
          return args.get(i + 1).eval(bindings);
        }
      }
      if (args.size() % 2 != 1) {
        return args.get(args.size() - 1).eval(bindings);
      }
      return leftVal.defaultValue();
    }
  }

//  class JavaScriptFunc implements Function {
//    ScriptableObject scope;
//    org.mozilla.javascript.Function fnApply;
//    com.google.common.base.Function<NumericBinding, Object[]> bindingExtractor;
//
//    @Override
//    public String name() {
//      return "javascript";
//    }
//
//    @Override
//    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
//      if (fnApply == null) {
//        if (args.size() != 2) {
//          throw new RuntimeException("function 'javascript' needs 2 argument");
//        }
//        makeFunction(Evals.getConstantString(args.get(0)), Evals.getConstantString(args.get(1)));
//      }
//
//      final Object[] params = bindingExtractor.apply(bindings);
//      // one and only one context per thread
//      final Context cx = Context.enter();
//      try {
//        return ExprEval.bestEffortOf(fnApply.call(cx, scope, scope, params));
//      } finally {
//        Context.exit();
//      }
//    }
//
//    private void makeFunction(String required, String script) {
//      final String[] bindings = splitAndTrim(required);
//      final String function = "function(" + StringUtils.join(bindings, ",") + ") {" + script + "}";
//
//      final Context cx = Context.enter();
//      try {
//        cx.setOptimizationLevel(9);
//        this.scope = cx.initStandardObjects();
//        this.fnApply = cx.compileFunction(scope, function, "script", 1, null);
//      } finally {
//        Context.exit();
//      }
//
//      final Object[] convey = new Object[bindings.length];
//      bindingExtractor = new com.google.common.base.Function<NumericBinding, Object[]>() {
//        @Override
//        public Object[] apply(NumericBinding input) {
//          for (int i = 0; i < bindings.length; i++) {
//            convey[i] = input.get(bindings[i]);
//          }
//          return convey;
//        }
//      };
//    }
//
//    private String[] splitAndTrim(String required) {
//      String[] splits = required.split(",");
//      for (int i = 0; i < splits.length; i++) {
//        splits[i] = splits[i].trim();
//      }
//      return splits;
//    }
//  }

  class ConcatFunc implements Function {
    @Override
    public String name() {
      return "concat";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      StringBuilder b = new StringBuilder();
      for (Expr expr : args) {
        b.append(expr.eval(bindings).asString());
      }
      return ExprEval.of(b.toString());
    }
  }

  class FormatFunc implements Function, Factory {
    final StringBuilder builder = new StringBuilder();
    final Formatter formatter = new Formatter(builder);

    String format;
    Object[] formatArgs;

    @Override
    public String name() {
      return "format";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (format == null) {
        if (args.isEmpty()) {
          throw new RuntimeException("function 'format' needs at least 1 argument");
        }
        format = Evals.getConstantString(args.get(0));
        formatArgs = new Object[args.size() - 1];
      }
      builder.setLength(0);
      for (int i = 0; i < formatArgs.length; i++) {
        formatArgs[i] = args.get(i + 1).eval(bindings).value();
      }
      formatter.format(format, formatArgs);
      return ExprEval.of(builder.toString());
    }

    @Override
    public Function get() {
      return new FormatFunc();
    }
  }

  class LPadFunc implements Function, Factory {
    @Override
    public String name() {
      return "lpad";
    }

    private transient int length = -1;
    private transient char padding;

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (length < 0) {
        if (args.size() < 3) {
          throw new RuntimeException("function 'lpad' needs 3 arguments");
        }
        length = (int) Evals.getConstantLong(args.get(1));
        String string = Evals.getConstantString(args.get(2));
        if (string.length() != 1) {
          throw new RuntimeException("3rd argument of function 'lpad' should be constant char");
        }
        padding = string.charAt(0);
      }
      String input = args.get(0).eval(bindings).asString();
      return ExprEval.of(StringUtils.leftPad(input, length, padding));
    }

    @Override
    public Function get() {
      return new LPadFunc();
    }
  }

  class RPadFunc implements Function, Factory {
    @Override
    public String name() {
      return "rpad";
    }

    private transient int length = -1;
    private transient char padding;

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (length < 0) {
        if (args.size() < 3) {
          throw new RuntimeException("function 'rpad' needs 3 arguments");
        }
        length = (int) Evals.getConstantLong(args.get(1));
        String string = Evals.getConstantString(args.get(2));
        if (string.length() != 1) {
          throw new RuntimeException("3rd argument of function 'rpad' should be constant char");
        }
        padding = string.charAt(0);
      }
      String input = args.get(0).eval(bindings).asString();
      return ExprEval.of(StringUtils.rightPad(input, length, padding));
    }

    @Override
    public Function get() {
      return new RPadFunc();
    }
  }

  class UpperFunc implements Function {
    @Override
    public String name() {
      return "upper";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 1) {
        throw new RuntimeException("function 'upper' needs 1 argument");
      }
      String input = args.get(0).eval(bindings).asString();
      return ExprEval.of(input == null ? null : input.toUpperCase());
    }
  }

  class LowerFunc implements Function {
    @Override
    public String name() {
      return "lower";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 1) {
        throw new RuntimeException("function 'lower' needs 1 argument");
      }
      String input = args.get(0).eval(bindings).asString();
      return ExprEval.of(input == null ? null : input.toLowerCase());
    }
  }

  class SplitFunc implements Function {
    @Override
    public String name() {
      return "split";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 3) {
        throw new RuntimeException("function 'split' needs 3 arguments");
      }
      String input = args.get(0).eval(bindings).asString();
      String splitter = args.get(1).eval(bindings).asString();
      int index = (int) args.get(2).eval(bindings).longValue();

      String[] split = input.split(splitter);
      return ExprEval.of(index >= split.length ? null : split[index]);
    }
  }

  class ProperFunc implements Function {
    @Override
    public String name() {
      return "proper";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 1) {
        throw new RuntimeException("function 'proper' needs 1 argument");
      }
      String input = args.get(0).eval(bindings).asString();
      return ExprEval.of(
          StringUtils.isEmpty(input) ? input :
              Character.toUpperCase(input.charAt(0)) + input.substring(1)
      );
    }
  }

  class LengthFunc implements Function {
    @Override
    public String name() {
      return "length";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 1) {
        throw new RuntimeException("function 'length' needs 1 argument");
      }
      String input = args.get(0).eval(bindings).asString();
      return ExprEval.of(StringUtils.isEmpty(input) ? 0 : input.length());
    }
  }

  class LeftFunc implements Function {
    @Override
    public String name() {
      return "left";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 2) {
        throw new RuntimeException("function 'left' needs 2 arguments");
      }
      String input = args.get(0).eval(bindings).asString();
      int index = (int) args.get(1).eval(bindings).longValue();

      return ExprEval.of(StringUtils.isEmpty(input) || input.length() < index ? input : input.substring(0, index));
    }
  }

  class RightFunc implements Function {
    @Override
    public String name() {
      return "right";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 2) {
        throw new RuntimeException("function 'right' needs 2 arguments");
      }
      String input = args.get(0).eval(bindings).asString();
      int index = (int) args.get(1).eval(bindings).longValue();

      return ExprEval.of(
          StringUtils.isEmpty(input) || input.length() < index
              ? input
              : input.substring(input.length() - index)
      );
    }
  }

  class MidFunc implements Function {
    @Override
    public String name() {
      return "mid";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 3) {
        throw new RuntimeException("function 'mid' needs 3 arguments");
      }
      String input = args.get(0).eval(bindings).asString();
      int start = (int) args.get(1).eval(bindings).longValue();
      int end = (int) args.get(2).eval(bindings).longValue();

      return ExprEval.of(StringUtils.isEmpty(input) ? input : input.substring(start, end));
    }
  }

  class IndexOfFunc implements Function {
    @Override
    public String name() {
      return "indexOf";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 2) {
        throw new RuntimeException("function 'indexOf' needs 2 arguments");
      }
      String input = args.get(0).eval(bindings).asString();
      String find = args.get(1).eval(bindings).asString();

      return ExprEval.of(StringUtils.isEmpty(input) || StringUtils.isEmpty(find) ? -1 : input.indexOf(find));
    }
  }

  class ReplaceFunc implements Function {
    @Override
    public String name() {
      return "replace";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 3) {
        throw new RuntimeException("function 'replace' needs 2 arguments");
      }
      String input = args.get(0).eval(bindings).asString();
      String find = args.get(1).eval(bindings).asString();
      String replace = args.get(2).eval(bindings).asString();

      return ExprEval.of(
          StringUtils.isEmpty(input) || StringUtils.isEmpty(find) ? input :
              StringUtils.replace(input, find, replace)
      );
    }
  }

  class TrimFunc implements Function {
    @Override
    public String name() {
      return "trim";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (args.size() != 1) {
        throw new RuntimeException("function 'trim' needs 1 argument");
      }
      String input = args.get(0).eval(bindings).asString();
      return ExprEval.of(StringUtils.isEmpty(input) ? input : input.trim());
    }
  }

  class InFunc implements Function, Factory {
    @Override
    public String name() {
      return "in";
    }

    private transient Set<Object> set;

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      if (set == null) {
        if (args.size() < 2) {
          throw new RuntimeException("function 'in' needs at least 2 arguments");
        }
        set = Sets.newHashSet();
        for (int i = 1; i < args.size(); i++) {
          set.add(args.get(i).eval(null).value());
        }
      }
      return ExprEval.of(set.contains(args.get(0).eval(bindings).value()));
    }

    @Override
    public Function get() {
      return new InFunc();
    }
  }

  class Now implements Function {

    @Override
    public String name() {
      return "now";
    }

    @Override
    public ExprEval apply(List<Expr> args, NumericBinding bindings) {
      return ExprEval.of(System.currentTimeMillis());
    }
  }

}
