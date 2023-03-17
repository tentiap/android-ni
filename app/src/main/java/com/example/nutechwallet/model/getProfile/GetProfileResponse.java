package com.example.nutechwallet.model.getProfile;

import com.google.gson.annotations.SerializedName;

public class GetProfileResponse{

	@SerializedName("data")
	private GetProfileData getProfileData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(GetProfileData getProfileData){
		this.getProfileData = getProfileData;
	}

	public GetProfileData getData(){
		return getProfileData;
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