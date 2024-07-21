package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.models.po.*;
import org.example.service.BusinessService;
import org.example.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/list")
public class UserListController {
    @Autowired
    private UserListService userListService;
    @Autowired
    private BusinessService businessService;
    @PostMapping("/UserList")
    public Result<List<Userinformation>> userlist(){
         List<Userinformation> list = userListService.getList();
         return Result.success(list);
    }
    @PostMapping("/SelectUserList")
    public Result<Map<String, Object>> selectUserlist(
            @RequestParam(required = false) String 用户账号,
            @RequestParam(required = false) String 手机号,
            @RequestParam(required = false) String 所属运营商,
            @RequestParam(required = false) String 归属地区,
            @RequestParam(required = false) @Pattern(regexp = "^(正常|已封号)$", message = "状态只允许取值为 '正常' 或 '已封号'") String 状态,
            @RequestParam(required = false) String 开始时间,
            @RequestParam(required = false) String 结束时间,
            @RequestParam(required = true) String pageSize,
            @RequestParam(required = true) String currentPage
    ) {
        List<Userinformation> list = userListService.selectUserlist(用户账号, 手机号, 所属运营商, 归属地区, 状态);
        list = userListService.Timedetection(list, 开始时间, 结束时间);

        // 转换分页参数为整数
        int IpageSize = Integer.valueOf(pageSize);
        int IcurrentPage = Integer.valueOf(currentPage);

        // 计算分页数据
        int startIndex = (IcurrentPage - 1) * IpageSize;
        int endIndex = Math.min(startIndex + IpageSize, list.size());

        // 分页截取结果
        List<Userinformation> paginatedList = list.subList(startIndex, endIndex);

        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", list.size());
        result.put("list", paginatedList);
        return Result.success(result);
    }
    @PostMapping("/ChangeUserStatus")
    public Result<String> ChangeStatus(String 序号){
        Userinformation u = userListService.findByNumber(序号);
        if(u!=null){
            userListService.changeStatus(序号,u);
            Userinformation u1 = userListService.findByNumber(String.valueOf(u.get序号()));
            return Result.success(u1.get状态());
        }
        return Result.error("用户不存在!");
    }
    @PostMapping("/GetUserDetails")
    public Result<Userdetailinfo> GetDetail (String 序号){
        Userdetailinfo u = userListService.GetUserDetail(序号);
        if(u!=null){
           return Result.success(u);
        }else {
           return Result.error("操作失败！");
        }
    }
    @PostMapping("/GetUserRefundForm")
    public Result<Map<String,Object>> GetRefundForm (String 用户账号,String pageSize,String currentPage) {
        List<ReFundList> list = userListService.GetUserRefundForm(用户账号);
        // 转换分页参数为整数
        int IpageSize = Integer.valueOf(pageSize);
        int IcurrentPage = Integer.valueOf(currentPage);
        // 计算分页数据
        int startIndex = (IcurrentPage - 1) * IpageSize;
        int endIndex = Math.min(startIndex + IpageSize, list.size());
        // 分页截取结果
        List<ReFundList> paginatedList = list.subList(startIndex, endIndex);
        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", list.size());
        result.put("list", paginatedList);
        return Result.success(result);
    }
    @PostMapping("/GetApprovalForm")
    public Result<Map<String,Object>> GetApprovalForm (String 用户账号,String pageSize,String currentPage) {
        List<ApprovalForm> list = userListService.GetUserApprovalForm(用户账号);
        // 转换分页参数为整数
        int IpageSize = Integer.valueOf(pageSize);
        int IcurrentPage = Integer.valueOf(currentPage);
        // 计算分页数据
        int startIndex = (IcurrentPage - 1) * IpageSize;
        int endIndex = Math.min(startIndex + IpageSize, list.size());
        // 分页截取结果
        List<ApprovalForm> paginatedList = list.subList(startIndex, endIndex);
        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", list.size());
        result.put("list", paginatedList);
        return Result.success(result);
    }
    @PostMapping("/HandleUserRefundForm")
    public Result<List<ReFundList>> HandleUserRefundForm () {
        List<ReFundList> list = userListService.HandleUserRefundForm();
        return Result.success(list);
    }
    @PostMapping("/HandleUserRefundPass")
    public Result<String> HandleUserRefund (String 序号,
                                            @Pattern(regexp = "^(通过|拒绝)$", message = "审批状态只允许取值为 '通过' 或 '拒绝'") String 审批状态,
                                            String 审批人,String 审批意见,String 用户账号,String 退款金额) {
        // 获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化日期时间为字符串
        String 审批时间 = currentDateTime.format(formatter);
        userListService.HandleUserRefund(序号,审批状态,审批人,审批时间,审批意见,用户账号,退款金额);
        if("通过".equals(审批状态)) {
            return Result.success("审批通过");
        }
        else
            return Result.success("审批拒绝");

    }
    @PostMapping("/DeleteUserInfo")
    public Result<String> DeleteUserInfo(String 序号){
        Userinformation u = userListService.findByNumber(序号);
        double sum = Double.parseDouble(u.get余额());
        if(u!=null){
            if(sum==0){
                userListService.DeleteUserInfo(序号);
                return Result.success("删除成功!");
            }
            return Result.error("该用户当前无法删除!");
        }
        return Result.error("账号不存在!");
    }

}
