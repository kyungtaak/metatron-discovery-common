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
    String ruleCode = "drop col: test1~test2, test5";
    assertEquals("Drop{col=[test1~test2, test5]}", runAndPrint(ruleCode));
  }

  @Test
  public void headerTest() {
    String ruleCode = "header rownum: 1";
    assertEquals("Header{rownum=1}", runAndPrint(ruleCode));
  }

  @Test
  public void renameTest() {
    String ruleCode = "rename col: test to: 'abc'";
    assertEquals("Rename{col='test', to=''abc''}", runAndPrint(ruleCode));
  }

  @Test
  public void keepTest1() {
    String ruleCode = "keep row: isnull(Category)";
    assertEquals("Keep{row=isnull(Category)}", runAndPrint(ruleCode));
  }

  @Test
  public void keepTest2() {
    String ruleCode = "keep row: (column4-1) > 4";
    assertEquals("Keep{row=((column4 - 1) > 4)}", runAndPrint(ruleCode));
  }

  @Test
  public void setTest2() {
    String ruleCode = "set col: name value: if(item, 1, 2) row: name == 'a'";
    assertEquals("Set{col='name', value=if(item,1,2), row=(name == 'a')}", runAndPrint(ruleCode));
  }

  @Test
  public void setTypeTest() {
    String ruleCode = "settype col: Category type: 'Integer'";
    assertEquals("SetType{col='Category', type=''Integer''}", runAndPrint(ruleCode));
  }

  @Test
  public void deriveTest() {
    String ruleCode = "derive value: substring(name, 1, 2)/10/3 as: 'cate_if'";
    assertEquals("Derive{value=((substring(name,1,2) / 10) / 3), as=''cate_if''}", runAndPrint(ruleCode));
  }

  @Test
  public void replaceTest() {
    String ruleCode = "replace col: Category with: 'abc' on: /\\d{3}/ global: true row: sales > 10";
    assertEquals("Replace{col=Category, on=/\\\\d{3}/, after=null, before=null, with='abc', global=true, row=(sales > 10)}", runAndPrint(ruleCode));
  }

  @Test
  public void countPatternTest() {
    String ruleCode = "countpattern col: City on: /H.*/ ignoreCase: true";
    assertEquals("CountPattern{col='City', on=/H.*/, after=null, before=null, ignoreCase=true}", runAndPrint(ruleCode));
  }

  @Test
  public void splitTest() {
    String ruleCode = "split col: column7 on: '-' limit: 2 quote: '\"' ignoreCase: true";
    assertEquals("Split{col='column7', on='-', limit=2, quote=''\"'', ignoreCase=true}", runAndPrint(ruleCode));
  }

  @Test
  public void deleteTest() {
    String ruleCode = "delete row: Sales < 10000";
    assertEquals("Delete{row=(Sales < 10000)}", runAndPrint(ruleCode));
  }

  @Test
  public void pivotTest1() {
    String ruleCode = "pivot col: birth_year value: count() group: region limit: 30";
    assertEquals("Pivot{col=birth_year, value=count(), group=region, limit=30}", runAndPrint(ruleCode));
  }

  @Test
  public void pivotTest2() {
    String ruleCode = "pivot col: column3,column4 value: sum(column22),sum(column23) group: column11,column14 limit: 100";
    assertEquals("Pivot{col=[column3, column4], value=FunctionArrayExpr{functions=[sum(column22), sum(column23)]}, group=[column11, column14], limit=100}", runAndPrint(ruleCode));
  }

  @Test
  public void unpivotTest1() {
    String ruleCode = "unpivot col: column3,column6 groupEvery:2";
    assertEquals("Unpivot{col=[column3, column6], groupEvery=2}", runAndPrint(ruleCode));
  }

  @Test
  public void unpivotTest2() {
    String ruleCode = "unpivot col: column3 groupEvery:1";
    assertEquals("Unpivot{col=column3, groupEvery=1}", runAndPrint(ruleCode));
  }

  @Test
  public void extractTest() {
    String ruleCode = "extract col: column5 on: /e/ limit: 2 quote: '\''";
    assertEquals("Extract{col='column5', on=/e/, limit=2, quote='''', ignoreCase=null}", runAndPrint(ruleCode));
  }

  @Test
  public void flattenTest() {
    String ruleCode = "flatten col: column5";
    assertEquals("Flatten{col='column5'}", runAndPrint(ruleCode));
  }

  @Test
  public void mergeTest() {
    String ruleCode = "merge col: column5,column6~column8 with: ',' as: 'test'";
    assertEquals("Merge{col=[column5, column6~column8], with=',', as=''test''}", runAndPrint(ruleCode));
  }

  @Test
  public void nestTest() {
    String ruleCode = "nest col: ItemA,ItemB into:'map' as:'myMap'";
    assertEquals("Nest{col=[ItemA, ItemB], into=''map'', as=''myMap''}", runAndPrint(ruleCode));
  }

  @Test
  public void joinTest() {
    String ruleCode = "join dataset2: name2 leftSelectCol: ItemA,ItemB,ItemC rigthSelectCol: ItemD,ItemE condition: ItemF=ItemG && ItemH=ItemI joinType: 'inner'";
    assertEquals("Join{dataset2=name2, leftSelectCol=[ItemA, ItemB, ItemC], condition=((ItemF = ItemG) && (ItemH = ItemI)), rigthSelectCol=[ItemD, ItemE], joinType=''inner''}", runAndPrint(ruleCode));
  }

  @Test
  public void aggregateTest1() {
    String ruleCode = "aggregate value: count(), sum(col1) group: region,city limit: 30";
    assertEquals("Aggregate{value=FunctionArrayExpr{functions=[count(), sum(col1)]}, group=[region, city]}", runAndPrint(ruleCode));
  }

  @Test
  public void aggregateTest2() {
    String ruleCode = "aggregate col: column3,column4 value: sum(column22),sum(column23) group: column11,column14 limit: 100";
    assertEquals("Aggregate{value=FunctionArrayExpr{functions=[sum(column22), sum(column23)]}, group=[column11, column14]}", runAndPrint(ruleCode));
  }

  @Test
  public void unnestTest() {
    String ruleCode = "unnest col: name into: map idx: '1', '2'";
    assertEquals("Unnest{col='name', into=map, idx=['1', '2']}", runAndPrint(ruleCode));
  }

  @Test
  public void splitRowsTest() {
    String ruleCode = "splitrows col: column1 on: '-' quote: '\"'";
    assertEquals("SplitRows{col='column1', on='-', quote=''\"''}", runAndPrint(ruleCode));
  }

  //@Test
  //public void unnestTest2() {
  //  String ruleCode = "unnest col: name~speed, weight into: map idx: '1', '2'";
  //  assertEquals("Unnest{col='[name~speed, weight]', into=map, idx=['1', '2']}", runAndPrint(ruleCode));
  //}

  private String runAndPrint(String ruleCode) {

    Rule rule = new RuleVisitorParser().parse(ruleCode);

    ObjectMapper mapper = new ObjectMapper();
    String json = null;
    try {
      json = mapper.writeValueAsString(rule);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.printf("code below: %n '%s' %n has been parsed to object: %n '%s'%n '%s'%n", ruleCode, json, rule.toString());
    return rule.toString();
  }

}