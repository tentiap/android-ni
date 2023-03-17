package com.example.nutechwallet.model.getBalance;

import com.google.gson.annotations.SerializedName;

public class GetBalanceResponse{

	@SerializedName("data")
	private GetBalanceData getBalanceData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(GetBalanceData getBalanceData){
		this.getBalanceData = getBalanceData;
	}

	public GetBalanceData getData(){
		return getBalanceData;
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