package com.atsho.activitimanage.controller;

import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
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



    //历史查询
    @Test
    public void query(){
// 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务实例
        RepositoryService repositoryService = engine.getRepositoryService();
        // 得到运行时服务组件
        RuntimeService runtimeService = engine.getRuntimeService();
        // 得到历史服务组件
        HistoryService historyService = engine.getHistoryService();
        // 任务组件
        TaskService taskService = engine.getTaskService();
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/ActivityQuery.bpmn").deploy();
        // 开始两条流程
        ProcessInstance pi1 = runtimeService.startProcessInstanceByKey("testProcess");
        ProcessInstance pi2 = runtimeService.startProcessInstanceByKey("testProcess");
        // 完成第一个流程
        Task task = taskService.createTaskQuery().processInstanceId(pi1.getId()).singleResult();
        taskService.complete(task.getId());
        runtimeService.signalEventReceived("mySignal");
        // 第二个流程实例完成第一个任务
        task = taskService.createTaskQuery().processInstanceId(pi2.getId()).singleResult();
        taskService.complete(task.getId());
        //查询数据
        List<HistoricActivityInstance> datas = historyService.createHistoricActivityInstanceQuery()
                .activityId("endevent1").list();
        System.out.println("使用activityId查询：" + datas.size());//结果1
        datas = historyService.createHistoricActivityInstanceQuery()
                .activityInstanceId(datas.get(0).getId()).list();
        System.out.println("使用activityInstanceId查询：" + datas.size());//结果1
        datas = historyService.createHistoricActivityInstanceQuery()
                .activityType("intermediateCatchEvent").list();
        System.out.println("使用activityType查询：" + datas.size());//结果2
        datas = historyService.createHistoricActivityInstanceQuery().finished().list();
        System.out.println("使用finished查询：" + datas.size());//结果6
        datas = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(pi2.getId()).list();
        System.out.println("使用processInstanceId查询：" + datas.size());//结果3
        datas = historyService.createHistoricActivityInstanceQuery()
                .taskAssignee("crazyit").list();
        System.out.println("使用taskAssignee查询：" + datas.size());//结果2
        datas = historyService.createHistoricActivityInstanceQuery().unfinished().list();
        System.out.println("使用unfinished查询：" + datas.size());//结果1
    }
    @Test
    public void delet(){
// 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务实例
        RepositoryService repositoryService = engine.getRepositoryService();
        // 得到运行时服务组件
        RuntimeService runtimeService = engine.getRuntimeService();
        // 得到历史服务组件
        HistoryService historyService = engine.getHistoryService();
        // 任务组件
        TaskService taskService = engine.getTaskService();
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/Delete.bpmn").deploy();
        // 开始流程
        ProcessInstance pi1 = runtimeService
                .startProcessInstanceByKey("testProcess");
        ProcessInstance pi2 = runtimeService
                .startProcessInstanceByKey("testProcess");
        // 完成第一个流程实例
        Task task = taskService.createTaskQuery()
                .processInstanceId(pi1.getId()).singleResult();
        taskService.setVariableLocal(task.getId(), "name", "crazyit");
        taskService.complete(task.getId());

        task = taskService.createTaskQuery()
                .processInstanceId(pi1.getId()).singleResult();
        taskService.complete(task.getId());


        // 第二个流程实例完成第一个节点
        task = taskService.createTaskQuery().processInstanceId(pi2.getId())
                .singleResult();
        taskService.complete(task.getId());

        System.out.println("删除前任务数量："
                + historyService.createHistoricTaskInstanceQuery().count());
        // 删除第二个流程实例的历史任务数据
        historyService.deleteHistoricTaskInstance(task.getId());
        System.out.println("删除后任务数量："
                + historyService.createHistoricTaskInstanceQuery().count());
        System.out.println("删除前流程实例数量："
                + historyService.createHistoricProcessInstanceQuery().count());
        // 删除第一个流程实例的历史流程数据
        historyService.deleteHistoricProcessInstance(pi1.getId());
        // 抛出错误，删除没有完成的流程实例历史数据
        historyService.deleteHistoricProcessInstance(pi2.getId());
        System.out.println("删除后流程实例数量："
                + historyService.createHistoricProcessInstanceQuery().count());



    }

    @Test
    public void detailQuery(){
        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务实例
        RepositoryService repositoryService = engine.getRepositoryService();
        // 得到运行时服务组件
        RuntimeService runtimeService = engine.getRuntimeService();
        // 得到历史服务组件
        HistoryService historyService = engine.getHistoryService();
        // 得到任务组件
        TaskService taskService = engine.getTaskService();
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/DetailQuery.bpmn").deploy();
        // 初始化参数
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("days", 1);
        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(
                "testProcess", vars);
        // 完成第一个任务
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId())
                .singleResult();
        vars.put("days", 2); // 设置参数为2
        taskService.complete(task.getId(), vars);
        // 完成第二个任务
        task = taskService.createTaskQuery().processInstanceId(pi.getId())
                .singleResult();
        vars.put("days", 3); // 设置参数为3
        taskService.complete(task.getId(), vars);

        // 查询明细总数
        List<HistoricDetail> datas = historyService.createHistoricDetailQuery()
                .processInstanceId(pi.getId()).list();
        System.out.println("设置三次参数后，历史明细表数据：" + datas.size());

        // 查询参数表
        List<HistoricVariableInstance> hisVars = historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(pi.getId()).list();
        System.out.println("参数表数据量：" + hisVars.size());
        System.out.println("参数最后的值为：" + hisVars.get(0).getValue());
    }

    @Test
    public void proccessInstanceQuery(){
        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务实例
        RepositoryService repositoryService = engine.getRepositoryService();
        // 得到运行时服务组件
        RuntimeService runtimeService = engine.getRuntimeService();
        // 得到历史服务组件
        HistoryService historyService = engine.getHistoryService();
        TaskService taskService = engine.getTaskService();
        // 部署流程文件
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("bpmn/ProcessInstanceQuery.bpmn").deploy();
        // 查询流程定义
        ProcessDefinition define = repositoryService
                .createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        // 开始流程
        ProcessInstance pi1 = runtimeService.startProcessInstanceByKey("testProcess", "businessKey1");
        ProcessInstance pi2 = runtimeService.startProcessInstanceByKey("testProcess", "businessKey2");
        // 完成第一条流程
        Task task = taskService.createTaskQuery().processInstanceId(pi1.getId()).singleResult();
        taskService.complete(task.getId());
        task = taskService.createTaskQuery().processInstanceId(pi1.getId()).singleResult();
        taskService.complete(task.getId());
        // 查询已完成的流程
        List<HistoricProcessInstance> datas = historyService
                .createHistoricProcessInstanceQuery().finished().list();
        System.out.println("使用finished方法：" + datas.size());
        // 根据流程定义ID查询
        datas = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionId(define.getId()).list();
        System.out.println("使用processDefinitionId方法： " + datas.size());
        // 根据流程定义key（流程描述文件的process节点id属性）查询
        datas = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey(define.getKey()).list();
        System.out.println("使用processDefinitionKey方法： " + datas.size());
        // 根据业务主键查询
        datas = historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey("businessKey1").list();
        System.out.println("使用processInstanceBusinessKey方法： " + datas.size());
        // 根据流程实例ID查询
        datas = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(pi1.getId()).list();
        System.out.println("使用processInstanceId方法： " + datas.size());
        // 查询没有完成的流程实例
        historyService.createHistoricProcessInstanceQuery().unfinished().list();
        System.out.println("使用unfinished方法： " + datas.size());
    }

    @Test
    public void taskQuery() throws ParseException {
        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务实例
        RepositoryService repositoryService = engine.getRepositoryService();
        // 得到运行时服务组件
        RuntimeService runtimeService = engine.getRuntimeService();
        // 得到历史服务组件
        HistoryService historyService = engine.getHistoryService();
        // 得到任务组件
        TaskService taskService = engine.getTaskService();
        // 部署流程文件
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("bpmn/TaskQuery.bpmn").deploy();
        ProcessDefinition define = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId()).singleResult();
        // 初始化参数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("varDate1", sdf.parseObject("2020-10-10 06:00:00"));
        vars.put("varDate2", sdf.parseObject("2021-10-10 06:00:00"));
        //开始流程
        ProcessInstance pi1 = runtimeService.startProcessInstanceByKey("testProcess",
                "businessKey1", vars);
        ProcessInstance pi2 = runtimeService.startProcessInstanceByKey("testProcess",
                "businessKey2", vars);
        //完成流程1
        Task task = taskService.createTaskQuery().processInstanceId(pi1.getId()).singleResult();
        taskService.complete(task.getId());
        task = taskService.createTaskQuery().processInstanceId(pi1.getId()).singleResult();
        taskService.complete(task.getId());
        // 流程2完成一个任务
        task = taskService.createTaskQuery().processInstanceId(pi2.getId()).singleResult();
        taskService.complete(task.getId());
        //历史数据查询
        List<HistoricTaskInstance> datas = historyService.createHistoricTaskInstanceQuery()
                .finished().list();
        System.out.println("使用finished方法查询：" + datas.size());//结果3
        datas = historyService.createHistoricTaskInstanceQuery()
                .processDefinitionId(define.getId()).list();
        System.out.println("使用processDefinitionId方法查询：" + datas.size());//结果4
        datas = historyService.createHistoricTaskInstanceQuery()
                .processDefinitionKey("testProcess").list();
        System.out.println("使用processDefinitionKey方法查询：" + datas.size());//结果4
        datas = historyService.createHistoricTaskInstanceQuery()
                .processDefinitionName("testProcess2").list();
        System.out.println("使用processDefinitionName方法查询：" + datas.size());//结果4
        datas = historyService.createHistoricTaskInstanceQuery()
                .processFinished().list();
        System.out.println("使用processFinished方法查询：" + datas.size());//结果2
        datas = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(pi2.getId()).list();
        System.out.println("使用processInstanceId方法查询：" + datas.size());//结果2
        datas = historyService.createHistoricTaskInstanceQuery()
                .processUnfinished().list();
        System.out.println("使用processUnfinished方法查询：" + datas.size());//结果2
        datas = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee("crazyit").list();
        System.out.println("使用taskAssignee方法查询：" + datas.size());//结果2
        datas = historyService.createHistoricTaskInstanceQuery()
                .taskAssigneeLike("%zy%").list();
        System.out.println("使用taskAssigneeLike方法查询：" + datas.size());//结果2
        datas = historyService.createHistoricTaskInstanceQuery()
                .taskDefinitionKey("usertask1").list();
        System.out.println("使用taskDefinitionKey方法查询：" + datas.size());//结果2
        datas = historyService.createHistoricTaskInstanceQuery()
                .taskDueAfter(sdf.parse("2020-10-11 06:00:00")).list();
        System.out.println("使用taskDueAfter方法查询：" + datas.size());//结果2
        datas = historyService.createHistoricTaskInstanceQuery()
                .taskDueBefore(sdf.parse("2022-10-11 06:00:00")).list();
        System.out.println("使用taskDueBefore方法查询：" + datas.size());//结果4
        datas = historyService.createHistoricTaskInstanceQuery()
                .taskDueDate(sdf.parse("2020-10-11 06:00:00")).list();
        System.out.println("使用taskDueDate方法查询：" + datas.size());//结果0
        datas = historyService.createHistoricTaskInstanceQuery()
                .unfinished().list();
        System.out.println("使用unfinished方法查询：" + datas.size());//结果1
    }

    @Test
    public void asyncConfig(){
        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务实例
        RepositoryService repositoryService = engine.getRepositoryService();
        // 得到管理服务实例
        ManagementService managementService = engine.getManagementService();
        // 得到运行时服务组件
        RuntimeService runtimeService = engine.getRuntimeService();
        // 部署流程文件
        repositoryService.createDeployment().addClasspathResource("bpmn/async-continuation.bpmn")
                .deploy();
        // 产生由async-continuation处理的工作
        ProcessInstance pi1 = runtimeService.startProcessInstanceByKey("async-continuation");
        // 查询工作数量
        System.out.println("工作数量：" + managementService.createJobQuery().count());
    }
}
