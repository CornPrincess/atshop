package com.atshop.shiro.warreport.mapper;


import com.atshop.shiro.warreport.model.Resources;
import com.atshop.shiro.warreport.util.MyMapper;

import java.util.List;
import java.util.Map;

public interface ResourcesMapper extends MyMapper<Resources> {

    public List<Resources> queryAll();

    public List<Resources> loadUserResources(Map<String, Object> map);

    public List<Resources> queryResourcesListWithSelected(Integer rid);
}