package com.atai.bigdata.hadooprpc.service;


import com.atai.bigdata.hadooprpc.protocol.IUserLoginService;

public class UserLoginServiceImpl implements IUserLoginService {

	@Override
	public String login(String name, String passwd) {
		
		return name + "logged in successfully...";
	}
	
	
	

}
