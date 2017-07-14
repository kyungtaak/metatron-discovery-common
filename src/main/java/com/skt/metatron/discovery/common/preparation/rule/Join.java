package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by seungunchoe on 2017. 5. 19.
 */
public class Join implements Rule, Rule.Factory {

  Expression dataset2;
  Expression leftSelectCol;
  Expression rightSelectCol;
  Expression condition;
  String joinType;

  public Join() {
  }

  public Join(Expression dataset2, Expression leftSelectCol, Expression rightSelectCol, Expression condition, String joinType) {
    this.dataset2 = dataset2;
    this.leftSelectCol = leftSelectCol;
    this.rightSelectCol = rightSelectCol;
    this.condition = condition;
    this.joinType = joinType;
  }

  public Expression getDataset2() {
    return dataset2;
  }

  public void setDataset2(Expression dataset2) {
    this.dataset2 = dataset2;
  }

  public Expression getLeftSelectCol() {
    return leftSelectCol;
  }

  public void setLeftSelectCol(Expression leftSelectCol) {
    this.leftSelectCol = leftSelectCol;
  }

  public Expression getRightSelectCol() {
    return rightSelectCol;
  }

  public void setRightSelectCol(Expression rightSelectCol) {
    this.rightSelectCol = rightSelectCol;
  }

  public Expression getCondition() {
    return condition;
  }

  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  public String getJoinType() {
    return joinType;
  }

  public void setJoinType(String joinType) {
    this.joinType = joinType;
  }

  @Override
  public String getName() {
    return "join";
  }

  @Override
  public Rule get() {
    return new Join();
  }

  @Override
  public String toString() {
    return "Join{" +
        "dataset2=" + dataset2 +
        ", leftSelectCol=" + leftSelectCol +
        ", condition=" + condition +
        ", rightSelectCol=" + rightSelectCol +
        ", joinType='" + joinType + '\'' +
        '}';
  }
}
