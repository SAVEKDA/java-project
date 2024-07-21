package org.example.service;

import org.example.models.po.ApprovalForm;
import org.example.models.po.ReFundList;
import org.example.models.po.Userdetailinfo;
import org.example.models.po.Userinformation;

import java.util.List;

public interface UserListService {
    List<Userinformation> getList();

    List<Userinformation> selectUserlist(String 用户账号, String 手机号, String 所属运营商, String 归属地区, String 状态);

    Userinformation findByNumber(String 序号);

    void changeStatus(String 序号, Userinformation u);

    void DeleteUserInfo(String 序号);

    List<Userinformation> Timedetection(List<Userinformation> list, String 开始时间, String 结束时间);

    Userdetailinfo GetUserDetail(String 序号);

    List<ReFundList> GetUserRefundForm(String 用户账号);

    List<ApprovalForm> GetUserApprovalForm(String 用户账号);

    List<ReFundList> HandleUserRefundForm();

    void HandleUserRefund(String 序号, String 审批状态, String 审批人, String 审批时间, String 审批意见, String 用户账号, String 退款金额);
}
