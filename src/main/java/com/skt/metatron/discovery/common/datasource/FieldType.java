package com.skt.metatron.discovery.common.datasource;

import org.apache.spark.sql.types.*;

/**
 * Created by kyungtaak on 2017. 3. 16..
 */
public enum FieldType {
  TEXT,
  BOOLEAN,
  INT,
  LONG,
  FLOAT,
  DOUBLE,
  TIMESTAMP,
  LNG,
  LNT,
  ARRAY,
  MAP,
  MAP_KEY,
  MAP_VALUE;


  public String toSqlDataType() {
    switch (this) {
      case TEXT:
        return "VARCHAR(500)";
      case LONG:
        return "INT";
      default:
        return this.name();
    }
  }

  public static FieldType sparkTypeToFieldType(DataType type) {

    if(type instanceof TimestampType) {
      return TIMESTAMP;
    } else if(type instanceof BooleanType) {
      return BOOLEAN;
    } else if(type instanceof ByteType || type instanceof IntegerType) {
      return INT;
    } else if(type instanceof LongType) {
      return LONG;
    } else if(type instanceof FloatType) {
      return FLOAT;
    } else if(type instanceof DoubleType) {
      return DOUBLE;
    } else if(type instanceof ArrayType) {
      return ARRAY;
    } else if(type instanceof MapType) {
      return ARRAY;
    } else {
      return TEXT;
    }
  }
}
