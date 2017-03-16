package com.skt.metatron.discovery.common.preparation.spec;

import com.skt.metatron.discovery.common.datasource.FieldType;

import org.apache.spark.sql.DataFrame;

import java.io.Serializable;
import java.util.List;

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
  public class RuleByDataSet {

    String id;

    List<String> rules;

    List<Field> fields;

    String tempTable;

    transient DataFrame dataFrame;

    public RuleByDataSet() {
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
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

  public class Field {
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
  public class ResultTable {

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
