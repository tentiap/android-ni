package com.example.nutechwallet.model.history;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HistoryResponse{

	@SerializedName("data")
	private List<HistoryData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<HistoryData> data){
		this.data = data;
	}

	public List<HistoryData> getData(){
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