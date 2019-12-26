package com.ppx.swaggerdoc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@ApiModel
public class People {
    @Setter
    @Getter
    @ApiModelProperty(value = "defaultOutputStr", example = "mockOutputStrValue")
    private String name;
    @ApiModelProperty(value = "defaulnameOutputStr", example = "4")
    private Integer name2;
    @ApiModelProperty(value = "defaulnamenametOutputStr", example = "444")
    private Long name3;
}
