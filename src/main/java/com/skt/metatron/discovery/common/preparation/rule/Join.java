package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

/**
 * Created by seungun on 2017. 5. 19.
 */
public class Join implements Rule, Rule.Factory {

  Expression leftSelectCol;
  Expression rigthSelectCol;
  Expression lhs;
  Expression rhs;
  String joinType;

  public Join() {
  }

  public Expression getLeftSelectCol() {
    return leftSelectCol;
  }

  public void setLeftSelectCol(Expression leftSelectCol) {
    this.leftSelectCol = leftSelectCol;
  }

  public Expression getRigthSelectCol() {
    return rigthSelectCol;
  }

  public void setRigthSelectCol(Expression rigthSelectCol) {
    this.rigthSelectCol = rigthSelectCol;
  }

  public Expression getLhs() {
    return lhs;
  }

  public void setLhs(Expression lhs) {
    this.lhs = lhs;
  }

  public Expression getRhs() {
    return rhs;
  }

  public void setRhs(Expression rhs) {
    this.rhs = rhs;
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
        "leftSelectCol=" + leftSelectCol +
        ", lhs=" + lhs +
        ", rigthSelectCol=" + rigthSelectCol +
        ", rhs=" + rhs +
        ", joinType='" + joinType + '\'' +
        '}';
  }
}
