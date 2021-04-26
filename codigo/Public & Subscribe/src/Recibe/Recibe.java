/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recibe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 *
 * @author Dhtey
 */
public class Recibe {

    private String EXCHANGE_NAME;
    public static String mensaje;
    
    public Recibe(String canal) throws Exception{
        this.EXCHANGE_NAME = canal;
        try{
            recibe();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String recibe() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.0.2.2");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

//        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            Recibe.mensaje = message;
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });

        return mensaje;
    }
}
