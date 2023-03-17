package com.example.nutechwallet.model.register;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse{

	@SerializedName("data")
	private RegisterData registerData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(RegisterData registerData){
		this.registerData = registerData;
	}

	public RegisterData getData(){
		return registerData;
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