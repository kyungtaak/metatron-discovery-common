package com.skt.metatron.discovery.common.preparation.rule;

import org.apache.commons.beanutils.BeanUtils;

import com.skt.metatron.discovery.common.preparation.rule.expr.Constant;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


/**
 * Created by kyungtaak on 2017. 3. 3..
 */
public class RuleBuilder {

  public static Map<String, Supplier<Rule>> ruleNameMapper = new HashMap<>();

  static {
    ruleNameMapper.put("drop", () -> new Drop());
    ruleNameMapper.put("header", () -> new Header());
    ruleNameMapper.put("settype", () -> new SetType());
    ruleNameMapper.put("rename", () -> new Rename());
    ruleNameMapper.put("keep", () -> new Keep());
    ruleNameMapper.put("set", () -> new Set());
  }

  String ruleName;

  List<Arguments> arguments;

  public RuleBuilder(String ruleName, List<Arguments> arguments) {
    this.ruleName = ruleName;
    this.arguments = arguments;
  }

  public Rule setArgs(Rule rule) {

    this.arguments.forEach(arguments -> {

//      Object value;
//      if (arguments.getValue() instanceof IdentifierExpr) {
//        value = ((IdentifierExpr) arguments.getValue()).getValue();
//      } else if (arguments.getValue() instanceof Constant) {
//        value = ((Constant) arguments.getValue()).getValue();
//      } else {
//        value = arguments.getValue();
//      }

      try {
        BeanUtils.setProperty(rule, arguments.getName().toLowerCase(), arguments.getValue());
      } catch (IllegalAccessException | InvocationTargetException e) {
        new RuntimeException("error!" + e.getMessage());
      }
    });

    return rule;
  }

  private Rule getRule() {

    if (ruleNameMapper.containsKey(ruleName.toLowerCase())) {
      return ruleNameMapper.get(ruleName.toLowerCase()).get();
    } else {
      throw new RuntimeException("Not supported rule name ( " + ruleName + " ).");
    }
  }

  public Rule build() {

    Rule rule = getRule();

    return setArgs(rule);
  }
}
