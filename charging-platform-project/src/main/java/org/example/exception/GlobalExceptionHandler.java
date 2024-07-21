package org.example.exception;

import org.example.models.po.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//@RestControllerAdvice
public class GlobalExceptionHandler {
 //  @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        String originalString = e.getMessage();
        int index = -1;
        if(originalString!=null)
         index = originalString.indexOf(":");
        if (index != -1) { // 如果找到了冒号
            originalString = originalString.substring(index + 2);// 从冒号空格后的位置开始截取字符串
        }
        return  Result.error(StringUtils.hasLength(originalString)?originalString:"操作失败");
    }

}
