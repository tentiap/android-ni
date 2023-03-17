package com.example.nutechwallet.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("data")
	private LoginData data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(LoginData data){
		this.data = data;
	}

	public LoginData getData(){
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