package com.atsho.activitimanage.controller;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActivitiTest {
    @Test
    public void getDefault() {
        //使用Activiti默认的方式创建ProcessEngineConfiguration
        ProcessEngineConfiguration config = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
        int i = 0;
    }

    @Test
    public void getAssignedConfig() {
        // 指定配置文件创建ProcessEngineConfiguration
        ProcessEngineConfiguration config = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("my-activiti1.xml");
    }

    @Test
    public void getAssignedConfig2() {
        // 指定配置文件创建ProcessEngineConfiguration
        ProcessEngineConfiguration config = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource(
                        "my-activiti2.xml", "processEngineConfiguration");
        System.out.println(config.getProcessEngineName());

    }

    @Test
    public void getInterceptor() {
        ProcessEngines.getDefaultProcessEngine();
        // 创建Activiti配置对象
        ProcessEngineConfiguration config = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
//                .createProcessEngineConfigurationFromResource("my-config.xml");
        // 初始化流程引擎
        ProcessEngine engine = config.buildProcessEngine();
        // 部署一个最简单的流程
        engine.getRepositoryService().createDeployment()
                .addClasspathResource("bpmn/config.bpmn20.xml").deploy();
        // 构建流程参数
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("day", 10);
        // 开始流程
        engine.getRuntimeService().startProcessInstanceByKey("vacationProcess",
                vars);
        System.exit(0);


    }

    @Test
    public void getEngineFromConfig() {
        // 读取配置
        ProcessEngineConfiguration config = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("my-activiti1.xml");
        // 创建ProcessEngine
        ProcessEngine engine = config.buildProcessEngine();
        System.out.println(engine.getName());
    }

    @Test
    public void initEngine() {
        // 初始化ProcessEngines的Map,
        // 再加载Activiti默认的配置文件（classpath下的activiti.cfg.xml文件）
        // 如果与Spring整合，则读取classpath下的activiti-context.xml文件
        ProcessEngines.init();
        // 得到ProcessEngines的Map
        Map<String, ProcessEngine> engines = ProcessEngines.getProcessEngines();
        System.out.println(engines.size());
        System.out.println(engines.get("default"));
    }

    @Test
    public void registerEngine() {
//读取自定义配置
        ProcessEngineConfiguration config = ProcessEngineConfiguration.
                createProcessEngineConfigurationFromResource("my-activiti1.xml");
        //创建ProcessEngine实例
        ProcessEngine engine = config.buildProcessEngine();
        //获取ProcessEngine的Map
        Map<String, ProcessEngine> engines = ProcessEngines.getProcessEngines();
        System.out.println("注册后引擎数：" + engines.size());
        //注销ProcessEngine实例
        ProcessEngines.unregister(engine);
        System.out.println("调用unregister后引擎数：" + engines.size());

    }

    @Test
    public void destroyEngine() {
        // 进行初始化并且返回默认的ProcessEngine实例
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        System.out.println("调用getDefaultProcessEngine方法后引擎数量："
                + ProcessEngines.getProcessEngines().size());
        // 调用销毁方法
        ProcessEngines.destroy();
        // 最终结果为0
        System.out.println("调用destroy方法后引擎数量："
                + ProcessEngines.getProcessEngines().size());

        // 得到资源文件的URL实例
        ClassLoader cl = ActivitiTest.class.getClassLoader();
        URL url = cl.getResource("activiti.cfg.xml");
        // 调用retry方法创建ProcessEngine实例
        ProcessEngines.retry(url.toString());
        System.out.println("只调用 retry方法后引擎数量："
                + ProcessEngines.getProcessEngines().size());
        // 调用销毁方法，没有效果
        ProcessEngines.destroy();
        System.out.println("调用destory无效果，引擎数量："
                + ProcessEngines.getProcessEngines().size());
    }

    @Test
    public void count() {
        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到身份服务组件实例
        IdentityService identityService = engine.getIdentityService();
        // 写入5条用户组数据
        createGroup(identityService, UUID.randomUUID().toString(), "GroupA", "typeA");
        createGroup(identityService, UUID.randomUUID().toString(), "GroupB", "typeB");
        createGroup(identityService, UUID.randomUUID().toString(), "GroupC", "typeC");
        createGroup(identityService, UUID.randomUUID().toString(), "GroupD", "typeD");
        createGroup(identityService, UUID.randomUUID().toString(), "GroupE", "typeE");
        // 使用list方法查询全部的部署数据
        long size = identityService.createGroupQuery().count();
        System.out.println("Group 数量：" + size);
    }

    // 将用户组数据保存到数据库中
    static void createGroup(IdentityService identityService, String id,
                            String name, String type) {
        // 调用newGroup方法创建Group实例
        Group group = identityService.newGroup(id);
        group.setName(name);
        group.setType(type);
        identityService.saveGroup(group);
    }


    @Test
    public void addGroup(){
        // 创建默认的流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到身份服务组件实例
        IdentityService identityService = engine.getIdentityService();
        // 生成UUID
        String genId = UUID.randomUUID().toString();
        //调用newGroup方法创建Group实例
        Group group = identityService.newGroup(genId);
        group.setName("经理组");
        group.setType("manager");
        //保存Group到数据库
        identityService.saveGroup(group);
        // 查询用户组
        Group data = identityService.createGroupQuery().groupId(genId).singleResult();
        // 保存后查询Group
        System.out.println("Group ID：" + data.getId() + ", Name：" + data.getName());
//		// 设置名称
//		data.setName("经理2组");
//		identityService.saveGroup(data);
//		// 再次查询
//		data = identityService.createGroupQuery().groupId(genId).singleResult();
//		System.out.println("Group ID：" + data.getId() + ", Name：" + data.getName());
    }

    @Test
    public void delGroup(){
        // 创建默认的流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到身份服务组件实例
        IdentityService identityService = engine.getIdentityService();
        String genId = UUID.randomUUID().toString();
        // 调用newGroup方法创建Group实例
        Group group = identityService.newGroup(genId);
        group.setName("经理组");
        group.setType("manager");
        // 保存Group到数据库
        identityService.saveGroup(group);
        // 查询用户组
        System.out.println("保存后用户组数量："
                + identityService.createGroupQuery().count());
        // 根据ID删除用户组
        identityService.deleteGroup(genId);
        System.out.println("删除后用户组数量："
                + identityService.createGroupQuery().count());
    }
}
