package com.skt.metatron.discovery.common.preparation.rule;

/**
 * Created by kyungtaak on 2017. 3. 3..
 */
public class Drop implements Rule, Rule.Factory {

  String col;

  public Drop() {
  }

  public Drop(String col) {
    this.col = col;
  }

  @Override
  public String getName() {
    return "drop";
  }

  public String getCol() {
    return col;
  }

  public void setCol(String col) {
    this.col = col;
  }

  @Override
  public Rule get() {
    return new Drop();
  }
}
