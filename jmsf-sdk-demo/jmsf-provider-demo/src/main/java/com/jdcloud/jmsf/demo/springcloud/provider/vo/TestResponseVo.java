package com.jdcloud.jmsf.demo.springcloud.provider.vo;

import lombok.Data;

/**
 * TestVo
 *
 * @author Zhiguo.Chen
 * @version 20210810
 */
@Data
public class TestResponseVo {

    private int code;

    private String message;

    private String data;
}
