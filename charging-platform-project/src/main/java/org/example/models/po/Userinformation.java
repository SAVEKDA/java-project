package org.example.models.po;

import lombok.Data;

@Data
public class Userinformation {

  private long 序号;
  private String 用户账号;
  private String 用户昵称;
  private String 手机号;
  private String 所属运营商;
  private String 归属地区;
  private String 车牌号;
  private String 余额;
  private String 状态;
  private String 注册时间;

}
