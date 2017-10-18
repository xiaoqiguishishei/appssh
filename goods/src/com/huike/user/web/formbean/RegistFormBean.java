package com.huike.user.web.formbean;

import java.util.HashMap;
import java.util.Map;

public class RegistFormBean {
	/*
	 * 和表单中的字段一一对应, 也有写到实体类的, 但是不太好, User的属性放到formbean合二为一, 或者在servlet里面做
	 */
	private String loginname;
	private String loginpass;
	private String reloginpass;
	private String email;
	private String verifycode;
	
	private Map<String, String> errors = new HashMap<String, String>();
	
	
	public RegistFormBean() {
		super();
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getLoginpass() {
		return loginpass;
	}
	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}
	public String getReloginpass() {
		return reloginpass;
	}
	public void setReloginpass(String reloginpass) {
		this.reloginpass = reloginpass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getVerifycode() {
		return verifycode;
	}
	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
	public Map<String, String> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	
	/*
	 * 校验数据
	 */
	public boolean validate() { //提示有没有校验通过, false:没有校验通过
		
		/*
		用户名校验：
			用户名不能为空；
			用户名长度必须在3 ~ 20之间；
		登录密码校验：
			密码不能为空；
			密码长度必须在3 ~ 10之间；
		确认密码校验：
			确认密码不能为空；
			两次输入不一致；
		Email校验：
			Email不能为空；     131232112121@qq.com
			Email格式错误（/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/）；
		验证码校验：
			验证码不能为空；
	*/
		
		if (loginname == null || loginname.trim().length() == 0) {
			errors.put("loginname", "用户名不能为空");
		}else if(!(loginname.length() >= 3 && loginname.length() <= 20)){
			errors.put("loginname", "用户名长度必须在3 ~ 20之间");
		}
		
		if (loginpass == null || loginpass.trim().length() == 0) {
			errors.put("loginpass", "密码不能为空");
		}else if(!(loginpass.length() >= 3 && loginpass.length() <= 10)){
			errors.put("loginpass", "密码长度必须在3 ~ 10之间");
		}
		
		if (reloginpass == null || reloginpass.trim().length() == 0) {
			errors.put("reloginpass", "确认密码不能为空");
		}else if(!reloginpass.equals(loginpass)){
			errors.put("reloginpass", "两次输入不一致");
		}
		
		if (email == null || email.trim().length() == 0) {
			errors.put("email", "邮箱不能为空");
		}
		if (verifycode == null || verifycode.trim().length() == 0) {
			errors.put("verifycode", "验证码不能为空");
		}
		
		
		return errors.isEmpty();
	}
}
