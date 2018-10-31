package com.base.project.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class RabbitMqProduct {
    public static String createMsgToVHost(String msg,String queueName,String exchenge,String vhost) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.27.26.82");
       // factory.setHost("192.168.8.66");
        factory.setPort(5672);
        factory.setUsername("cif");
        factory.setPassword("cif");
        factory.setVirtualHost(vhost);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("cif_x_updateEvent", "fanout", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchenge, "");
        channel.basicPublish("", queueName, null, msg.getBytes());
        System.out.println(" [x] Sent '" + msg + "'");
        return msg;
    }
}
