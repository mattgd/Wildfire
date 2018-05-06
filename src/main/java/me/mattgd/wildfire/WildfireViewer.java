package me.mattgd.wildfire;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;

/**
 * This file originally spawned from Stanford's Nifty Assignments web page.  This viewer is meant
 * to work in conjunction with Wildfire.java.
 *
 * @author mattgd
 * @version 1.03 2016/2/1
 */
class WildfireViewer {
     
    private final int DIM = 75;
    private final int SIZE = 6;
    /** The wildfire burn step Colors */
    private final Color[] COLORS = new Color[]{ Color.yellow, Color.green, Color.red, Color.blue };
     
    private final JFrame window;
    private final Wildfire forest;
     
    private WildfireViewer() {
        forest = new Wildfire(DIM);         
        window = new JFrame("Wildfire");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel canvas = new JPanel() {
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
                    g.drawLine(0, SIZE * i, (DIM + 2) * SIZE, SIZE * i);
                    g.drawLine(SIZE * i, 0, SIZE * i, (DIM + 2) * SIZE);
                }
            }
        };
         
        canvas.setPreferredSize(new Dimension(SIZE * (DIM + 2), SIZE * (DIM + 2)));
         
        window.add(canvas);
         
        window.pack();
        window.setVisible(true);
    }
         
    private void show() {
        forest.applySpread();
        window.repaint();
    }
     
    public static void main (String[] args) {
        WildfireViewer wildfire = new WildfireViewer();
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(wildfire::show, 0, 100, TimeUnit.MILLISECONDS);
    }
}
