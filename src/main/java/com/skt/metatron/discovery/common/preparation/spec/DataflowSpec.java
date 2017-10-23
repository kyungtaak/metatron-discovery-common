package com.skt.metatron.discovery.common.preparation.spec;

import com.google.common.collect.Maps;

import com.clearspring.analytics.util.Lists;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skt.metatron.discovery.common.datasource.FieldType;
import com.skt.metatron.discovery.common.preparation.RuleVisitorParser;
import com.skt.metatron.discovery.common.preparation.rule.Rule;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.spark.sql.Dataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Dataflow 표현하는 Spec. 정의
 *
 */
public class DataflowSpec implements Serializable {

  /**
   * Dataset 별 Rule 정의 내역 정의
   */
  List<RuleByDataSet> ruleSets;

  /**
   * 최종 결과 테이블 정의
   */
  List<ResultTable> resultTables;

  public DataflowSpec() {
  }

  public void addRuleSet(RuleByDataSet ruleByDataSet) {
    if(ruleSets == null) {
      ruleSets = Lists.newArrayList();
    }

    ruleSets.add(ruleByDataSet);
  }

  public void addResultTable(ResultTable resultTable) {
    if(resultTables == null) {
      resultTables = Lists.newArrayList();
    }

    resultTables.add(resultTable);
  }

  public List<RuleByDataSet> getRuleSets() {
    return ruleSets;
  }

  public void setRuleSets(List<RuleByDataSet> ruleSets) {
    this.ruleSets = ruleSets;
  }

  public List<ResultTable> getResultTables() {
    return resultTables;
  }

  public void setResultTables(List<ResultTable> resultTables) {
    this.resultTables = resultTables;
  }

  /**
   * DataSet 별 Rule 정의, Temp Table 지정
   */
  public static class RuleByDataSet {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleByDataSet.class);

    String name;

    String datasetId;

    List<String> rules;

    List<Field> fields;

    String table;

    @JsonIgnore
    Object dataFrame;

    public RuleByDataSet() {
    }

    public List<Rule> parsedRules() {

      if(CollectionUtils.isEmpty(rules)) {
        return Lists.newArrayList();
      }

      final RuleVisitorParser parser = new RuleVisitorParser();

      return rules.stream()
          .map(ruleScript -> parser.parse(ruleScript))
          .collect(Collectors.toList());
    }

    public List<Map<String, String>> ruleList() {

      List<Map<String, String>> resultRules = Lists.newArrayList();

      List<Rule> rules = parsedRules();
      for(Rule rule : rules) {
        Map<String, String> argMap = Maps.newLinkedHashMap();

        PropertyDescriptor[] descriptors =  PropertyUtils.getPropertyDescriptors(rule);
        for(PropertyDescriptor descriptor : descriptors) {
          String argName = descriptor.getName();
          if("class".equals(argName)) {
            continue;
          }

          try {
            argMap.put(argName, PropertyUtils.getProperty(rule, argName).toString());
          } catch (Exception e) {
            LOGGER.warn("Fail to get property({}) value.", argName);
            continue;
          }
        }

        resultRules.add(argMap);
      }

      return resultRules;
    }


    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDatasetId() {
      return datasetId;
    }

    public void setDatasetId(String datasetId) {
      this.datasetId = datasetId;
    }

    public List<String> getRules() {
      return rules;
    }

    public void setRules(List<String> rules) {
      this.rules = rules;
    }

    public List<Field> getFields() {
      return fields;
    }

    public void setFields(List<Field> fields) {
      this.fields = fields;
    }

    public String getTable() {
      return table;
    }

    public void setTable(String table) {
      this.table = table;
    }

    public Object getDataFrame() {
      return dataFrame;
    }

    public void setDataFrame(Object dataFrame) {
      this.dataFrame = dataFrame;
    }
  }

  public static class Field {
    /**
     * Field Name
     */
    String name;

    /**
     * Field Data Type
     */
    FieldType type;

    public Field() {
    }

    public Field(String name, FieldType type) {
      this.name = name;
      this.type = type;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public FieldType getType() {
      return type;
    }

    public void setType(FieldType type) {
      this.type = type;
    }
  }

  /**
   * SQL Builder 내에서 지정한 Result Table 정보 저장
   */
  public static class ResultTable {

    String name;

    String sql;

    String schema;

    String table;

    /**
     * 컬럼 원본명 이 Key 가 되고 Alias 값이 Value 로 셋팅
     */
    Map<String, String> columns;

    public ResultTable() {
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getSql() {
      return sql;
    }

    public void setSql(String sql) {
      this.sql = sql;
    }

    public String getSchema() {
      return schema;
    }

    public void setSchema(String schema) {
      this.schema = schema;
    }

    public String getTable() {
      return table;
    }

    public void setTable(String table) {
      this.table = table;
    }

    public Map<String, String> getColumns() {
      return columns;
    }

    public void setColumns(Map<String, String> columns) {
      this.columns = columns;
    }
  }
}
