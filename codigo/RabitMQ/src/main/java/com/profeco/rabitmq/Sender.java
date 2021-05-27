/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.rabitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class Sender {
    private String queue = "cola1";
    private  Connection connection;
    public Sender(){
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
    
    public void enviar(String mensaje){
        try {

            Channel channel = connection.createChannel();
            channel.exchangeDeclare(queue, "fanout");
            
            //Enviando el mensaje
            channel.basicPublish(queue, "", null, mensaje.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void close() throws IOException{
        this.connection.close();
    }

}
