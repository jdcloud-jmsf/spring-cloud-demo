package com.jdcloud.jmsf.demo.springcloud.provider.handler;

import com.jdcloud.jmsf.core.entity.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice//(basePackages = "com.jdcloud.jmsf")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResponse globalException(HttpServletResponse response, Exception ex) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        CommonResponse commonResponse = CommonResponse.failResponse(CommonResponse.Code.ERROR.getValue(), "自定义：" + ex.getMessage());
        return commonResponse;
    }

    @ResponseBody
    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonResponse noHandlerFoundException(HttpServletResponse response, NoHandlerFoundException ex) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        CommonResponse commonResponse = CommonResponse.failResponse(CommonResponse.Code.ERROR.getValue(), "自定义：" + ex.getMessage());
        return commonResponse;
    }

}
