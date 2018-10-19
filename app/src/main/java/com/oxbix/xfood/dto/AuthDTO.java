package com.oxbix.xfood.dto;

public class AuthDTO {

	private String token;
	private String username;
	private String userType;
	private long location;
	private String currency;
	private String tenantBrandURL;
	private String sysMessage;
	
	public AuthDTO(String token, String username, String userType, long location, String currency, String tenantBrandURL, String sysMessage) {
		super();
		this.token = token;
		this.username = username;
		this.userType = userType;
		this.location = location;
		this.currency=currency;
		this.tenantBrandURL=tenantBrandURL;
		this.sysMessage = sysMessage;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public long getLocation() {
		return location;
	}
	public void setLocation(long location) {
		this.location = location;
	}
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTenantBrandURL() {
		return tenantBrandURL;
	}

	public void setTenantBrandURL(String tenantBrandURL) {
		this.tenantBrandURL = tenantBrandURL;
	}

	public String getSysMessage() {
		return sysMessage;
	}

	public void setSysMessage(String sysMessage) {
		this.sysMessage = sysMessage;
	}

	
	
}
