package com.huike.bean;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.internal.xml.ClassType;

public class Lecturer implements Serializable {

	private int lid ;
	
	private String lecturerName ;
	
	private double workExperience ;
	
	private String lecturerState ;
	
	private Date registerTime ;
	
	private ClassType classType ;

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public String getLecturerName() {
		return lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}

	public double getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(double workExperience) {
		this.workExperience = workExperience;
	}

	public String getLecturerState() {
		return lecturerState;
	}

	public void setLecturerState(String lecturerState) {
		this.lecturerState = lecturerState;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public ClassType getClassType() {
		return classType;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}
	
}
