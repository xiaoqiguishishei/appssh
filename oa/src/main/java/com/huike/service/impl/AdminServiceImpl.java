package com.huike.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huike.bean.Admin;
import com.huike.dao.AdminDao;
import com.huike.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminDao ad ;

	public Admin login(Admin admin) {
		//先对密码进行加密
		return ad.login(admin) ;
	}
}
