package com.skt.metatron.discovery.common.preparation.spec;

import com.clearspring.analytics.util.Lists;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skt.metatron.discovery.common.datasource.FieldType;
import com.skt.metatron.discovery.common.preparation.RuleVisitorParser;
import com.skt.metatron.discovery.common.preparation.rule.Rule;

import org.apache.commons.collections.CollectionUtils;
import org.apache.spark.sql.DataFrame;

import java.io.Serializable;
import java.util.List;
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

    String name;

    String datasetId;

    List<String> rules;

    List<Field> fields;

    String tempTable;

    @JsonIgnore
    DataFrame dataFrame;

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

    public String getTempTable() {
      return tempTable;
    }

    public void setTempTable(String tempTable) {
      this.tempTable = tempTable;
    }

    public DataFrame getDataFrame() {
      return dataFrame;
    }

    public void setDataFrame(DataFrame dataFrame) {
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
  }
}
