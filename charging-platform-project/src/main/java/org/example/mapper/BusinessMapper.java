package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.models.po.Businessinformation;

import java.util.List;
@Mapper
public interface BusinessMapper extends BaseMapper<Businessinformation> {

    @Select("select * from businessinformation")
    List<Businessinformation> list();
    @Insert("INSERT businessinformation(账号,账号密码,联系方式,账号类型,启停状态,联系人,所属代理) values (#{登录账号},#{账号密码},#{联系方式},#{账号类型},#{状态},#{联系人},#{所属代理})")
    void AddInfo(String 登录账号, String 账号密码, String 联系方式, String 账号类型, String 状态, String 联系人, String 所属代理);
    @Select("select * from businessinformation where 账号 = #{登录账号}")
    Businessinformation findByNumber(String 登录账号);
    @Update("UPDATE businessinformation set 商户ID = #{businessID} where 账号 = #{登录账号}")
    void AddBusinessID(String 登录账号, String businessID);
    List<Businessinformation> selectBusinessinfo(String 商户名称, String 账号类型, String 运营类型, String 共享类型, String 启停状态, String 审批状态);
    @Update("UPDATE businessinformation set 账号密码 = #{Md5Password} where 账号 = #{登录账号}")
    void ResetPassword(String 登录账号, String Md5Password);

    void EditorBusinessinfo(String 登录账号, String 联系方式, String 所属代理, String 联系人, String 账号类型);
    @Update("UPDATE businessinformation set 启停状态 = '停用' where 账号 = #{登录账号}")
    void ChangeStatusToClose(String 登录账号);
    @Update("UPDATE businessinformation set 启停状态 = '启用' where 账号 = #{登录账号}")
    void ChangeStatusToOpen(String 登录账号);
}
