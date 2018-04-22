package mq.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope; 

public class Customer {
	
	 private static String exchange_name="priority_direct";  
	    private static String queueName="priority_queue";  
	      
	    /** 
	     * 只有当消费者不足，不能及时进行消费的情况下，优先级队列才会生效 
	     * 验证方式： 
	     * 1、可先发送消息，再进行消费 
	     * 2、开启手动应答、设置Qos。若为1，在一个消费者存在的情况下，除第一个消息为均按优先级进行消费(第一个消息被及时消费掉了) 
	     * 3、可在方式二的基础上不断增加消费者，也符合优先调用规则 
	     * 注意要点： 
	     * 1、队列、消息上均要设置优先级才可生效，以较小值为准； 
	     * 2、队列优先级只能声明一次，不可改变(涉及到硬盘、内存存储方式) 
	     * 3、优先级队列在内存、硬盘、cpu会有成本消耗，不建议创建大量的优先级别(数量、级别种类、大级别，理解混乱，英文理解困难...) 
	     * @throws IOException 
	     * @throws TimeoutException 
	     */  
	    public static void priorityRecv() throws IOException, TimeoutException{  
	    	ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("192.168.0.100"); //使用默认端口连接本地rabbitmq服务器
	        factory.setPort(5672);
	        factory.setUsername("admin");
	        factory.setPassword("admin");         
	        Connection connection = factory.newConnection(); //声明一个连接 
	        Channel channel = connection.createChannel();  
	        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.DIRECT);  
	        //设置队列的优先级，消息的优先级大于队列的优先级，以较小值为准(例如：队列优先级5、消息优先级8，消息实际优先级为5)  
	        Map<String, Object> args=new HashMap<String, Object>();  
	        args.put("x-max-priority", 10);//队列优先级只能声明一次，不可改变(涉及到硬盘、内存存储方式)  
	        channel.queueDeclare(queueName, false, false, false, args);  
	        channel.queueBind(queueName, exchange_name, "");  
	        channel.basicQos(1);//需要开启手动应答模式，否则无效  
	        channel.basicConsume(queueName, false, new DefaultConsumer(channel){  
	            @Override  
	            public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body) throws IOException{  
	                String mes = SerializationUtils.deserialize(body).toString();  
	                System.out.println(properties.getPriority()+":priority Received :'"+mes+"' done");    
	                channel.basicAck(envelope.getDeliveryTag(), false);  
	            }  
	        });  
	    }  
	    
	    public static void main(String[] args) throws IOException, TimeoutException {
			priorityRecv();
		}
}
