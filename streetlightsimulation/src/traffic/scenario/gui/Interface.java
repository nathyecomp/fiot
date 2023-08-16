
package traffic.scenario.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import simulateddevices.DeviceAgent;
import simulateddevices.InitAgent;
import traffic.scenario.model.Edge;
import traffic.scenario.model.World;



/**
 *
 * @author  Nathy
 */
public class Interface extends javax.swing.JFrame {
    
    private MainPanel mainPanel;
    private MapFile mapFile;
    int numNext = 0;

    int paintClick = 0;
    public Interface() {

        initComponents();
     //   setPreferredSize(new java.awt.Dimension(500, 500));
        this.jFileChooser1.setVisible(false);

        this.setTitle("Street Light Simulation");
        mainPanel = new MainPanel() {

            void mouseClicado() {
            //    if(!grafosButton.isEnabled()) grafosButton.setEnabled(true);
                if(!reiniciarGrafo.isEnabled()) reiniciarGrafo.setEnabled(true);

            }
        };        
        jPanel1.add(mainPanel);     
        World.getInstance().setPanel(mainPanel);
        this.setLocation(20, 0);
        pack();

//        origem.removeAllItems();
//        origem.addItem("Origem");
//        destino.removeAllItems();
//        destino.addItem("Destino");


    }    

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JToolBar.Separator();
        valueCompletedCars = new javax.swing.JLabel();
        carSelected = new javax.swing.JTextField();
        selectCar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        timeSimulation = new javax.swing.JLabel();
        PAINTBUTTON = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        loadMapButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        loadConfigurationButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        reiniciarGrafo = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JToolBar.Separator();
        nextCycleButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        numcycles = new javax.swing.JLabel();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("..:: NXT Ecomp ::..");
        setResizable(false);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel6.setText("Finished Cars");

        valueCompletedCars.setText("0");

        carSelected.setText("0");

        selectCar.setText("Sel Car");
        selectCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCarActionPerformed(evt);
            }
        });

        jLabel1.setText("Time  Simulation");

        timeSimulation.setText("0");

        PAINTBUTTON.setText("PAINT");
        PAINTBUTTON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PAINTBUTTONActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(2, 2, 2)
                .addComponent(timeSimulation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(valueCompletedCars, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(carSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectCar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PAINTBUTTON)
                .addGap(0, 342, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PAINTBUTTON)
                    .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel6)
                        .addComponent(valueCompletedCars)
                        .addComponent(carSelected, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(selectCar)
                        .addComponent(timeSimulation))))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);
        jToolBar1.setPreferredSize(new java.awt.Dimension(13, 30));

        loadMapButton.setBackground(new java.awt.Color(204, 204, 255));
        loadMapButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        loadMapButton.setText("Load Map");
        loadMapButton.setFocusable(false);
        loadMapButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loadMapButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        loadMapButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMapButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(loadMapButton);
        jToolBar1.add(jSeparator1);
        jToolBar1.add(jSeparator4);

        loadConfigurationButton.setBackground(new java.awt.Color(153, 153, 255));
        loadConfigurationButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        loadConfigurationButton.setText("Load Configurations");
        loadConfigurationButton.setFocusable(false);
        loadConfigurationButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loadConfigurationButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        loadConfigurationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadConfigurationButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(loadConfigurationButton);
        jToolBar1.add(jSeparator2);
        jToolBar1.add(jSeparator6);

        reiniciarGrafo.setText("Limpar");
        reiniciarGrafo.setEnabled(true);
        reiniciarGrafo.setFocusable(false);
        reiniciarGrafo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        reiniciarGrafo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        reiniciarGrafo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reiniciarGrafoActionPerformed(evt);
            }
        });
        jToolBar1.add(reiniciarGrafo);
        jToolBar1.add(jSeparator12);

        nextCycleButton.setBackground(new java.awt.Color(255, 204, 51));
        nextCycleButton.setText("  Next>>  ");
        nextCycleButton.setFocusable(false);
        nextCycleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nextCycleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        nextCycleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextCycleButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(nextCycleButton);
        jToolBar1.add(jSeparator3);

        numcycles.setText("0");
        jToolBar1.add(numcycles);

        jPanel3.add(jToolBar1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nextCycleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextCycleButtonActionPerformed
        // TODO add your handling code here:
         this.numNext = World.getInstance().getActualTimeSimulation(); 
          int completedCarsNumber = World.getInstance().getPanel().getPeopleCompleted();
          this.valueCompletedCars.setText(String.valueOf(completedCarsNumber));
        this.numcycles.setText(String.valueOf(this.numNext));
        World.getInstance().getPanel().setExecute(true);
//        while(true){
//        int time = Integer.valueOf(this.timeOfSimulation.getText());
       
//        if(this.numNext==0){
//            
//            World.getInstance().setTimeSimulation(time);
//        }
//        else if(this.numNext==World.getInstance().getTimeSimulation()){
//            this.numNext = 0;
//        }
       
      //  this.numNext++;
        
//        
//        int cont = 0;
//        while(World.getInstance().isReadOutput()){
//                    
//        }
//        while(cont++<100000){
//            
//        }
//        cont = 0;
//        while(cont++<100000){
//            
//        }
//        cont = 0;
//        while(cont++<100000){
//            
//        }
//        cont = 0;
//        while(cont++<100000){
//            
//        }
//        cont = 0;
//        while(cont++<100000){
//            
//        }
//        cont = 0;
//        while(cont++<100000){
//            
//        }
//        cont = 0;
//        while(cont++<100000){
//            
//        }
//        cont = 0;
//        while(cont++<100000){
//            
//        }
//        cont = 0;
//        while(cont++<100000){
//            
//        }
       // }
      //  nextCycleButton.doClick(); 
       
        
    }//GEN-LAST:event_nextCycleButtonActionPerformed

    private void reiniciarGrafoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reiniciarGrafoActionPerformed
        // TODO add your handling code here:
        this.numNext = 0;
        this.numcycles.setText(String.valueOf(this.numNext));
        this.valueCompletedCars.setText(String.valueOf(0));
        World.getInstance().getPanel().reiniciarGrafo();
     
    }//GEN-LAST:event_reiniciarGrafoActionPerformed

    private void loadConfigurationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadConfigurationButtonActionPerformed
        try {
            // TODO add your handling code here:
          //  int time = Integer.valueOf(this.timeOfSimulation.getText());
          //  World.getInstance().setTimeSimulation(time);
            World.getInstance().getPanel().controlConfiguration(this.getFile());
            this.timeSimulation.setText(String.valueOf(World.getInstance().getTimeSimulation()));
        } catch (IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_loadConfigurationButtonActionPerformed

    private void loadMapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMapButtonActionPerformed
        try {
            // TODO add your handling code here:
            
            //        this.jFileChooser1.setVisible(true);
            //
            //        String pathMap = jFileChooser1.getSelectedFile().getAbsolutePath();
            
            
           World.getInstance().getPanel().controlMap(this.getFile());
        } catch (IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_loadMapButtonActionPerformed

    private void selectCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectCarActionPerformed
        // TODO add your handling code here:
       World.getInstance().getPanel().setPersonSelected(this.carSelected.getText().replaceAll(" ", ""));
    }//GEN-LAST:event_selectCarActionPerformed

    private void PAINTBUTTONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PAINTBUTTONActionPerformed
        // TODO add your handling code here:
//        if(this.paintClick==0){
//            World.getInstance().getPanel().setExecute(true);
//        }
//        this.paintClick++;
        boolean draw = World.getInstance().getPanel().isDraw();
        if(draw)
        World.getInstance().getPanel().setDraw(false);
        else
            World.getInstance().getPanel().setDraw(true);
    }//GEN-LAST:event_PAINTBUTTONActionPerformed
        
    public String getFile() {  
        String actualPath = System.getProperty("user.dir"); 
    JFileChooser salvandoArquivo = new JFileChooser(actualPath);  
    int resultado = salvandoArquivo.showSaveDialog(this);  
    if (resultado != JFileChooser.APPROVE_OPTION) {  
        return "";  
    }  
  
    File selectedFile = salvandoArquivo.getSelectedFile();  
    
    return selectedFile.getAbsolutePath();
}  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PAINTBUTTON;
    private javax.swing.JTextField carSelected;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JToolBar.Separator jSeparator14;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton loadConfigurationButton;
    private javax.swing.JButton loadMapButton;
    private javax.swing.JButton nextCycleButton;
    private javax.swing.JLabel numcycles;
    private javax.swing.JButton reiniciarGrafo;
    private javax.swing.JButton selectCar;
    private javax.swing.JLabel timeSimulation;
    private javax.swing.JLabel valueCompletedCars;
    // End of variables declaration//GEN-END:variables
    
}
