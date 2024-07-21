package org.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.BusinessMapper;
import org.example.models.dto.PageDTO;
import org.example.models.po.Businessinformation;

import org.example.models.query.BusinessQuery;
import org.example.service.BusinessService;
import org.example.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper,Businessinformation> implements BusinessService {
    @Autowired
    private BusinessMapper businessMapper;
    @Override
    public List<Businessinformation> list() {
        return businessMapper.list();
    }
    @Override
    public void AddInfo(String 登录账号, String 账号密码, String 联系方式, String 账号类型, String 状态, String 联系人, String 所属代理){businessMapper.AddInfo(登录账号,账号密码,联系方式,账号类型,状态,联系人,所属代理);}

    @Override
    public Businessinformation findByNumber(String 登录账号) {
        return businessMapper.findByNumber(登录账号);
    }

    @Override
    public void AddBusinessID(String 登录账号, String businessID) {
        businessMapper.AddBusinessID(登录账号,businessID);
    }

    @Override
    public String GenerateBusinessID(Businessinformation businessinformation) {
        int number= (int) businessinformation.get序号();
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear() % 100;  // 获取年份的后两位
        int month = currentDate.getMonthValue();  // 获取月份
        int day = currentDate.getDayOfMonth();    // 获取日期
        String formattedDate = String.format("%02d%02d%02d", year, month, day);
        String formattedNumber = String.format("%04d", number);
        String BusinessID = formattedDate + formattedNumber;
        return BusinessID;
    }
    @Override
    public PageDTO<Businessinformation> selectBusinessinfo(BusinessQuery query) {
        Page<Businessinformation> page = Page.of(query.getPageNo(), query.getPageSize());
        if(StrUtil.isNotBlank(query.getSortBy())){
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(query.getSortBy());
            orderItem.setAsc(query.getIsAsc());
            page.addOrder(orderItem);
        }else {
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn("创建时间");
            orderItem.setAsc(false);
            page.addOrder(orderItem);
        }
        Page<Businessinformation> p = lambdaQuery()
                .like(query.get商户名称()!= null && query.get商户名称()!= "",Businessinformation::get商户名称,query.get商户名称())
                .eq(query.get账号类型()!= null && query.get账号类型()!= "",Businessinformation::get账号类型,query.get账号类型())
                .eq(query.get运营类型()!= null && query.get运营类型()!= "",Businessinformation::get运营类型,query.get运营类型())
                .eq(query.get共享类型()!= null && query.get共享类型()!= "",Businessinformation::get共享类型,query.get共享类型())
                .eq(query.get启停状态()!= null && query.get启停状态()!= "",Businessinformation::get启停状态,query.get启停状态())
                .eq(query.get审批状态()!= null && query.get审批状态()!= "",Businessinformation::get审批状态,query.get审批状态())
                .ge(query.get开始时间()!= null && query.get开始时间()!= "",Businessinformation::get创建时间,query.get开始时间())
                .le(query.get结束时间()!= null && query.get结束时间()!= "",Businessinformation::get创建时间,query.get结束时间())
                .page(page);
        PageDTO<Businessinformation> dto = new PageDTO<>();
        dto.setTotal(p.getTotal());
        dto.setPages(p.getPages());
        List<Businessinformation> records = p.getRecords();
        if(CollUtil.isEmpty(records)){
            dto.setList(Collections.emptyList());
            return dto;
        }else {
            dto.setList(records);
            return dto;
        }
    }

    @Override
    public String ResetPassword(String 登录账号) {
        String password = "Aa1234";
        String Md5Password = Md5Util.getMD5String(password);
        businessMapper.ResetPassword(登录账号,Md5Password);
        return password;
    }

    @Override
    public void EditorBusinessinfo(String 登录账号, String 联系方式, String 所属代理, String 联系人, String 账号类型) {
        businessMapper.EditorBusinessinfo(登录账号,联系方式,所属代理,联系人,账号类型);
    }

    @Override
    public void ChangeStatus(String 登录账号,Businessinformation businessinformation) {
        String status = businessinformation.get启停状态();
        if(status.equals("启用")){
            businessMapper.ChangeStatusToClose(登录账号);
        }
        if(status.equals("停用")){
            businessMapper.ChangeStatusToOpen(登录账号);
        }

    }

}
