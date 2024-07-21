package org.example.models.po;

import lombok.Data;

@Data
public class ReFundList {
    private Long 序号;
    private String 用户账号;
    private String 退款金额;
    private String 退款前金额;
    private String 退款后金额;
    private String 退款理由;
    private String 申请时间;
    private String 审批状态;
    private String 申请方式;
    private String 审批人;
    private String 审批时间;
    private String 审批意见;
}
