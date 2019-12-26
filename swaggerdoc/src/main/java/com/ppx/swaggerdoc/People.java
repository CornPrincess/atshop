package com.ppx.swaggerdoc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class People {
    @ApiModelProperty(value = "defaultOutputStr", example = "mockOutputStrValue")
    private String name;
    @ApiModelProperty(value = "defaulnameOutputStr", example = "mockOutputStrValue")
    private String name2;
    @ApiModelProperty(value = "defaulnamenametOutputStr", example = "mockOutputStrValue")
    private String name3;
}
