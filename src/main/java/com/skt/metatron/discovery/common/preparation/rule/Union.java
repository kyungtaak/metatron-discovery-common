package com.skt.metatron.discovery.common.preparation.rule;

import com.skt.metatron.discovery.common.preparation.rule.expr.Expression;

import java.util.ArrayList;

/**
 * Created by seungunchoe on 2017. 5. 19.
 */
public class Union implements Rule, Rule.Factory {

  Expression masterCol;
  Expression dataset2;
  Expression slaveCol;
  Expression totalCol;

  public Union() {
  }

  public Union(Expression masterCol, Expression dataset2, Expression slaveCol, Expression totalCol) {
    this.masterCol = masterCol;
    this.dataset2 = dataset2;
    this.slaveCol = slaveCol;
    this.totalCol = totalCol;
  }

  public Expression getMasterCol() {
    return masterCol;
  }

  public void setMasterCol(Expression masterCol) {
    this.masterCol = masterCol;
  }

  public Expression getDataset2() {
    return dataset2;
  }

  public void setDataset2(Expression dataset2) {
    this.dataset2 = dataset2;
  }

  public Expression getSlaveCol() {
    return slaveCol;
  }

  public void setSlaveCol(Expression slaveCol) {
    this.slaveCol = slaveCol;
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
        ", masterCol=" + masterCol +
        ", slaveCol=" + slaveCol +
        ", totalCol=" + totalCol +
        '}';
  }
}
