package com.mexc.common.util;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @DateTime 2015年12月8日 下午4:39:58
 * @Company 刘兴密个人类库
 * @Author 刘兴密
 * @QQ 63972012
 * @Desc 日志输出信息组装类
 */
public class LogUtil {
	
	
	public static String msg(String cmd, String msg){
		return new StringBuilder().append("cmd=")
				.append(cmd)
				.append(" msg=")
				.append(msg).toString();
	}

	public static String msg(String cmd, String msg, String ip){
		return new StringBuilder().append("cmd=")
				.append(cmd)
				.append(" ip=")
				.append(ip)
				.append(" msg=")
				.append(msg).toString();
	}
	
	
	public static String msg(String cmd, String sid, String msg, String ip){
		
		return new StringBuilder().append("cmd=")
				.append(cmd)
				.append(" ip=")
				.append(ip)
				.append(" sid=")
				.append(sid)
				.append(" msg=")
				.append(msg).toString();
	}

	
	public static String msg(String cmd, String msg, String sid, String ip, Object data){
		return new StringBuilder().append(" cmd=")
				.append(cmd)
				.append(" ip=")
				.append(ip)
				.append(" sid=")
				.append(sid)
				.append(" data=")
				.append(JSON.toJSONString(data))
				.append(" msg=")
				.append(msg).toString();
	}
	

}
