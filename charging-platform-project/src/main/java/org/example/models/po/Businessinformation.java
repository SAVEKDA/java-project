package org.example.models.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "businessinformation")
public class Businessinformation {
  @TableId(value = "序号", type = IdType.AUTO)
  private long 序号;
  @TableField(value = "商户ID")
  private String 商户ID;
  private String 账号;
  private String 账号密码;
  private String 账号类型;
  private String 商户名称;
  private String 运营类型;
  private String 共享类型;
  private String 信用代码;
  private String 身份证号;
  private String 联系人;
  private String 联系方式;
  private String 所属代理;
  private String 创建时间;
  private String 启停状态;
  private String 审批状态;

}
