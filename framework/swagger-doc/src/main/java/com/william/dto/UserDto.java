package com.william.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * Created by william on 17-3-24.
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户其本信息")
public class UserDto implements Serializable {
    private static final long serialVersionUID = -5609484630223735411L;

    @ApiModelProperty(required = false, value = "ID")
    @Getter @Setter
    private Long id;
    @ApiModelProperty(required = true, value = "名称")
    @Getter @Setter
    private String username;
    @ApiModelProperty(required = true, value = "角色")
    @Getter @Setter
    private String role;
    @ApiModelProperty(required = true, value = "年龄")
    @Getter @Setter
    private Integer age;
    @ApiModelProperty(required = true, value = "性别")
    @Getter @Setter
    private Integer gendar;

}
