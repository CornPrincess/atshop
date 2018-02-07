package com.atsho.activitimanage.acitivitidemo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;

public class PropertyInjection implements TaskListener {

	private Expression userName;
	
	public void setUserName(Expression userName) {
		this.userName = userName;
	}
	
	public void notify(DelegateTask delegateTask) {
		System.out.println("属性注入后的值：" + this.userName.getValue(null));
	}

}
