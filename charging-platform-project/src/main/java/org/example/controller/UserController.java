package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.models.po.Result;
import org.example.models.po.Userregister;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @PostMapping("/register")
    public Result register (@Pattern(regexp = "^\\S{5,16}$",message = "用户账号长度应为5-16位") String username,@Pattern(regexp = "^\\S{5,16}$",message = "用户密码长度应为5-16位")  String password){

            Userregister u = userService.findByUserName(username);
            if(u == null){
                userService.register(username,password);
                return Result.success();
            }else {
                return Result.error("用户名已被占用");
            }


    }
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,11}$",message = "用户账号长度应为5-11位") String username,@Pattern(regexp = "^\\S{5,16}$",message = "用户密码长度应为5-16位")  String password){
           Userregister u = userService.findByUserName(username);
           if(u==null){
               return Result.error("用户名错误");
           }
           if(Md5Util.getMD5String(password).equals(u.get用户密码())){
               Map<String,Object> claims = new HashMap<>();
               claims.put("username",u.get用户账号());
               String token = JwtUtil.genToken(claims);
               ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
               operations.set(token,token,1, TimeUnit.DAYS);
               return Result.success(token);
           }
           return Result.error("密码错误");
    }
}
