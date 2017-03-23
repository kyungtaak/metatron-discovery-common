package com.skt.metatron.discovery.common.preparation.spec;

import com.google.common.collect.Lists;

import com.esotericsoftware.kryo.util.ObjectMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kyungtaak on 2017. 3. 20..
 */
public class DataflowSpecTest {

  @Test
  public void test() throws JsonProcessingException {
    DataflowSpec spec = new DataflowSpec();

    List<DataflowSpec.RuleByDataSet> ruleSets = Lists.newArrayList();

    DataflowSpec.RuleByDataSet ruleSet = new DataflowSpec.RuleByDataSet();
    ruleSet.setName("sales");
    ruleSet.setTable("sales");
    ruleSet.setRules(Lists.newArrayList(
        "derive value: if(category == 'Furniture', true, false) as: 'cate_if'",
        "drop col: profit"));

    System.out.println(ruleSet.ruleList());

    ruleSets.add(ruleSet);

    spec.setRuleSets(ruleSets);

    System.out.println(new ObjectMapper().writeValueAsString(spec));

  }

}