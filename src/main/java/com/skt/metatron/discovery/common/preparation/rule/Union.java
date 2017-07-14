package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

import java.util.ArrayList;

/**
 * Created by seungunchoe on 2017. 5. 19.
 */
public class Union implements Rule, Rule.Factory {

  Expression leftSelectCol;
  Expression dataset2;
  Expression rightSelectCol;
  Expression totalCol;

  public Union() {
  }

  public Union(Expression leftSelectCol, Expression dataset2, Expression rightSelectCol, Expression totalCol) {
    this.leftSelectCol = leftSelectCol;
    this.dataset2 = dataset2;
    this.rightSelectCol = rightSelectCol;
    this.totalCol = totalCol;
  }

  public Expression getLeftSelectCol() {
    return leftSelectCol;
  }

  public void setLeftSelectCol(Expression leftSelectCol) {
    this.leftSelectCol = leftSelectCol;
  }

  public Expression getDataset2() {
    return dataset2;
  }

  public void setDataset2(Expression dataset2) {
    this.dataset2 = dataset2;
  }

  public Expression getRightSelectCol() {
    return rightSelectCol;
  }

  public void setRightSelectCol(Expression rightSelectCol) {
    this.rightSelectCol = rightSelectCol;
  }

  public Expression getTotalCol() {
    return totalCol;
  }

  public void setTotalCol(Expression totalCol) {
    this.totalCol = totalCol;
  }

  @Override
  public String getName() {
    return "union";
  }

  @Override
  public Rule get() {
    return new Union();
  }

  @Override
  public String toString() {
    return "Union{" +
        "dataset2=" + dataset2 +
        ", leftSelectCol=" + leftSelectCol +
        ", rightSelectCol=" + rightSelectCol +
        ", totalCol=" + totalCol +
        '}';
  }
}
