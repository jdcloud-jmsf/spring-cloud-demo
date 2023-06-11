package com.jdcloud.jmsf.demo.springcloud.consumer.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * RequestVo
 *
 * @author Zhiguo.Chen
 * @version 20210814
 */
@Data
public class RequestVo implements Serializable {

    private String name;

    private String password;

    private int age;

}
