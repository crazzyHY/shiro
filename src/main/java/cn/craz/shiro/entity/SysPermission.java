package cn.craz.shiro.entity;

import lombok.Data;

@Data
public class SysPermission {
    private Long id;

    private String name;

    private String type;

    private String url;

    private Long parentId;

    private String parentIds;

    private String permission;

    private Boolean available;


}