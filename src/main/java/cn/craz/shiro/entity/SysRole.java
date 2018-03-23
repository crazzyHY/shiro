package cn.craz.shiro.entity;

import lombok.Data;

@Data
public class SysRole {
    private String id;

    private String role;

    private String description;

    private String resourceIds;

    private Boolean available;


}