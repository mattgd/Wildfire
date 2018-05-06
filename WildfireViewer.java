/**
 * @WildfireViewer.java
 *
 * @author mattgd
 * @version 1.03 2016/2/1
 */
 
/*
* This file originally spawned from Stanford's Nifty Assignments webpage.  This viewer is meant
* to work in conjuntion with Wildfire.java
*/
 
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;
 
class WildfireViewer {
     
    private int DIM = 75;
    private int SIZE = 6;
    private Color[] COLORS = new Color[]{Color.yellow, Color.green, Color.red, Color.blue};
     
    private JFrame window;
    private JPanel canvas;
    private Wildfire forest;
     
    public WildfireViewer() {
        forest = new Wildfire(DIM);         
        window = new JFrame("Wildfire");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        canvas = new JPanel() {
            public void paintComponent(Graphics g) {
                int[][] tempForest = forest.getForest();
                for (int i = 0; i < tempForest.length; i++) {
                    for (int j = 0; j < tempForest[i].length; j++) {
                        g.setColor(COLORS[tempForest[i][j]]);
                        g.fillRect(SIZE * i, SIZE * j, SIZE, SIZE);
                    }
                }
                 
                g.setColor(Color.black);
                for (int i = 0; i <= tempForest.length; i++) {
                    g.drawLine(0, SIZE * i,(DIM + 2) * SIZE, SIZE * i);
                    g.drawLine(SIZE * i, 0, SIZE * i,(DIM + 2) * SIZE);
                }
            }
        };
         
        canvas.setPreferredSize(new Dimension(SIZE * (DIM + 2), SIZE * (DIM + 2)));
         
        window.add(canvas);
         
        window.pack();
        window.setVisible(true);
    }
         
    public void show() {
        forest.applySpread();
        window.repaint();
    }
     
    public static void main (String[] args) {
        WildfireViewer wildfire = new WildfireViewer();
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                wildfire.show();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }
}
