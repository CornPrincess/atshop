package com.atshop.shiro.warreport.mapper;


import com.atshop.shiro.warreport.model.Role;
import com.atshop.shiro.warreport.util.MyMapper;

import java.util.List;

public interface RoleMapper extends MyMapper<Role> {
    public List<Role> queryRoleListWithSelected(Integer id);
}