package com.mexc.common.base;

/**
 * @author liuxingmi
 * @datetime 2017年12月25日 下午2:51:04
 * @desc 业务接口基础返回类
 */
public class ResultVo<T> {

	private String code;
	
	private String errMsg;
	
	private T data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	
}
