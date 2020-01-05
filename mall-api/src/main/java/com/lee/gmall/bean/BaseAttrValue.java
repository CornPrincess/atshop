package com.lee.gmall.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseAttrValue implements Serializable {
  private String id;
  private String valueName;
  private String attrId;
  private String isEnabled;
}
