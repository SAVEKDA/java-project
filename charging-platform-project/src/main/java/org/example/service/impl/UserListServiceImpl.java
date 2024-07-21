package org.example.service.impl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.example.mapper.UserListMapper;
import org.example.models.po.ApprovalForm;
import org.example.models.po.ReFundList;
import org.example.models.po.Userdetailinfo;
import org.example.models.po.Userinformation;
import org.example.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserListServiceImpl implements UserListService {
    @Autowired
    private UserListMapper userListMapper;
    @Override
    public List<Userinformation> getList() {
       return userListMapper.list();
    }
    @Override
    public List<Userinformation> selectUserlist(String 用户账号, String 手机号, String 所属运营商, String 归属地区, String 状态) {
        return userListMapper.selectUserlist(用户账号,手机号,所属运营商,归属地区,状态);
    }

    @Override
    public Userinformation findByNumber(String 序号) {
        return userListMapper.findByNumber(序号);
    }

    @Override
    public void changeStatus(String 序号, Userinformation u) {
        if(u.get状态().equals("正常")){
            userListMapper.changeStatusToClose(序号);
        }
        if(u.get状态().equals("已封号")){
            userListMapper.changeStatusToOpen(序号);
        }
    }

    @Override
    public void DeleteUserInfo(String 序号) {
        userListMapper.DeleteUserInfo(序号);
    }

    @Override
    public List<Userinformation> Timedetection(List<Userinformation> list, String 开始时间, String 结束时间) {
        if (开始时间 == null && 结束时间 == null) {
            return list;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        List<Userinformation> list1 = new ArrayList<>();
        if(开始时间 == null && 结束时间 != null){
            for (Userinformation userInfo : list) {
                String dateString = userInfo.get注册时间().split(" ")[0];
                LocalDate date = LocalDate.parse(dateString, formatter);
                LocalDate rangeEnd = LocalDate.parse(结束时间, formatter);
                if(!date.isAfter(rangeEnd)){
                    list1.add(userInfo);
                }
            }
        } else if (开始时间 != null && 结束时间 == null) {
            for (Userinformation userInfo : list) {
                String dateString = userInfo.get注册时间().split(" ")[0];
                LocalDate date = LocalDate.parse(dateString, formatter);
                LocalDate rangeStart = LocalDate.parse(开始时间, formatter);
                if(!date.isBefore(rangeStart)){
                    list1.add(userInfo);
                }
            }
        }else if (开始时间 != null && 结束时间 != null){
            for (Userinformation userInfo : list) {
                String dateString = userInfo.get注册时间().split(" ")[0];
                LocalDate date = LocalDate.parse(dateString, formatter);
                LocalDate rangeStart = LocalDate.parse(开始时间, formatter);
                LocalDate rangeEnd = LocalDate.parse(结束时间, formatter);
                if(!date.isBefore(rangeStart) && !date.isAfter(rangeEnd)){
                    list1.add(userInfo);
                }
            }
        }

        return list1;
    }

    @Override
    public Userdetailinfo GetUserDetail(String 序号) {
        return userListMapper.GetUserDetail(序号);
    }

    @Override
    public List<ReFundList> GetUserRefundForm(String 用户账号) {
        return userListMapper.GetUserRefundForm(用户账号);
    }

    @Override
    public List<ApprovalForm> GetUserApprovalForm(String 用户账号) {
        return userListMapper.GetUserApprovalForm(用户账号);
    }

    @Override
    public List<ReFundList> HandleUserRefundForm() {
        return userListMapper.HandleUserApprovalForm();
    }

    @Override
    public void HandleUserRefund(String 序号, String 审批状态, String 审批人, String 审批时间, String 审批意见, String 用户账号,String 退款金额) {
        if("通过".equals(审批状态)){
            userListMapper.HandleRefundPass(序号,审批状态, 审批人,审批时间,审批意见);
            String 活动 = "审批通过";
            userListMapper.UpdateApprovelForm(用户账号,活动,审批人,审批意见);
            userListMapper.UpdateSum(用户账号,退款金额);
        }
        else if ("拒绝".equals(审批状态)) {
            userListMapper.HandleRefundPass(序号,审批状态, 审批人,审批时间,审批意见);
            String 活动 = "审批拒绝";
            userListMapper.UpdateApprovelForm(用户账号,活动,审批人,审批意见);
        }
    }

}
