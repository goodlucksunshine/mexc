package com.mexc.mq.core;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author liuxingmi
 * @datetime 2017年12月7日 下午7:46:37
 * @desc 消息队列消费者业务
 */
public interface IMqCustomerListener extends MessageListener{

	public default void onMessage(Message message) {
		process(message);
	}
	
	public void  process(Message message);
	
}
