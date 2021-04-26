package Envia;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dhtey
 */
public class Envia {
//    private String canal;
    private String EXCHANGE_NAME;
    
    public Envia(String canal) {
//        this.canal = canal;
        this.EXCHANGE_NAME = canal;
        try {
            this.envia("Conexion realizada");
        } catch (Exception ex) {
            Logger.getLogger(Envia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void envia(String mensaje) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.0.2.2");
        
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message = mensaje;

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
