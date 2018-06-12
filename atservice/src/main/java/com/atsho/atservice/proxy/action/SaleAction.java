package com.atsho.atservice.proxy.action;


import com.atsho.atservice.proxy.service.IBoss;
import com.atsho.atservice.proxy.service.impl.Boss;

public class SaleAction {
	/**
	 * 不使用代理，直接调用方法
	 * 方法中规定什么业务，就只能调用什么业务，规定什么返回值，就只能输出什么返回值
	 */
//	@Test
	public static void main(String[] args) {
		IBoss boss = new Boss();
		System.out.println("老板自营！");
		int money = boss.yifu("xxl");// 老板自己卖衣服，不需要客服，结果就是没有聊天记录
		System.out.println("衣服成交价：" + money);
	}
}
