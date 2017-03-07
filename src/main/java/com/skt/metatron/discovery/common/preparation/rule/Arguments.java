package com.skt.metatron.discovery.common.preparation.rule;

/**
 * Created by kyungtaak on 2017. 3. 3..
 */
public class Arguments {

  String name;

  Object value;

  public Arguments() {
  }

  public Arguments(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

}
