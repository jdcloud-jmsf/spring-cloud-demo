package com.jdcloud.jmesh.demo.springcloud.provider.vo;

import lombok.Data;

import java.util.List;

/**
 * TestRequestVo
 *
 * @author Zhiguo.Chen
 * @version 20210810
 */
@Data
public class TestRequestVo {

    private String userName;

    private String password;

    private RequestChildVo objectParam;

    private List<String> stringListParam;

    private List<RequestChildVo> objectListParam;
}
