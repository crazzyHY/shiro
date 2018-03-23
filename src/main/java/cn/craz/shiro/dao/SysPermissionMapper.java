package cn.craz.shiro.dao;

import cn.craz.shiro.entity.SysPermission;

public interface SysPermissionMapper {
    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}