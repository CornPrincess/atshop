package com.lee.gmall.bean;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Data
public class UserInfo implements Serializable {

  private String id;
  private String loginName;
  private String nickName;
  private String passwd;
  private String name;
  private String phoneNum;
  private String email;
  private String headImg;
  private String userLevel;

  @Transient
  List<UserAddress> userAddressList;

}
