package com.skt.metatron.discovery.common.preparation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skt.metatron.discovery.common.preparation.rule.Rule;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kyungtaak on 2017. 3. 7..
 */
public class RuleVisitorParserTest {
  @Test
  public void dropTest() {
    String ruleCode = "drop col: test1, test2";

    runAndPrint(ruleCode);
  }

  @Test
  public void headerTest() {
    String ruleCode = "header rownum: 1";

    runAndPrint(ruleCode);
  }

  @Test
  public void renameTest() {
    String ruleCode = "rename col: test to: 'abc'";

    runAndPrint(ruleCode);
  }

  @Test
  public void keepTest1() {
    String ruleCode = "keep row: isnull(Category)";

    runAndPrint(ruleCode);
  }

  @Test
  public void keepTest2() {
    String ruleCode = "keep row: (column4-1) > 4";

    runAndPrint(ruleCode);
  }

  @Test
  public void setTest2() {
    String ruleCode = "set col: lot_id value: lower(column) row: cnt > 5";

    runAndPrint(ruleCode);
  }

  @Test
  public void setTypeTest() {
    String ruleCode = "settype col: Category type: 'Integer'";

    runAndPrint(ruleCode);
  }

  private void runAndPrint(String ruleCode) {

    Rule rule = new RuleVisitorParser().parse(ruleCode);

    ObjectMapper mapper = new ObjectMapper();
    String json = null;
    try {
      json = mapper.writeValueAsString(rule);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.printf("code below: %n '%s' %n has been parsed to object: %n '%s'%n '%s'%n", ruleCode, json, rule.toString());
  }

}