package com.huike.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Admin implements Serializable {

	private Integer id ;
	
	@NotEmpty(message="用户名不能为空")
	private String username ;
	
	@NotEmpty(message="密码不能为空")
	private String password ;
	
	public Admin() {
	}

	public Admin(String username, String password) {
		this.username = username ;
		this.password = password ;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
