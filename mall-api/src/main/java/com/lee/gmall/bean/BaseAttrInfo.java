package com.lee.gmall.bean;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
@Data
public class BaseAttrInfo implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String attrName;
    private String catalog3Id;
    private String isEnabled;

    @Transient
    List<BaseAttrValue> attrValueList;

}
