package com.oxbix.xfood.net;

import java.lang.reflect.Type;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.oxbix.xfood.dto.ResponseDTO;
import com.oxbix.xfood.net.WSClient.StatusListener;

/**
 * 处理网络访问响应和返回数据的类
 * @author AllenZheng 
 * @date 2014年9月17日 下午3:54:06
 */
class MyBaseJsonHttpResponseHandler extends BaseJsonHttpResponseHandler<ResponseDTO> {
	private StatusListener statusLisenter;
	private Type type;

	public MyBaseJsonHttpResponseHandler(StatusListener statusLisenter, Type type) {
		this.statusLisenter = statusLisenter;
		this.type = type;
	}

	@Override
	public void onStart() {
		if (statusLisenter != null) {
			statusLisenter.preLoadListener();
		}
		super.onStart();
	}

	/**
	 * 重写访问失败调用的方法
	 */
	@Override
	public void onFailure(int statusCode, Header[] arg1, Throwable throwable, String arg3, ResponseDTO responseDto) {
		if (statusLisenter != null && responseDto != null) {
			// 失败返回空
			statusLisenter.statusListener(responseDto.getStatus(), null);
			throwable.printStackTrace();
		} else {
			// 网络请求异常，服务器没有返回数据
			statusLisenter.statusListener(statusCode, null);
		}
	}

	/**
	 * 重写访问成功调用的方法
	 */
	@Override
	public void onSuccess(int statusCode, Header[] arg1, String arg2, ResponseDTO responseDto) {

		if (statusLisenter != null && responseDto != null && responseDto.getStatus() == 200 && responseDto.getResponse().toString() != null) {

			statusLisenter.statusListener(responseDto.getStatus(), new Gson().fromJson(new Gson().toJson(responseDto.getResponse()), type));
		} else {
			// 访问成功，但服务器返回的数据为空
			statusLisenter.statusListener(responseDto.getStatus(), null);
		}
	}

	/**
	 * 解析网络返回的json数据
	 */
	@Override
	protected ResponseDTO parseResponse(String json, boolean arg1) throws Throwable {
		ResponseDTO response = (ResponseDTO) new Gson().fromJson(json, ResponseDTO.class);
		return response;
	}
	
}
