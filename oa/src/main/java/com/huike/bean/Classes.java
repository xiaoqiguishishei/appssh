package com.huike.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;



public class Classes implements Serializable{

	private int cid  ;
	
	private String className ;	
	
	private Date startTime ;
	
	private double timeLength ;
	
	private ClassType classType ;
	
	private String courseStatus  ;
	
	private Teacher  teacher ;
	
	private Lecturer lecturer ;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public double getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(double timeLength) {
		this.timeLength = timeLength;
	}

	public ClassType getClassType() {
		return classType;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}

	public String getCourseStatus() {
		return courseStatus;
	}

	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	@Override
	public String toString() {
		return "Classes [cid=" + cid + ", className=" + className
				+ ", startTime=" + startTime + ", timeLength=" + timeLength
				+ ", classType=" + classType + ", courseStatus=" + courseStatus
				+ ", teacher=" + teacher + ", lecturer=" + lecturer + "]";
	}
}
