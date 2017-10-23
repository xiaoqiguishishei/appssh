package com.huike.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huike.bean.Student;
import com.huike.dao.StudentDao;
import com.huike.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentDao sd ;

	public Student login(Student stu) {
		//先对密码进行加密
		return sd.login(stu) ;
	}
}
