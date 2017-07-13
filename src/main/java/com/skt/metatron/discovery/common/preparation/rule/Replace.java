package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Constant;
import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by seungunchoe on 2017. 3. 5..
 */
public class Replace implements Rule, Rule.Factory {

  /**
   * 복수개 가능 대상
   *
   */
  Expression col;

  /**
   * 대치하려는 패턴(RegularExpr)/문자열(StringExpr)
   *
   */
  Expression on;

  /**
   * 패턴/문자열에 일치한 위치 이후
   *
   */
  Expression after;

  /**
   * 패턴/문자열에 일치한 위치 이전
   *
   */
  Expression before;

  /**
   * 대치하려는 값 (Constant 형태)
   *
   */
  Constant with;

  /**
   * if true, match all occurences
   *
   */
  Boolean global;

  /**
   * 적용할 row 조건
   *
   */
  Expression row;


  public Replace() {
  }

  public Replace(Expression col, Expression on, Expression after, Expression before, Constant with, Boolean global, Expression row) {
    this.col = col;
    this.on = on;
    this.after = after;
    this.before = before;
    this.with = with;
    this.global = global;
    this.row = row;
  }

  @Override
  public String getName() {
    return "replace";
  }

  @Override
  public Rule get() {
    return new Replace();
  }

  public Expression getCol() {
    return col;
  }

  public void setCol(Expression col) {
    this.col = col;
  }

  public Expression getOn() {
    return on;
  }

  public void setOn(Expression on) {
    this.on = on;
  }

  public Expression getAfter() {
    return after;
  }

  public void setAfter(Expression after) {
    this.after = after;
  }

  public Expression getBefore() {
    return before;
  }

  public void setBefore(Expression before) {
    this.before = before;
  }

  public Constant getWith() {
    return with;
  }

  public void setWith(Constant with) {
    this.with = with;
  }

  public Boolean getGlobal() {
    return global;
  }

  public void setGlobal(Boolean global) {
    this.global = global;
  }

  public Expression getRow() {
    return row;
  }

  public void setRow(Expression row) {
    this.row = row;
  }

  @Override
  public String toString() {
    return "Replace{" +
        "col=" + col +
        ", on=" + on +
        ", after=" + after +
        ", before=" + before +
        ", with=" + with +
        ", global=" + global +
        ", row=" + row +
        '}';
  }
}
