package com.atshop.shiro.warreport.mapper;


import com.atshop.shiro.warreport.model.UserRole;
import com.atshop.shiro.warreport.util.MyMapper;

import java.util.List;

public interface UserRoleMapper extends MyMapper<UserRole> {
    public List<Integer> findUserIdByRoleId(Integer roleId);
}