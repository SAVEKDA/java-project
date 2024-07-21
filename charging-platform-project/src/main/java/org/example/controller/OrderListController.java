package org.example.controller;

import org.example.models.po.Result;
import org.example.models.po.Self_Financingorders;
import org.example.models.po.Shareorders;
import org.example.service.BusinessService;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/list")
public class OrderListController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BusinessService businessService;
    @PostMapping("/shareorderlist")
    public Result<List<Shareorders>> shareorders(){
        List<Shareorders> list = orderService.ShareOrderList();
        return Result.success(list);
    }
    @PostMapping("/self-financingorders")
    public Result<List<Self_Financingorders>> selforders(){
       List<Self_Financingorders> list = orderService.SelfOrderList();
       return Result.success(list);
    }
    @PostMapping("/SelectShareorderlist")
    public Result<List<Shareorders>> SelectShareorderlist(
            @RequestParam(required = false) String 订单编号,
            @RequestParam(required = false) String 电桩编号,
            @RequestParam(required = false) String 订单状态,
            @RequestParam(required = false) String 订单类型,
            @RequestParam(required = false) String 用户账号){
        List<Shareorders> list = orderService.SelectShareorderlist(订单编号,电桩编号,订单状态,订单类型,用户账号);
        if(list.size()== 0){
            return Result.error("无符合条件订单!");
        }
        return Result.success(list);
    }
    @PostMapping("/SelectSelforderlist")
    public Result<List<Self_Financingorders>> SelectSelforderlist(
            @RequestParam(required = false) String 订单编号,
            @RequestParam(required = false) String 电桩编号,
            @RequestParam(required = false) String 订单状态,
            @RequestParam(required = false) String 订单类型,
            @RequestParam(required = false) String 用户账号){
        List<Self_Financingorders> list = orderService.SelectSelforderlist(订单编号,电桩编号,订单状态,订单类型,用户账号);
        if(list.size()== 0){
            return Result.error("无符合条件订单!");
        }
        return Result.success(list);
    }
}
