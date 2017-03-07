package com.skt.metatron.discovery.common.preparation.rule;

/**
 *
 */
public class Header implements Rule, Rule.Factory {

  Long rownum;

  public Header() {
  }

  public Header(Long rownum) {
    this.rownum = rownum;
  }

  @Override
  public String getName() {
    return "header";
  }

  public Long getRownum() {
    return rownum;
  }

  public void setRownum(Long rownum) {
    this.rownum = rownum;
  }

  @Override
  public Rule get() {
    return new Header();
  }
}
