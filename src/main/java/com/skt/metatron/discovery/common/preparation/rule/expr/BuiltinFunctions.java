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

import com.skt.metatron.discovery.common.preparation.rule.expr.Expr.NumericBinding;
import com.skt.metatron.discovery.common.preparation.rule.expr.Function.Factory;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;

/**
 */
public interface BuiltinFunctions extends Function.Library {

  Logger LOGGER = LoggerFactory.getLogger(BuiltinFunctions.class);

  abstract class SingleParam implements Function {

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 1) {
        LOGGER.warn("function '{}' needs 1 argument", name());
        return false;
      }

      return true;
    }

  }

  abstract class DoubleParam implements Function {

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 2) {
        LOGGER.warn("function '{}' needs 2 arguments", name());
        return false;
      }

      return true;
    }

  }

  abstract class TripleParam implements Function {

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 3) {
        LOGGER.warn("function '{}' needs 3 arguments", name());
        return false;
      }

      return true;
    }

  }

  abstract class NamedParams implements Function, Factory {

    @Override
    public boolean validate(List<Expr> args) {

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
      for (; i < args.size(); i++) {
        Expr expr = args.get(i);
        if (!(expr instanceof Expr.AssignExpr)) {
          LOGGER.warn("function '{}' requires named parameters", name());
          return false;
        }
      }

      return true;
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
    public boolean validate(List<Expr> args) {
      if (args.size() != 2 && args.size() != 3) {
        LOGGER.warn("function '{}' needs 2 or 3 arguments", name());
        return false;
      }
      return true;
    }

    @Override
    public Function get() {
      return new Regex();
    }
  }

  abstract class AggreationFunc extends SingleParam {
  }

  class Sum extends AggreationFunc {

    @Override
    public String name() {
      return "sum";
    }
  }

  class Avg extends AggreationFunc {

    @Override
    public String name() {
      return "avg";
    }
  }

  class Max extends AggreationFunc {

    @Override
    public String name() {
      return "max";
    }
  }

  class Min extends AggreationFunc {

    @Override
    public String name() {
      return "min";
    }
  }

  class Count extends AggreationFunc {

    @Override
    public String name() {
      return "count";
    }
  }



    abstract class SingleParamMath extends SingleParam implements Function {
    }

    abstract class DoubleParamMath extends DoubleParam implements Function {
    }

    abstract class TripleParamMath extends TripleParam implements Function {
    }

    class Abs extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.abs";
      }
    }

    class Acos extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.acos";
      }
    }

    class Asin extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.asin";
      }
    }

    class Atan extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.atan";
      }
    }

    class Cbrt extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.cbrt";
      }
    }

    class Ceil extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.ceil";
      }
    }

    class Cos extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.cos";
      }
    }

    class Cosh extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.cosh";
      }
    }

    class Exp extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.exp";
      }
    }

    class Expm1 extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.expm1";
      }
    }

    class Floor extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.floor";
      }
    }

    class GetExponent extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.getExponent";
      }
    }

    class Round extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.round";
      }
    }

    class Signum extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.signum";
      }
    }

    class Sin extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.sin";
      }
    }

    class Sinh extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.sinh";
      }
    }

    class Sqrt extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.sqrt";
      }
    }

    class Tan extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.tan";
      }
    }

    class Tanh extends SingleParamMath implements Function {
      @Override
      public String name() {
        return "math.tanh";
      }
    }

    class MathMax extends DoubleParamMath implements Function {
      @Override
      public String name() {
        return "math.max";
      }
    }

    class MathMin extends DoubleParamMath implements Function {
      @Override
      public String name() {
        return "math.min";
      }
    }

    class NextAfter extends DoubleParamMath implements Function {
      @Override
      public String name() {
        return "math.nextAfter";
      }
    }

    class Pow extends DoubleParamMath implements Function {
      @Override
      public String name() {
        return "math.pow";
      }
    }


  class ConditionFunc implements Function {
    @Override
    public String name() {
      return "if";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() < 3) {
        LOGGER.warn("function 'if' needs at least 3 argument");
        return false;
      }
      if (args.size() % 2 == 0) {
        LOGGER.warn("function 'if' needs default value");
        return false;
      }
      return true;
    }
  }

  class CastFunc implements Function, Factory {
    private ExprType castTo;

    @Override
    public String name() {
      return "cast";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 2) {
        LOGGER.warn("function '" + name() + "' needs 2 argument");
        return false;
      }

      return true;
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
    public boolean validate(List<Expr> args) {

      if (args.isEmpty()) {
        LOGGER.warn("function '{}' needs at least 1 generic argument", name());
        return false;
      }

      return true;
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
      if (language != null) {
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
      if (language != null) {
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
    public boolean validate(List<Expr> args) {

      if (args.size() != 1) {
        LOGGER.warn("function 'isNull' needs 1 argument");
        return false;
      }

      return true;
    }
  }

  class NvlFunc implements Function {
    @Override
    public String name() {
      return "nvl";
    }

    @Override
    public boolean validate(List<Expr> args) {

      if (args.size() != 2) {
        LOGGER.warn("function 'nvl' needs 2 arguments");
        return false;
      }

      return true;
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
    public boolean validate(List<Expr> args) {
      if (args.size() < 2) {
        LOGGER.warn("function 'datediff' needs at least 2 arguments");
        return false;
      }

      return true;
    }

  }

  class ToDateFunc implements Function {
    @Override
    public String name() {
      return "to_date";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() == 1) {
        LOGGER.warn("function 'datediff' needs 1 arguments");
        return false;
      }

      return true;
    }

  }

  class CaseWhenFunc implements Function {
    @Override
    public String name() {
      return "case";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() < 3) {
        LOGGER.warn("function 'case' needs at least 3 arguments");
        return false;
      }

      return true;
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
    public boolean validate(List<Expr> args) {
      return true;
    }
  }

  class FormatFunc implements Function, Factory {

    @Override
    public String name() {
      return "format";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.isEmpty()) {
        LOGGER.warn("function 'format' needs at least 1 argument");
        return false;
      }

      return true;
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
    public boolean validate(List<Expr> args) {
      if (args.isEmpty()) {
        LOGGER.warn("function 'lpad' needs 3 arguments");
        return false;
      }

      length = (int) Evals.getConstantLong(args.get(1));
      String string = Evals.getConstantString(args.get(2));
      if (string.length() != 1) {
        LOGGER.warn("3rd argument of function 'lpad' should be constant char");
        return false;
      }

      return true;
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
    public boolean validate(List<Expr> args) {
      if (args.isEmpty()) {
        LOGGER.warn("function 'rpad' needs 3 arguments");
        return false;
      }

      length = (int) Evals.getConstantLong(args.get(1));
      String string = Evals.getConstantString(args.get(2));
      if (string.length() != 1) {
        LOGGER.warn("3rd argument of function 'rpad' should be constant char");
        return false;
      }

      return true;
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
    public boolean validate(List<Expr> args) {
      if (args.size() != 1) {
        LOGGER.warn("function 'upper' needs 1 argument");
        return false;
      }

      return true;
    }

  }

  class LowerFunc implements Function {
    @Override
    public String name() {
      return "lower";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 1) {
        LOGGER.warn("function 'lower' needs 1 argument");
        return false;
      }

      return true;
    }

  }

  class SplitFunc implements Function {
    @Override
    public String name() {
      return "split";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 3) {
        LOGGER.warn("function 'split' needs 3 arguments");
        return false;
      }

      return true;
    }
  }

  class ProperFunc implements Function {
    @Override
    public String name() {
      return "proper";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 1) {
        LOGGER.warn("function 'split' needs 1 arguments");
        return false;
      }

      return true;
    }

  }

  class LengthFunc implements Function {
    @Override
    public String name() {
      return "length";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 1) {
        LOGGER.warn("function 'length' needs 1 argument");
        return false;
      }

      return true;
    }
  }

  class LeftFunc implements Function {
    @Override
    public String name() {
      return "left";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 2) {
        LOGGER.warn("function 'left' needs 2 arguments");
        return false;
      }

      return true;
    }

  }

  class RightFunc implements Function {
    @Override
    public String name() {
      return "right";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 2) {
        LOGGER.warn("function 'right' needs 2 arguments");
        return false;
      }

      return true;
    }
  }

  class MidFunc implements Function {
    @Override
    public String name() {
      return "mid";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 3) {
        LOGGER.warn("function 'mid' needs 3 arguments");
        return false;
      }

      return true;
    }

  }

  class IndexOfFunc implements Function {
    @Override
    public String name() {
      return "indexOf";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 2) {
        LOGGER.warn("function 'mid' needs 2 arguments");
        return false;
      }

      return true;
    }
  }

  class ReplaceFunc implements Function {

    @Override
    public String name() {
      return "replace";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 3) {
        LOGGER.warn("function 'replace' needs 2 arguments");
        return false;
      }

      return true;
    }

  }

  class TrimFunc implements Function {
    @Override
    public String name() {
      return "trim";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() != 3) {
        LOGGER.warn("function 'trim' needs 1 argument");
        return false;
      }

      return true;
    }
  }

  class InFunc implements Function, Factory {
    @Override
    public String name() {
      return "in";
    }

    @Override
    public boolean validate(List<Expr> args) {
      if (args.size() < 2) {
        LOGGER.warn("function 'in' needs at least 2 arguments");
        return false;
      }

      return true;
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
    public boolean validate(List<Expr> args) {
      return true;
    }

  }

}
