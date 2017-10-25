package com.huike.user.web.formbean;

import java.util.HashMap;
import java.util.Map;

public class LoginFormBean {
	private String loginname;
	private String loginpass;
	private String verifyCode;
	
	private Map<String, String> errors = new HashMap<String, String>();
	public LoginFormBean() {
		super();
	}
	public Map<String, String> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
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
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	public boolean validate() {
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
		
		if (verifyCode == null || verifyCode.trim().length() == 0) {
			errors.put("verifycode", "验证码不能为空");
		}
		
		return errors.isEmpty();
	}
}
