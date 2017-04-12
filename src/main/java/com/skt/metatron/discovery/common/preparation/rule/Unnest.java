package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Constant;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public class Unnest implements Rule, Rule.Factory {

  /**
   * Unnest 할 대상 필드 (1개)
   *
   */
  String col;

  /**
   * 필드 생성기준이 되는 key 명 (Nested Key, Array 인 경우 Index 정의 ex. key[key], [0])
   *
   */
  Constant keys;

  /**
   * (Optional) 필드명에 Unnest 대상필드명을 붙이는지 여부 ex col_key
   */
  Boolean markLineage;

  /**
   * (Optional) Remove Original
   */
  Boolean pluck;


  public Unnest() {
  }

  public Unnest(String col, Constant keys, Boolean markLineage, Boolean pluck) {
    this.col = col;
    this.keys = keys;
    this.markLineage = markLineage;
    this.pluck = pluck;
  }

  public void setCol(String col) {
    this.col = col;
  }

  public Constant getKeys() {
    return keys;
  }

  public void setKeys(Constant keys) {
    this.keys = keys;
  }

  public Boolean getMarkLineage() {
    return markLineage;
  }

  public void setMarkLineage(Boolean markLineage) {
    this.markLineage = markLineage;
  }

  public Boolean getPluck() {
    return pluck;
  }

  public void setPluck(Boolean pluck) {
    this.pluck = pluck;
  }

  @Override
  public String getName() {
    return "unnest";
  }

  @Override
  public Rule get() {
    return new Unnest();
  }

  @Override
  public String toString() {
    return "Unnest{" +
        "col='" + col + '\'' +
        ", keys=" + keys +
        ", markLineage=" + markLineage +
        ", pluck=" + pluck +
        '}';
  }
}
