package com.mexc.mq.core.impl;

import com.mexc.mq.core.IMqProducerService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MqProducerService implements IMqProducerService {

	@Resource
	private AmqpTemplate amqpTemplate;

	
	public void convertAndSend(String queueName, Object obj) {
		
	     amqpTemplate.convertAndSend(queueName, obj);
	}	
	 
}
