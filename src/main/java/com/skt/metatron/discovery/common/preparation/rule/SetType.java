package com.skt.metatron.discovery.common.preparation.rule;

/**
 * Created by kyungtaak on 2017. 3. 3..
 */
public class SetType implements Rule, Rule.Factory {
  String col;
  String type;

  public SetType() {
  }

  public SetType(String col, String type) {
    this.col = col;
    this.type = type;
  }

  @Override
  public String getName() {
    return "settype";
  }

  public String getCol() {
    return col;
  }

  public void setCol(String col) {
    this.col = col;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public Rule get() {
    return new SetType();
  }
}
