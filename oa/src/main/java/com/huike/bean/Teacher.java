package com.huike.bean;

import java.io.Serializable;
import java.util.Date;

public class Teacher implements Serializable {

	private int tid;
	
	private String teacherName;
	
	private double workExperience;
	
	private String teacherState;
	
	private Date registerTime;

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public double getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(double workExperience) {
		this.workExperience = workExperience;
	}

	public String getTeacherState() {
		return teacherState;
	}

	public void setTeacherState(String teacherState) {
		this.teacherState = teacherState;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

}
