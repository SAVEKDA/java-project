package org.example.service;

import org.example.models.dto.PageDTO;
import org.example.models.po.Businessinformation;
import org.example.models.query.BusinessQuery;

import java.util.List;

public interface BusinessService {
    List<Businessinformation> list();
    void AddInfo(String 登录账号, String 账号密码, String 联系方式, String 账号类型, String 状态, String 联系人, String 所属代理);
    Businessinformation findByNumber(String 登录账号);
    void AddBusinessID(String 登录账号, String businessID);
    String GenerateBusinessID(Businessinformation businessinformation);
    PageDTO<Businessinformation> selectBusinessinfo(BusinessQuery query);
    String ResetPassword(String 登录账号);
    void EditorBusinessinfo(String 登录账号, String 联系方式, String 所属代理, String 联系人, String 账号类型);
    void ChangeStatus(String 登录账号,Businessinformation businessinformation);

}
