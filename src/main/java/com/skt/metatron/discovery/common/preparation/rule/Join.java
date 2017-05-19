package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by seungun on 2017. 5. 19.
 */
public class Join implements Rule, Rule.Factory {

  Expression left;
  Expression rigth;
  Expression condition;
  String joinType;

  public Join() {
  }

  public Expression getLeft() {
    return left;
  }

  public void setLeft(Expression left) {
    this.left = left;
  }

  public Expression getRigth() {
    return rigth;
  }

  public void setRigth(Expression rigth) {
    this.rigth = rigth;
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
        "left=" + left +
        ", right=" + rigth +
        ", condition=" + condition +
        ", joinTye='" + joinType + '\'' +
        '}';
  }
}
