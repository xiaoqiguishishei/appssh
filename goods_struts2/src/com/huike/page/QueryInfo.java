package com.huike.page;

public class QueryInfo {
	/*
	 * 封装前台提交的信息   limit start,pageSize
	 * start = pageSize * (currentPage - 1)
	 */
	private int currentPage = 1;
	private int startIndex;   //数据库的分页第一个参数
	private int pageSize = 8;
	private String cid;
	private String bname;
	private String press;
	private String author;
	
	public QueryInfo() {
		super();
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getStartIndex() {
		
		startIndex = (currentPage - 1)*pageSize;
		
		return startIndex;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "QueryInfo [currentPage=" + currentPage + ", startIndex="
				+ startIndex + ", pageSize=" + pageSize + ", cid=" + cid
				+ ", bname=" + bname + ", press=" + press + ", author="
				+ author + "]";
	}
	
}
