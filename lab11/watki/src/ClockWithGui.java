import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.LocalTime;

public class ClockWithGui extends JPanel {

    LocalTime time = LocalTime.now();

    ClockWithGui(){
        new ClockThread().start();
        setOpaque(false);
    }
    public void paintComponent(Graphics g){
        Graphics2D g2d=(Graphics2D)g;
        g2d.translate(getWidth()/2,getHeight()/2);

        for(int i=1;i<13;i++){
            AffineTransform at = new AffineTransform();
            at.rotate(2*Math.PI/12*i);
            Point2D src = new Point2D.Float(0,-120);
            Point2D trg = new Point2D.Float();
            at.transform(src,trg);
            g2d.drawString(Integer.toString(i),(int)trg.getX(),(int)trg.getY());

        }

        AffineTransform saveAT = g2d.getTransform();
        g2d.rotate(time.getHour()*2*Math.PI/60);
        g2d.drawLine(0,0,0,-50);
        g2d.setTransform(saveAT);

        AffineTransform saveMIN = g2d.getTransform();
        g2d.rotate(time.getMinute()*2*Math.PI/60);
        g2d.drawLine(0,0,0,-80);
        g2d.setTransform(saveMIN);

        AffineTransform saveS = g2d.getTransform();
        g2d.rotate(time.getSecond()*2*Math.PI/60);
        g2d.drawLine(0,0,0,-100);
        g2d.setTransform(saveS);


    }

    class ClockThread extends Thread{
        @Override
        public void run() {
            for(;;){
                time = LocalTime.now();
                System.out.printf("%02d:%02d:%02d\n",time.getHour(),time.getMinute(),time.getSecond());

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock");
        frame.setContentPane(new ClockWithGui());
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);


    }
}