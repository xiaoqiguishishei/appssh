package com.huike.page;

import java.util.List;

import com.huike.book.domain.Book;

public class QueryResult {
	
	private long totalRecord;  //数据库中相应的数据的总条数
	private List list;	//这个表示数据  (分页)
	public QueryResult() {
		super();
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
}
