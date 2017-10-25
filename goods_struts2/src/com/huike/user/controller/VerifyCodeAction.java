package com.huike.user.controller;

import java.io.ByteArrayInputStream;

import com.huike.utils.vcode.utils.VerifyCode;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class VerifyCodeAction extends ActionSupport {
	private ByteArrayInputStream inputStream;

	public String execute() throws Exception {
		VerifyCode rdnu = VerifyCode.Instance();
		this.setInputStream(rdnu.getImage());// 取得带有随机字符串的图片
		ActionContext.getContext().getSession()
				.put("vCode", rdnu.getString());// 取得随机字符串放入HttpSession
		return SUCCESS;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
}