package controller_classes;

import entities.Background;
import entities.Star;
import model_classes.StarsManager;
import view_classes.Animation;

import javax.swing.*;
import java.awt.*;

public class Simulation extends JPanel{
    private StarsManager starsManager;
    private static final int CANVAS_WIDTH = 720;
    private static final int CANVAS_HEIGHT = 720;
    private final int DRAWING_DELAY = 10; //milliseconds
    private double METERS_PER_PIXEL;
    private int SECONDS_PER_TIMESTEP;

    public Simulation(Star[] starsArray){

        this.starsManager = new StarsManager(starsArray);

        METERS_PER_PIXEL = starsManager.decideScaling(CANVAS_HEIGHT/2);
        SECONDS_PER_TIMESTEP = (int) starsManager.decideTimeScaling();
        starsManager.decideRadiiScaling();

        Background background = new Background(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK);

        Animation animation = new Animation(background, CANVAS_WIDTH, CANVAS_HEIGHT, starsManager.getStarsArray()
                , METERS_PER_PIXEL);
        this.setLayout(new BorderLayout());
        this.add(animation, BorderLayout.CENTER);

    }

    public void init(){
        initializeFrame();
        new Thread(() -> {
            // Buffer time to load
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (true) {
                updatePosition();
                repaint();

                try {
                    Thread.sleep(DRAWING_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private void updatePosition(){
        starsManager.updateAllForces(SECONDS_PER_TIMESTEP);
    }

    private void initializeFrame(){
        JFrame frame = new JFrame("2D-Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.setContentPane(this);
        frame.pack();
        frame.setVisible(true);
    }
}
