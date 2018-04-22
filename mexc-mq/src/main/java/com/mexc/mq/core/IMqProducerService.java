package com.mexc.mq.core;

/**
 * @author liuxingmi
 * @datetime 2017年12月7日 下午7:46:06
 * @desc 消息队列生成者接口
 */
public interface IMqProducerService {
    public void convertAndSend(String queueName, Object obj);

}
