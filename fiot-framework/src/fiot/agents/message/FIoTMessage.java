/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.agents.message;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 *Message representation on FIoT Framework
 * Represents a structure with string content, date and adress of message
 * @version 1.0
 * @author Nathalia
 */
public class FIoTMessage {
    //dados de mensagem recebida
    Date date;
    String content;
    String ip;
    String port;
    
    InetAddress address = null;
    //String type;//socket or acl
    
//    //se for mandar a mensagem
//    String ipRecipient; //or the name of agent
//    String portRecipient;
//    
    public FIoTMessage(){
        date = Calendar.getInstance().getTime();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


//    public String getIpRecipient() {
//        return ipRecipient;
//    }
//
//    public void setIpRecipient(String ipRecipient) {
//        this.ipRecipient = ipRecipient;
//    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
//
//    public String getPortRecipient() {
//        return portRecipient;
//    }
//
//    public void setPortRecipient(String portRecipient) {
//        this.portRecipient = portRecipient;
//    }
    
    //the name that will be used to save agents
    public String getIdSender(){
        if(port.isEmpty())
            return ip;
        else return ip+"-"+port;
    }
    public void setIdSender(String idsender){
        if(idsender.contains("-")){
        String[] end = idsender.split("-");

        this.ip = end[0];
        this.port = end[1];
        }
        else{
            this.ip = idsender;
            this.port= "";
        }
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

   
    
    
}
