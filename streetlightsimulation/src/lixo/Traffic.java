package lixo;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
 
public class Traffic extends JPanel {
    BufferedImage car;
    GeneralPath truck;
    int width = 80;
 
    public Traffic() {
        car = makeCar();
        truck = new GeneralPath();
        Dimension d = getPreferredSize();
        float dx = (width/2 - 20)/2f;
        truck.moveTo(d.width/2+dx, d.height-70);
        truck.lineTo(d.width/2+dx+20, d.height-70);
        truck.lineTo(d.width/2+dx+20, d.height-40);
        truck.lineTo(d.width/2+dx, d.height-40);
        truck.closePath();
    }
 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        double x = (w - width)/2;
        double y = (h - width)/2;
        // Road boundries.
        g2.draw(new Line2D.Double(x, 0, x, y));
        g2.draw(new Line2D.Double(0, y, x, y));
        g2.draw(new Line2D.Double(0, y+width, x, y+width));
        g2.draw(new Line2D.Double(x, y+width, x, h));
        g2.draw(new Line2D.Double(x+width, y+width, x+width, h));
        g2.draw(new Line2D.Double(x+width, y+width, w, y+width));
        g2.draw(new Line2D.Double(x+width, y, w, y));
        g2.draw(new Line2D.Double(x+width, 0, x+width, y));
        // Center stripes.
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
                                     BasicStroke.JOIN_MITER, 8f,
                                     new float[] { 10f, 10f }, 0));
        g2.draw(new Line2D.Double(w/2, 0, w/2, y));
        g2.draw(new Line2D.Double(0, y+width/2, x, y+width/2));
        g2.draw(new Line2D.Double(w/2, y+width, w/2, h));
        g2.draw(new Line2D.Double(x+width, h/2, w, h/2));
        // Vehicles.
        g2.setPaint(Color.red);
        g2.fill(truck);
        int ix = w/2 - (width - car.getWidth())/2;
        g2.drawImage(car, ix, 50, this);
    }
 
    public Dimension getPreferredSize() {
        return new Dimension(400,400);
    }
 
    private BufferedImage makeCar() {
        int w = 20, h = 40, type = BufferedImage.TYPE_INT_RGB;
        BufferedImage car = new BufferedImage(w, h, type);
        Graphics2D g2 = car.createGraphics();
        g2.setBackground(Color.blue);
        g2.clearRect(0,0,w,h);
        g2.setPaint(Color.red);
        g2.drawRect(0,0,w-1,h-1);
        g2.dispose();
        return car;
    }
 
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new Traffic());
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}