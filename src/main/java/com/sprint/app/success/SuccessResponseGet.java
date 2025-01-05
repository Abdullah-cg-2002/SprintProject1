package com.sprint.app.success;

import java.util.List;

import com.sprint.app.model.Posts;



public class SuccessResponseGet {
	
	private String status;
	private List<Object> data;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Object> getData() {
		return data;
	}
	
	public void setData(List<Object> groupsList) {
		this.data=groupsList;
		
	}
	public void setMessage(List<Object> postList) {
		this.data=postList;
	}
	
	
}
