package fiot.agents.message.quote;

 
import fiot.agents.message.FIoTMessage;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuoteServerThread{// extends Thread {

    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;

    protected String dataToSend = "";
    protected FIoTMessage fiotMessage = null;
    public QuoteServerThread() throws IOException {
	this("QuoteServerThread",9876);
    }

    public QuoteServerThread(String name, int port) throws IOException {
       // super(name);
        socket = new DatagramSocket(port);

//        try {
//            in = new BufferedReader(new FileReader("one-liners.txt"));
//        } catch (FileNotFoundException e) {
//            System.err.println("Could not open quote file. Serving time instead.");
//        }
    }

    public FIoTMessage read(){
            try {
                this.fiotMessage = null;
                byte[] buf = new byte[256];
                FIoTMessage fiot = new FIoTMessage();

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                //System.out.println("VALOR RECEBIDO FOI "+ received);
                
                
//                if(received.contains("controller1")){
//                    received = "bananaController";
//                }
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                String ip = address.getHostName();
                fiot.setContent(received);
                fiot.setAddress(address);
                fiot.setPort(String.valueOf(port));
                fiot.setIp(ip);
                
                this.fiotMessage = fiot;
            } catch (IOException e) {
                e.printStackTrace();
		//moreQuotes = false;
            }
            
            return this.fiotMessage;
    }
    public void run() {

       // while (moreQuotes) {
//            try {
//                this.fiotMessage = null;
//                byte[] buf = new byte[256];
//                FIoTMessage fiot = new FIoTMessage();
//
//                // receive request
//                DatagramPacket packet = new DatagramPacket(buf, buf.length);
//                socket.receive(packet);
//                String received = new String(packet.getData(), 0, packet.getLength());
//                System.out.println("VALOR RECEBIDO FOI "+ received);
//                
//                
//                if(received.contains("controller1")){
//                    received = "bananaController";
//                }
//
//                // figure out response
////                String dString = null;
////                if (in == null)
////                    dString = new Date().toString();
////                else
////                    dString = getNextQuote();
////
////                buf = dString.getBytes();
//
//		// send the response to the client at "address" and "port"
//                InetAddress address = packet.getAddress();
//                int port = packet.getPort();
//                String ip = address.getHostName();
//                fiot.setContent(received);
//                fiot.setAddress(address);
//                fiot.setPort(String.valueOf(port));
//                fiot.setIp(ip);
//                
//                this.fiotMessage = fiot;
////                packet = new DatagramPacket(buf, buf.length, address, port);
////                socket.send(packet);
//            } catch (IOException e) {
//                e.printStackTrace();
//		//moreQuotes = false;
//            }
      //  }
       // socket.close();
    }
    
    public boolean sendMessage(FIoTMessage msgToSend){
        byte[] buf = new byte[256];
        String content = msgToSend.getContent();
        buf = content.getBytes();
        InetAddress address = msgToSend.getAddress();
        int port = Integer.valueOf(msgToSend.getPort());
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(QuoteServerThread.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    public void closeSocket(){
        socket.close();
    }

    protected String getNextQuote() {
        return this.getDataToSend();
//        String returnValue = null;
//        try {
//            if ((returnValue = in.readLine()) == null) {
//                in.close();
//		moreQuotes = false;
//                returnValue = "No more quotes. Goodbye.";
//            }
//        } catch (IOException e) {
//            returnValue = "IOException occurred in server.";
//        }
//        return returnValue;
    }

    public String getDataToSend() {
        return dataToSend;
    }

    public void setDataToSend(String dataToSend) {
        this.dataToSend = dataToSend;
    }

    public FIoTMessage getFiotMessage() {
        return fiotMessage;
    }

    public void setFiotMessage(FIoTMessage fiotMessage) {
        this.fiotMessage = fiotMessage;
    }
    
    
    
}
