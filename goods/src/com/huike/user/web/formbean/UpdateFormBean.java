package com.huike.user.web.formbean;

import java.util.HashMap;
import java.util.Map;

public class UpdateFormBean {
	private String loginpass;
	private String newpass;
	private String reloginpass;
	private String verifyCode;
	private Map<String, String> errors = new HashMap<String, String>();
	
	public UpdateFormBean() {
		super();
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	public String getLoginpass() {
		return loginpass;
	}
	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}
	public String getNewpass() {
		return newpass;
	}
	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}
	public String getReloginpass() {
		return reloginpass;
	}
	public void setReloginpass(String reloginpass) {
		this.reloginpass = reloginpass;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	public boolean validate() {
		if (loginpass == null || loginpass.trim().length() == 0) {
			errors.put("loginpass", "原密码不能为空");
		}
		
		if (newpass == null || newpass.trim().length() == 0) {
			errors.put("newpass", "密码不能为空");
		}else if(!(newpass.length() >= 3 && newpass.length() <= 10)){
			errors.put("newpass", "新密码长度必须在3 ~ 10之间");
		}
		
		if (reloginpass == null || reloginpass.trim().length() == 0) {
			errors.put("reloginpass", "密码不能为空");
		}else if(!reloginpass.equals(newpass)){
			errors.put("reloginpass", "两次输入不一致");
		}
		
		if (verifyCode == null || verifyCode.trim().length() == 0) {
			errors.put("verifycode", "验证码不能为空");
		}
		
		return errors.isEmpty();
	}
}
