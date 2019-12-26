package com.ppx.swaggerdoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
@Api(value = "用户信息管理")
public class RestController {
    @ApiOperation(value = "获取用户信息", notes = "通过用户ID获取用户信息")
    @GetMapping("/test")
    public People getUser(@ApiParam(value = "用户ID",required = true) int id) {
        return new People();
    }
}
