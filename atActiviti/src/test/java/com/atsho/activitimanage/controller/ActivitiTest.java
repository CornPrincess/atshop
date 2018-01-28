package com.atsho.activitimanage.controller;

import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class ActivitiTest {
    @Test
    public void getDefault(){
        //使用Activiti默认的方式创建ProcessEngineConfiguration
        ProcessEngineConfiguration config = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
    }
}
