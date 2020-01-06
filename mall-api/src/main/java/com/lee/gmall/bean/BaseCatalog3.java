package com.lee.gmall.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 三级分类表
 */
@Data
public class BaseCatalog3 implements Serializable {

  private String id;
  private String name;
  private String catalog2Id;
}
