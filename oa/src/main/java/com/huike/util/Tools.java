package com.huike.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

public class Tools {

	//对字符串进行加密
	public static String getSecurityStr(String string){
		//加密
		try {
			//获取md5加密对象
			MessageDigest md = MessageDigest.getInstance("md5") ;
			byte[] bs  = md.digest(string.getBytes()) ;
			
			//第二次加密
			BASE64Encoder base = new BASE64Encoder() ;
			//数据指纹
			return base.encode(bs) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null ;
	} 
	
	//转换get方式的乱码
	public static String getUTFEncoding(String str){
		try {
			return new String(str.getBytes("iso8859-1"),"utf-8") ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null ;
	} 
}
