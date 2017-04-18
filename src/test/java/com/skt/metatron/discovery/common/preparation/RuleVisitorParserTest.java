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
    //String ruleCode = "set col: contract_date value: math.floor(datediff(to_date(contract_date), to_date(birth_date)))";
    String ruleCode = "set col: name value: math.max(speed) + math.min(weight)";
    //String ruleCode = "set col: name value: 1 row: name == 'a'";
    runAndPrint(ruleCode);
  }

  @Test
  public void setTypeTest() {
    String ruleCode = "settype col: Category type: 'Integer'";

    runAndPrint(ruleCode);
  }

  @Test
  public void deriveTest() {
//    String ruleCode = "derive value: if(column3 == 'Furniture', true, false) as: 'cate_if'";
//    String ruleCode = "derive value: if(category == 'Furniture', true, false) as: 'cate_if'";
    String ruleCode = "derive value: IF( floor(datediff(to_date(contract_date), to_date(birth_date)) / 365.25/ 10) = 1, 1, 0) as: 'cate_if'";

    runAndPrint(ruleCode);
  }

  @Test
  public void replaceTest() {
    String ruleCode = "replace col: Category with: 'abc' on: /.*/ global: true row: sales > 10";

    runAndPrint(ruleCode);
  }

  @Test
  public void countPatternTest() {
    String ruleCode = "countpattern col: City on: /H.*/ ignoreCase: true";

    runAndPrint(ruleCode);
  }

  @Test
  public void splitTest() {
    String ruleCode = "split col: column7 on: '-' limit: 2 quote: '\"' ignoreCase: true";

    runAndPrint(ruleCode);
  }

  @Test
  public void deleteTest() {
    String ruleCode = "delete row: Sales < 10000";

    runAndPrint(ruleCode);
  }

  @Test
  public void pivotTest() {
//    String ruleCode = "pivot col: column3,column4 value: sum(column22),sum(column23) group: column11,column14 limit: 100";
    String ruleCode = "pivot col: birth_year value: count() group: region limit: 30";

    runAndPrint(ruleCode);
  }

  @Test
  public void unpivotTest() {
    String ruleCode = "unpivot col: column3,column6 groupEvery:2";

    runAndPrint(ruleCode);
  }

  @Test
  public void extractTest() {
    String ruleCode = "extract col: column5 on: /e/ limit: 2 quote: '\''";

    runAndPrint(ruleCode);
  }

  @Test
  public void flattenTest() {
    String ruleCode = "flatten col: column5";

    runAndPrint(ruleCode);
  }

  @Test
  public void mergeTest() {
    String ruleCode = "merge col: column5,column6 with: ',' as: 'test'";

    runAndPrint(ruleCode);
  }

  @Test
  public void nestTest() {
    String ruleCode = "nest col:ItemA,ItemB into:'map' as:'myMap'";

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