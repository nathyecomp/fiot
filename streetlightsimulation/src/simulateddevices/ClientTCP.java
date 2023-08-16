/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulateddevices;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import javax.swing.JOptionPane;


public class ClientTCP {

  public void connect() {

    try {

      Socket cliente = new Socket("192.168.1.102",1099);
        System.out.println("Conectei");
        
        ObjectOutputStream s = new ObjectOutputStream(cliente.getOutputStream());
        
        s.write(22);

     // ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

      //Date data_atual = (Date)entrada.readObject();

      //JOptionPane.showMessageDialog(null,"Data recebida do servidor:" + data_atual.toString());

      //entrada.close();
s.close();
      System.out.println("Conexão encerrada");

    }

    catch(Exception e) {

      System.out.println("Erro: " + e.getMessage());

    }

  }

}

