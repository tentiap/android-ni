package com.example.nutechwallet.model.topup;

import com.google.gson.annotations.SerializedName;

public class TopupResponse{

	@SerializedName("data")
	private Object data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(Object data){
		this.data = data;
	}

	public Object getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}