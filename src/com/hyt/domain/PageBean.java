package com.hyt.domain;

import java.util.List;

public class PageBean<T> {
	
	private int currentPage = 1;//当前页，默认显示第一页
	private int pageCount = 12;//每页显示的数（查询返回的行数），默认每页显示12个
	private int totalCount;
	private int totalPage;
	private List<T> data;
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
//	返回总页数
	public int getTotalPage() {
		if(totalCount % pageCount == 0) {
			totalPage = totalCount / pageCount;
		}else {
			totalPage = totalCount / pageCount + 1;
		}
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
}
