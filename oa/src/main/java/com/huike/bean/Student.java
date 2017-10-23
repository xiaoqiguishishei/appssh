package com.huike.bean;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class Student implements Serializable {

	private Integer sid  ;
	
	@NotEmpty(message="用户名不能为空")
	private String name  ;
	
	private String gender ;
	
	private String place ;
	
	private String school ;
	
	private String phone ;
	
	private String status ;
	
	private Date birthday ;
	
	private Date registerTime ;
	
	private String icon ;
	
	@NotEmpty(message="密码不能为空")
	private String password ;
	
	private Integer classes_cid ;
	
	public Student() {
	
	}
	
	public Student(String username, String password) {
		this.name = username ;
		this.password = password ;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getClasses_cid() {
		return classes_cid;
	}
	public void setClasses_cid(Integer classes_cid) {
		this.classes_cid = classes_cid;
	}
	
	
}
