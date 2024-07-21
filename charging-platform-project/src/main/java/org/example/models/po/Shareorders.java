package org.example.models.po;

import lombok.Data;

@Data
public class Shareorders {

  private long 序号;
  private String 订单编号;
  private String 订单状态;
  private String 订单类型;
  private String 用户账号;
  private String 所属商户;
  private String 电桩编号;
  private String 充电时长;
  private double 订单金额;
  private double 支付金额;
  private String 创建时间;

}
