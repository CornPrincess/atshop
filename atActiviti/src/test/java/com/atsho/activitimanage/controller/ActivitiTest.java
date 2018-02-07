package com.atsho.activitimanage.controller;

import com.atsho.activitimanage.acitivitidemo.DelegateBean;
import com.atsho.activitimanage.acitivitidemo.ExpressionBean;
import com.atsho.activitimanage.acitivitidemo.MyJavaBean;
import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ActivitiTest {

    private ProcessEngine engine;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private ManagementService managementService;
    private HistoryService historyService;

    @Before
    public void setUp() {
        // 创建流程引擎
        engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务实例
        repositoryService = engine.getRepositoryService();
        // 得到运行时服务组件
        runtimeService = engine.getRuntimeService();
        // 得到历史服务组件
        historyService = engine.getHistoryService();
        // 得到任务组件
        taskService = engine.getTaskService();
    }

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
    public void addGroup() {
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
    public void delGroup() {
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
    public void query() {
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
    public void delet() {
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
    public void detailQuery() {
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
    public void proccessInstanceQuery() {
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
    public void asyncConfig() {
        // 部署流程文件
        repositoryService.createDeployment().addClasspathResource("bpmn/async-continuation.bpmn")
                .deploy();
        // 产生由async-continuation处理的工作
        ProcessInstance pi1 = runtimeService.startProcessInstanceByKey("async-continuation");
        // 查询工作数量
        System.out.println("工作数量：" + managementService.createJobQuery().count());
    }

    @Test
    public void deadLetterJob() {
        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务实例
        RepositoryService repositoryService = engine.getRepositoryService();
        // 得到运行时服务组件
        RuntimeService runtimeService = engine.getRuntimeService();
        // 管理服务组件
        ManagementService managementService = engine.getManagementService();
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/deadletter.bpmn").deploy();
        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("deadletter");
        // 设置重试次数
        Job job = managementService.createJobQuery().singleResult();
        managementService.setJobRetries(job.getId(), 1);
        // 重新执行该工作，抛出异常
        try {
            managementService.executeJob(job.getId());
        } catch (Exception e) {

        }
        // 查询无法执行工作表
        long deadCount = managementService.createDeadLetterJobQuery().count();
        System.out.println("无法执行的工作，数据量：" + deadCount);
    }

    @Test
    public void eventTest() {
        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务实例
        RepositoryService repositoryService = engine.getRepositoryService();
        // 得到运行时服务组件
        RuntimeService runtimeService = engine.getRuntimeService();
        // 任务服务组件
        TaskService taskService = engine.getTaskService();
        // 部署流程文件
        repositoryService
                .createDeployment()
                .addClasspathResource("bpmn/event.bpmn")
                .deploy();
        runtimeService.startProcessInstanceByKey("event");
        // 将task1的工作完成后，就会产生工作
        Task task = taskService.createTaskQuery().taskName("Task1").singleResult();
        taskService.complete(task.getId());
        // 查询工作数量
        System.out.println("工作数量：" + engine.getManagementService().createJobQuery().count());
    }

    @Test
    public void suspendJob() {
        // 部署流程文件
        Deployment dep = repositoryService
                .createDeployment()
                .addClasspathResource("bpmn/SuspendJob.bpmn")
                .deploy();
        // 启动流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("suspendJob");
        // 查询定时器表的数量
        long timerCount = managementService.createTimerJobQuery().count();
        System.out.println("中断前定时器表的数据量：" + timerCount);
        // 查询中断表的数量
        long suspendCount = managementService.createSuspendedJobQuery().count();
        System.out.println("中断前中断表数据量：" + suspendCount);
        // 中断流程实例
        runtimeService.suspendProcessInstanceById(pi.getId());
        // 查询定时器表的数量
        timerCount = managementService.createTimerJobQuery().count();
        System.out.println("中断后定时器表的数据量：" + timerCount);
        // 查询中断表的数量
        suspendCount = managementService.createSuspendedJobQuery().count();
        System.out.println("中断后中断表数据量：" + suspendCount);
    }

    @Test
    public void timerIntermediateTransition() {
        // 部署流程文件
        repositoryService
                .createDeployment()
                .addClasspathResource("bpmn/timer-intermediate-transition.bpmn")
                .deploy();
        // 开始流程
        runtimeService.startProcessInstanceByKey("timer-intermediate-transition");
        // 查询工作数量
        System.out.println("工作数量：" + engine.getManagementService().createTimerJobQuery().count());
    }

    @Test
    public void getProperties() {
        Map<String, String> props = managementService.getProperties();
        //输出属性
        for (String key : props.keySet()) {
            System.out.println("属性：" + key + " 值为：" + props.get(key));
        }
    }

    @Test
    public void signalEvent() {
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/SignalDefine.bpmn").deploy();
    }

    @Test
    public void startEvent() throws InterruptedException {
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/TimerStartEvent.bpmn").deploy();
        // 等待时间条件
        Thread.sleep(70 * 1000);
        // 查询流程实例
        List<ProcessInstance> ints = runtimeService.createProcessInstanceQuery().list();
        System.out.println(ints.size());
        System.exit(0);
    }

    @Test
    public void messageCatchEvent() {
        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务组件
        RepositoryService repositoryService = engine.getRepositoryService();
        // 得到运行时服务组件
        RuntimeService runtimeService = engine.getRuntimeService();
        TaskService taskService = engine.getTaskService();
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/MessageCatchingEvent.bpmn").deploy();
        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("mcProcess");
        Execution exe = runtimeService.createExecutionQuery().activityId("messageintermediatecatchevent1").singleResult();
        runtimeService.messageEventReceived("myMsg", exe.getId());
        // 输出当前的任务
        List<Task> tasks = taskService.createTaskQuery().list();
        for (Task task : tasks) {
            System.out.println(task.getName());
        }
    }

    @Test
    public void signalCatchEvent() {
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/SignalCatchingEvent.bpmn").deploy();
        // 启动流程
        runtimeService.startProcessInstanceByKey("scProcess");
        Task firstTask = taskService.createTaskQuery().singleResult();
        taskService.complete(firstTask.getId());
        // 此时会出现并行的两个流程分支，查找用户任务并完成
        Task payTask = taskService.createTaskQuery().singleResult();
        // 完成任务
        taskService.complete(payTask.getId());
        // 发送信号完成支付
        runtimeService.signalEventReceived("finishPay");
        Task finishTask = taskService.createTaskQuery().singleResult();
        System.out.println("当前流程任务：" + finishTask.getName());
    }

    @Test
    public void signalThrowEvent() {
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/SignalThrowingEvent.bpmn").deploy();
        // 启动流程
        runtimeService.startProcessInstanceByKey("stProcess");
        // 完成选择商品任务
        Task firstTask = taskService.createTaskQuery().singleResult();
        taskService.complete(firstTask.getId());
        // 完成用户支付任务
        Task payTask = taskService.createTaskQuery().singleResult();
        taskService.complete(payTask.getId());
        // 由于使用了异步的中间Throwing事件，因此会产生2条工作数据
        List<Job> jobs = managementService.createJobQuery().list();
        System.out.println(jobs.size());
    }

    @Test
    public void timerCatchingEvent() throws InterruptedException {
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/TimerCatchingEvent.bpmn").deploy();
        // 启动流程
        runtimeService.startProcessInstanceByKey("tcProcess");
        // 查询当前任务
        Task currentTask = taskService.createTaskQuery().singleResult();
        taskService.complete(currentTask.getId());
        Thread.sleep(1000 * 70);
        // 重新查询当前任务
        currentTask = taskService.createTaskQuery().singleResult();
        System.out.println("定时器中间事件的触发后任务：" + currentTask.getName());
    }


    @Test
    public void variable1() {
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/Variable1.bpmn").deploy();
        // 启动流程
        runtimeService.startProcessInstanceByKey("myProcess");
    }

    // 12
    @Test
    public void asignee() {
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/Assignee.bpmn").deploy();
        // 启动流程
        runtimeService.startProcessInstanceByKey("process1");
    }

    @Test
    public void candidate() {
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/Candidate.bpmn").deploy();
        // 启动流程
        runtimeService.startProcessInstanceByKey("process1");
        // 根据用户组查询任务
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("boss").list();
        System.out.println("分配到boss用户组下的任务数量：" + tasks.size());
        // 根据用户查询任务
        tasks = taskService.createTaskQuery().taskCandidateUser("angus").list();
        System.out.println("用户angus下的任务数量为：" + tasks.size());
    }

    @Test
    public void javaBeanServiceTask() {
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/JavaBeanServiceTask.bpmn").deploy();
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("myBean", new MyJavaBean());
        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(
                "process1", vars);
        // 进行任务参数查询
        System.out.println("运行两个Service Task的myName参数值为："
                + runtimeService.getVariable(pi.getId(), "myName"));
    }

    @Test
    public void classTaskListener() {
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/ClassTaskListener.bpmn").deploy();
        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("process1");
    }

    @Test
    public void delegateExpressionTaskListener() {
        // 部署流程文件
        repositoryService
                .createDeployment()
                .addClasspathResource(
                        "bpmn/DelegateExpressionTaskListener.bpmn")
                .deploy();
        // 初始化参数
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("myDelegate", new DelegateBean());
        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(
                "process1", vars);
    }

    @Test
    public void expressionTaskListener() {
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/ExpressionTaskListener.bpmn").deploy();
        // 初始化参数
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("myBean", new ExpressionBean());
        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("process1", vars);
    }

    @Test
    public void listenerFire() {
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/ListenerFire.bpmn").deploy();
        // 启动流程
        ProcessInstance pi = runtimeService
                .startProcessInstanceByKey("process1");
        // 查询并完成任务
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId())
                .singleResult();
        taskService.complete(task.getId());
    }

    @Test
    public void propertyInjection() {
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/PropertyInjection.bpmn").deploy();
        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("process1");
    }

    @Test
    public void executionListenerInvocation() {
        // 部署流程文件
        repositoryService
                .createDeployment()
                .addClasspathResource(
                        "bpmn/ExecutionListenerInvocation.bpmn")
                .deploy();
        // 启动流程
        ProcessInstance pi = runtimeService
                .startProcessInstanceByKey("process1");
        // 查找并完成任务
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        taskService.complete(task.getId());
    }

    // 13


}
