package org.example.models.po;

import lombok.Data;

@Data
public class ApprovalForm {
    private Long 序号;
    private String 用户账号;
    private String 创建时间;
    private String 活动;
    private String 操作人;
    private String 审批意见;
}
