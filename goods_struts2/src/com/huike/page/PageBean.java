package com.huike.page;

import java.util.Arrays;
import java.util.List;

import com.huike.book.domain.Book;
/*
 * 提供分页上需要显示的信息
 */
public class PageBean {
	private List list; 	//查询的分页数据
	private int totalPage;		//一共有多少页
	private long totalRecord;	//数据库中相应的数据的总条数
	private int pageSize;		//每页显示多少条数据
	private int currentPage;	//当前是第几页
	private int prePage;		//上一页  currentPage - 1
	private int nextPage;		//下一页  currentPage + 1
	private int[] pageBar; 		//页码条
	private String url;
	
	public PageBean() {
		super();
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setPageBar(int[] pageBar) {
		this.pageBar = pageBar;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getTotalPage() {
		
		/*
		 * 计算总页数    = 总记录条数 / 每页的数量     102 / 10 = 10.2  -> 11.0  10.1 -> 11.0  10.9 -> 11.0
		 */
		
		int a = (int)(totalRecord /  pageSize);
		if (a == 0) {
			
			totalPage = a;
		}else{
			totalPage = a + 1;
		}
		/*ceil:天花板
		floor:地板
		round:四舍五入*/
		
		//totalPage = (int)Math.ceil((double)totalRecord /  pageSize);
	
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPrePage() {
		
		prePage = currentPage - 1;
		if (prePage <= 0) {
			prePage = 1;
		}
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public int getNextPage() {
		nextPage = this.currentPage + 1;
		if (nextPage >= totalPage) {
			nextPage = totalPage;
		}
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int[] getPageBar() {
		/*
		 * [1,2,3,4,5,6,7,8	,9,10]
		 * 9:  [4 - 13]
		 * 13: [8 - 17]
		 * 
		 * start = x - 5
		 * end = x + 4;
		 * 当前页 前面有5个页码,后面有4个页码
		 * 	1.页码条始终要显示10个选项
		 * 		当我点击的页码大于5, 页码条就要发生了改变, 向后移动
		 */
		int start = currentPage - 5;	//页码条的起始
		int end = currentPage + 4;		//页码条的结束
		
		//总页数小于10    1,2,3,4,5,6
		if (getTotalPage() < 10) {
			start = 1;
			end = totalPage;
			pageBar = new int[totalPage];
		}else{
			//页码总数是大于10的
			pageBar = new int[10];
			
			if (currentPage <= 5) {
				start = 1;
				end = 10;
			}
			//  页码条不会继续上升
			if (currentPage > totalPage - 4) { //   100   100  
				end = totalPage;
				start = totalPage - 9;
			}
		}
		
		//根据end和start生成pageBar    1   10
		for(int index = 0 , i = start; i <= end;i++){
			pageBar[index++] = i;
		}
		
		return pageBar;
	}
	
}
