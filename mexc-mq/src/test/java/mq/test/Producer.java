package mq.test;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;  

public class Producer {

        
    public static void prioritySend(Serializable mes) throws IOException, TimeoutException{  
    	ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.100"); //使用默认端口连接本地rabbitmq服务器
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        Connection connection = factory.newConnection(); //声明一个连接
        Channel channel = connection.createChannel(); 
        channel.exchangeDeclare("exchange_name1", BuiltinExchangeType.DIRECT);
        channel.queueBind("priority_queue1", "exchange_name1", "");
        //发送10条消息  
        for(int i=0;i<10;i++){  
            //随机设置消息优先级  
            Builder properties=new BasicProperties.Builder();  
            int priority=new Random().nextInt(10);  
            properties.priority(priority);//建议0~255，超过貌似也没问题  
            String _mes=mes.toString()+i;  
            channel.basicPublish("exchange_name1", "", properties.build(), SerializationUtils.serialize(_mes));
            System.out.println(priority+" "+_mes);  
        }  
        channel.close();  
        connection.close();  
    }  
    
    public static void main(String[] args) throws IOException, TimeoutException {
    	
        prioritySend("level sss");
    }


}
