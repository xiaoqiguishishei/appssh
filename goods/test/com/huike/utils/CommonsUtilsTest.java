package com.huike.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.huike.user.domain.User;
import com.huike.utils.commons.CommonUtils;

public class CommonsUtilsTest {

	//junit: 单元测试, 单独对每个功能进行测试, 也可以同时对多个功能进行整体测试
	
	
	@Test
	public void uuidTest() {
		String uuid = CommonUtils.uuid();
		System.out.println(uuid);
	}
	
	@Test
	public void mapToBeanTest() {
		/*
		 * toBean:将map中的数据转为javabean对象
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", "123");
		map.put("loginname", "张三");
		map.put("loginpass", "123456");
		map.put("email", "qqq.@163.com");
	}
	
	/*public static void main(String[] args) {
		
		 * 测试CommonsUtils中的方法
		 
		String uuid = CommonUtils.uuid();
		System.out.println(uuid);
		
		
		 * toBean:将map中的数据转为javabean对象
		 
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", uuid);
		map.put("loginname", "张三");
		map.put("loginpass", "123456");
		map.put("email", "qqq.@163.com");
		
		User user = CommonUtils.toBean(map, User.class);
		System.out.println(user);
		
	}*/
}
