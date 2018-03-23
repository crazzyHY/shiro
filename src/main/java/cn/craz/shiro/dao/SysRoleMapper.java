package cn.craz.shiro.dao;

import cn.craz.shiro.entity.SysRole;

import java.util.List;

public interface SysRoleMapper {
    int insert(SysRole record);

    int insertSelective(SysRole record);

    List<SysRole> selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    SysRole getById(Long id);
}