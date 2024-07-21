package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.models.dto.PageDTO;
import org.example.models.po.Businessinformation;
import org.example.models.po.Result;
import org.example.models.query.BusinessQuery;
import org.example.service.BusinessService;
import org.example.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/list")
@Validated
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @PostMapping("/businesslist")
    public Result<List<Businessinformation>> BusinessinformationList(){
        List<Businessinformation> list = businessService.list();
        return Result.success(list);
    }
    @PostMapping("/AddBusinessinfo")
    public Result<String> AddBusinessinformation(
            @Pattern(regexp = "^\\S{6,16}$",message = "用户账号长度应为6-16位") String 登录账号,
            @Pattern(regexp = "^\\S{6,16}$",message = "用户账号长度应为6-16位") String 账号密码,
            @Pattern(regexp = "^\\d{11}$", message = "联系方式必须是11位数字") String 联系方式,
            @RequestParam(required = false) String 账号类型, @Pattern(regexp = "^(启用|停用)$", message = "只允许取值为 '启用' 或 '停用'") String 状态, String 联系人,@RequestParam(required = false) String 所属代理){
        Businessinformation b = businessService.findByNumber(登录账号);
        if(b==null){
            //将密码使用Md5加密
            String md5String = Md5Util.getMD5String(账号密码);
            businessService.AddInfo(登录账号,md5String,联系方式,账号类型,状态,联系人,所属代理);
            //生成商户ID
            Businessinformation b1 = businessService.findByNumber(登录账号);
            businessService.AddBusinessID(登录账号,businessService.GenerateBusinessID(b1));
            return Result.success();
        }
            return Result.error("账号已存在！");
    }
    @PostMapping("/SelectBusinessinfo")
    public Result<PageDTO<Businessinformation>> SelectBusinessinformation(@RequestBody BusinessQuery query){
        System.out.println("query = " + query);
        return Result.success(businessService.selectBusinessinfo(query));
    }
    @PostMapping("/ResetPassword")
    public Result<String> ResetPassword(String 登录账号){
        Businessinformation b = businessService.findByNumber(登录账号);
        if(b!=null){
            String password = businessService.ResetPassword(登录账号);
            return Result.success("密码已被重置为"+password);
        }
        return Result.error("重置失败!");
    }
    @PostMapping("/EditorBusinessinfo")
    public Result<String> EditorBusinessinfo(String 登录账号,@Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式不正确") String 联系方式,@RequestParam(required = false) String 所属代理,String 联系人,String 账号类型){
        Businessinformation b = businessService.findByNumber(登录账号);
        if(b!=null){
            businessService.EditorBusinessinfo(登录账号,联系方式,所属代理,联系人,账号类型);
            return Result.success("修改成功!");
        }
        return Result.error("账号不存在!");
    }
    @PostMapping("/ChangeBusinessStatus")
    public Result<String> ChangeStatus(String 登录账号){
        Businessinformation b = businessService.findByNumber(登录账号);
        if(b!=null){
            businessService.ChangeStatus(登录账号,b);
            Businessinformation b1 = businessService.findByNumber(b.get账号());
            return Result.success(b1.get启停状态());
        }
        return Result.error("账号不存在!");
    }
}
