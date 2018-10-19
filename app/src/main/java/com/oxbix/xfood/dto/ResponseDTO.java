package com.oxbix.xfood.dto;

/**
 * 访问WebService接口时的状态返回类
 * @author AllenZheng 
 * @date 2014年9月17日 下午3:34:38
 */
public class ResponseDTO {
	
	/**
	 * 状态返回码 
	 * @param 200  对应状态返回信息中的"success"
	 * @param 401  对应状态返回信息中的"access_error"
	 * @param 501  对应状态返回信息中的"server_error"
	 **/
	private int status;
	/** 状态返回信息  **/
	private String message;  
	/** 服务器返回数据  **/
	private Object response;

	public ResponseDTO(int status, String message, Object response) {
		super();
		this.status = status;
		this.message = message;
		this.response = response;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}
