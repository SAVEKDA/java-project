package org.example.service.impl;

import org.example.mapper.OrderListMapper;
import org.example.models.po.Self_Financingorders;
import org.example.models.po.Shareorders;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderListMapper orderListMapper;
    @Override
    public List<Shareorders> ShareOrderList() {
        return orderListMapper.ShareOrderList();
    }

    @Override
    public List<Self_Financingorders> SelfOrderList() {
        return orderListMapper.SelfOrderList();
    }

    @Override
    public List<Shareorders> SelectShareorderlist(String 订单编号, String 电桩编号, String 订单状态, String 订单类型, String 用户账号) {
        return orderListMapper.SelectShareorderlist(订单编号,电桩编号,订单状态,订单类型,用户账号);
    }

    @Override
    public List<Self_Financingorders> SelectSelforderlist(String 订单编号, String 电桩编号, String 订单状态, String 订单类型, String 用户账号) {
        return orderListMapper.SelectSelforderlist(订单编号,电桩编号,订单状态,订单类型,用户账号);
    }


}
