package org.example.models.query;

import lombok.Data;

@Data
public class BusinessQuery extends PageQuery{
    private String 商户名称;
    private String 账号类型;
    private String 运营类型;
    private String 共享类型;
    private String 启停状态;
    private String 审批状态;
    private String 开始时间;
    private String 结束时间;

}
