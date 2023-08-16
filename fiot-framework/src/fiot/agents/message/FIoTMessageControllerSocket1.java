/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.agents.message;

import fiot.agents.FIoTAgent;
import fiot.agents.message.quote.QuoteServerThread;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathalia
 */
public class FIoTMessageControllerSocket1 extends FIoTMsgController{

    int port = 9876;
    //DatagramSocket dsForReceiver;
    QuoteServerThread quoteServer;

    public FIoTMessageControllerSocket1(FIoTAgent agent) {
      //  super(agent,type);
        //this.type = "SOCKET";
        this.setType("SOCKET");
        this.port = Integer.valueOf(agent.getAdress().getPort());
        try {
            this.quoteServer = new QuoteServerThread(agent.getAdress().getName(), port);
        } catch (IOException ex) {
            Logger.getLogger(FIoTMessageControllerSocket1.class.getName()).log(Level.SEVERE, null, ex);
        }
//        try {
//            dsForReceiver = new DatagramSocket(this.port);
//        
//        } catch (SocketException ex) {
//            Logger.getLogger(FIoTMessageControllerSocket1.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
//        @Override
//    public FIoTMessageControllerSocket getInstance(FIoTAgent a) {
//        this.port = Integer.valueOf(a.getPort());
//        try {
//            dsForReceiver = new DatagramSocket(this.port);
//        } catch (SocketException ex) {
//            Logger.getLogger(FIoTMessageControllerSocket.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return this;
// 
//        
//    }
    
    private FIoTMessage readOnQuoteSocket(){
       // this.quoteServer.setFiotMessage(null);
       // this.quoteServer.start();
        FIoTMessage msg = this.quoteServer.read();
       // System.out.println("JÁ NO FIO MESSA CONTROLLER "+ msg.getContent()+" -- "+ msg.getIp());
//        while(msg == null){
//            msg = this.quoteServer.getFiotMessage();
//        }
        return msg;
    }
    @Override
    public FIoTMessage readFIoTMessage() {
        //try {
            return this.readOnQuoteSocket();
//            //return this.readMsgOnSocket();
//        } catch (UnknownHostException ex) {
//            Logger.getLogger(FIoTMessageControllerSocket1.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(FIoTMessageControllerSocket1.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //return null;
    }

    @Override
    public boolean sendFIoTMessage(FIoTMessage msg) {
        if(msg.getAddress()==null){
            InetAddress address;
            try {
                address = InetAddress.getByName(msg.getIp());
                msg.setAddress(address);
            } catch (UnknownHostException ex) {
                Logger.getLogger(FIoTMessageControllerSocket1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //try {
            this.quoteServer.sendMessage(msg);
          //  this.sendMsgOnSocket(msg.getIp(), msg.getPort(), msg.content);
//        } catch (SocketException ex) {
//            Logger.getLogger(FIoTMessageControllerSocket1.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(FIoTMessageControllerSocket1.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return false;
    }

//    private void sendMsgOnSocket(String adress, String portSend, String message) throws UnknownHostException, SocketException, IOException {
//        byte[] msg = message.getBytes();
//        InetAddress addr = InetAddress.getByName(adress);
////
////      //Monta o pacote a ser enviado
////
//        DatagramPacket pkg = new DatagramPacket(msg, msg.length, addr,  Integer.valueOf(portSend));
////
////       // Cria o DatagramSocket que será responsável por enviar a mensagem
////
//        DatagramSocket ds = new DatagramSocket();
////
////       //Envia a mensagem
////
//        ds.send(pkg);
////
////       System.out.println("Mensagem enviada para: " + addr.getHostAddress() + "\n" +
//////
////        "Porta: " + port + "\n" + "Mensagem: " + msg);
////
//        //Fecha o DatagramSocket
//
//        ds.close();
//    }
//    
//    public void finishSocketCommunication(){
//        this.dsForReceiver.close();
//    }
    
//    public void print(){
//        if(dsForReceiver!=null){
//            System.out.println("SOCKET ADRESS ");
//            if(dsForReceiver.getInetAddress()!=null){
//            System.out.println("Inet adress canonical"+ dsForReceiver.getInetAddress().getCanonicalHostName());
//            System.out.println("Inet adress host adress"+ dsForReceiver.getInetAddress().getHostAddress());
//            System.out.println("Inet adress host name"+ dsForReceiver.getInetAddress().getHostName());
//            }
//            System.out.println("local port"+ dsForReceiver.getLocalPort());
//            System.out.println("port"+ dsForReceiver.getPort());
//            
//            System.out.println("Local adress host adress"+ dsForReceiver.getLocalAddress().getHostAddress());
//            System.out.println("Remote Socket Adress"+ dsForReceiver.getRemoteSocketAddress().toString());
//             System.out.println("Local Socket Adress"+ dsForReceiver.getLocalSocketAddress().toString());
//        }
//    }

   // private FIoTMessage readMsgOnSocket() throws SocketException, UnknownHostException, IOException {
//      //  int port = 9876;
//        
//        //Cria o DatagramSocket para aguardar mensagens, neste momento o método fica bloqueando
//        //até o recebimente de uma mensagem
//        int cont = 0;
//
//     //   System.out.println("Ouvindo a porta: " + port);
//
//       // System.out.println("Local host: " + InetAddress.getLocalHost());
//        byte[] msg = new byte[256];
//
//        //Prepara o pacote de dados
//        DatagramPacket pkg = new DatagramPacket(msg, msg.length);
//
//      //  this.print();
//        
//        //Recebimento da mensagem
//        dsForReceiver.receive(pkg);
//        
//       // this.print();
//        System.out.println("PORT EM RECEIVER "+ pkg.getPort());
//        FIoTMessage m = new FIoTMessage();
//        //       System.out.println("Received from "+ ds.getInetAddress().getHostAddress());
//        String content = new String(pkg.getData()).trim();
//        m.setContent(content);
//     //   System.out.println("Msg: " + m);
//        String ipThing = pkg.getAddress().getHostAddress();
//       // System.out.println("Address " + ipThing);
//        m.setIp(ipThing);
//        int portReceiver = pkg.getPort();
//        m.setPort(String.valueOf(portReceiver));
//        //System.out.println("SAINDO DE READSOCKET -------------------");
//        return m;
//    }



    
}
