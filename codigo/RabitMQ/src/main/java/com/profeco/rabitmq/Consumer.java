/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.rabitmq;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public abstract class Consumer {
    private String queue = "cola1";
    private  Connection connection;
    public Consumer(){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            connection = factory.newConnection();
            factory.setHost("localhost");
        } catch (IOException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void start() throws IOException, TimeoutException{
        
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(queue, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, this.queue, "");
        
        //Recibir mensaje
        channel.basicConsume(queueName, true, (String string, Delivery message) -> {
            String m = new String(message.getBody(), "UTF-8");
            System.out.println("I just received a message = " + m);
            this.funcionExtra(m);
        }, consumerTag -> {});


    }
    
    public abstract void funcionExtra(String mensaje);
    
    public void close() throws IOException{
        this.connection.close();
    }


}
