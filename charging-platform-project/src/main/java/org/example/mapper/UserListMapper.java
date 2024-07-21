package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.models.po.ApprovalForm;
import org.example.models.po.ReFundList;
import org.example.models.po.Userdetailinfo;
import org.example.models.po.Userinformation;

import java.util.List;
@Mapper
public interface UserListMapper {
    @Select("select * from userinformation where deletesign = 0")
    List<Userinformation> list();

    List<Userinformation> selectUserlist(String 用户账号, String 手机号, String 所属运营商, String 归属地区, String 状态);
    @Select("select * from userinformation where 序号 = #{序号} and deletesign = 0")
    Userinformation findByNumber(String 序号);
    @Update("update userinformation set 状态 = '已封号' where 序号 = #{序号} and deletesign = 0")
    void changeStatusToClose(String 序号);
    @Update("update userinformation set 状态 = '正常' where 序号 = #{序号} and deletesign = 0")
    void changeStatusToOpen(String 序号);
    @Update("update userinformation set deletesign = 1 where 序号 = #{序号}")
    void DeleteUserInfo(String 序号);
    @Select("select * from userdetailinfo where 序号 = #{序号}")
    Userdetailinfo GetUserDetail(String 序号);
    @Select("select * from refundlist where 用户账号 = #{用户账号} AND 审批状态 IS NOT NULL AND 审批状态 != ''")
    List<ReFundList> GetUserRefundForm(String 用户账号);
    @Select("select * from approvalform where 用户账号 = #{用户账号}")
    List<ApprovalForm> GetUserApprovalForm(String 用户账号);
    @Select("select * from refundlist where 审批状态 IS NULL OR 审批状态 = ''")
    List<ReFundList> HandleUserApprovalForm();
    @Update("Update refundlist set 审批状态 = #{审批状态} , 审批人 = #{审批人} , 审批时间 = #{审批时间} , 审批意见 = #{审批意见} where 序号 = #{序号}")
    void HandleRefundPass(String 序号, String 审批状态, String 审批人, String 审批时间, String 审批意见);
    @Insert("INSERT INTO approvalform (用户账号,活动,操作人,审批意见) VALUES (#{用户账号},#{活动},#{审批人},#{审批意见})")
    void UpdateApprovelForm(String 用户账号, String 活动 ,String 审批人, String 审批意见);
    @Update("Update userinformation set 余额 = 余额 + #{退款金额}  where 用户账号 = #{用户账号}")
    void UpdateSum(String 用户账号, String 退款金额);
}
