package cn.craz.shiro.dao;

import cn.craz.shiro.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SysPermissionMapper {
    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    List<String> getPermissionsById(@Param("set") Set ids);
}