package com.william.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by william on 2017/12/3.
 */
@Data
@Builder
@AllArgsConstructor
public class User {

    @NotBlank
    private String username;
    private Integer age;
    private Date    birthday;
    private List<String> hibats;
    private Set<String>  tags;

}
