package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.models.po.Self_Financingorders;
import org.example.models.po.Shareorders;

import java.util.List;

@Mapper
public interface OrderListMapper {
    @Select("select * from shareorders")
    List<Shareorders> ShareOrderList();
    @Select("select * from selffinancingorders")
    List<Self_Financingorders> SelfOrderList();
    List<Shareorders> SelectShareorderlist(String 订单编号, String 电桩编号, String 订单状态, String 订单类型, String 用户账号);
    List<Self_Financingorders> SelectSelforderlist(String 订单编号, String 电桩编号, String 订单状态, String 订单类型, String 用户账号);
}
