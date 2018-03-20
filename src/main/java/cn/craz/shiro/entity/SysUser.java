package cn.craz.shiro.entity;

import lombok.Data;

@Data

public class SysUser {
    private Long id;

    private Long organizationId;

    private String username;

    private String password;

    private String salt;

    private String roleIds;

    private Boolean locked;


    public String getCredentialsSalt() {
        return username + salt;
    }
}