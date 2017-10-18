package com.huike.page;

public class Expression {

	private String fieldName;
	private String expression;
	private String fieldValue;
	
	
	public Expression() {
		super();
	}
	
	public Expression(String fieldName, String expression, String fieldValue) {
		super();
		this.fieldName = fieldName;
		this.expression = expression;
		this.fieldValue = fieldValue;
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
}
