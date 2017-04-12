package com.skt.metatron.discovery.common.preparation.rule;

/**
 * Created by kyungtaak on 2017. 3. 3..
 */
public class Flatten implements Rule, Rule.Factory {
  /**
   * 대상 컬럼명 (Array 타입 한정)
   */
  String col;

  public Flatten() {
  }

  public Flatten(String col) {
    this.col = col;
  }

  public String getCol() {
    return col;
  }

  public void setCol(String col) {
    this.col = col;
  }

  @Override
  public String getName() {
    return "flatten";
  }


  @Override
  public Rule get() {
    return new Flatten();
  }

  @Override
  public String toString() {
    return "Flatten{" +
        "col='" + col + '\'' +
        '}';
  }
}
