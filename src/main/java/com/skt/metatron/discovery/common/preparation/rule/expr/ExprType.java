package com.skt.metatron.discovery.common.preparation.rule.expr;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by kyungtaak on 2017. 3. 5..
 */
public enum ExprType {
  DOUBLE, LONG, STRING;

  public static ExprType bestEffortOf(String name) {
    if (StringUtils.isEmpty(name)) {
      return STRING;
    }
    switch (name.toUpperCase()) {
      case "FLOAT":
      case "DOUBLE":
        return DOUBLE;
      case "BYTE":
      case "SHORT":
      case "INT":
      case "INTEGER":
      case "LONG":
      case "BIGINT":
        return LONG;
      default:
        return STRING;
    }
  }
}
