/*
 * Circle Model.java
 */

import java.util.ArrayList;
import java.lang.Thread;

/**
 * Models a collection of circles roaming about impacting other circles.
 * 
 * @author Amy Larson (with Erik Steinmetz)
 */
public class CircleModel extends Thread {

    private ArrayList<Circle> circles = new ArrayList<>();

    /** Time in ms. "Frame rate" for redrawing the circles. */
    private int stepSize = 200;
    /** Current number of circles visible in the window. */
    private int count = 0;
    /** Pauses simulation so circles do not move */
    private boolean paused = true;

    private SimulationGUI simulation;

    private boolean changeColor = false;

    private boolean changeDirection = false;

    private int seeRadius;

    private double desiredSeperation = 2;

    /** Default constructor. */
    public CircleModel() {
        // All circels that might appear in the graphics window are created, but are not
        // visible.
        for (int i = 0; i < 20; i++) {
            circles.add(new Circle());
        }
    }

    @Override
    public void run() {
        // Forever run the simulation
        while (true) {
            // Move things only if the simulation is not paused
            if (!paused) {
                advanceCircles();
                averagePosition();
                averageDirection();
                //seperation();
                simulation.getContentPane().repaint();
            }
            try {
                Thread.sleep(stepSize);
            } catch (Exception e) {

            }
        }
    }

    /** Pause the simulation - circles freeze. */
    public void pause() {
        paused = true;
    }

    /** Circles move again */
    public void play() {
        System.out.println("Playing now");
        paused = false;

    }

    /** Move circles to next location */
    public void advanceCircles() {
        for (int i = 0; i < count; i++) {
            // Advance each circle
            circles.get(i).step();
            // Set the location, which prompts the viewer to newly display the circle
            // circles.get(i).setLocation(circles.get(i).getXY().x,
            // circles.get(i).getXY().y);
        }
    }

    public ArrayList<Circle> getCircles() {
        return circles;
    }

    public void setSim(SimulationGUI sim) {
        simulation = sim;
    }

    public void setChangeColor(Boolean b) {
        this.changeColor = b;
    }

    public void setChangeDirection(Boolean b) {
        this.changeDirection = b;
    }

    /** Reset circles */
    public void setCount(int circleCount) {
        System.out.println("Making circles!");
        // Must be in bounds. Only 20 circles in the list.
        if (circleCount < 2) {
            circleCount = 2;
        } else if (circleCount > 20) {
            circleCount = 20;
        }
        // Reset "count" circles, making them visible
        count = circleCount;
        for (int i = 0; i < count; i++) {
            circles.get(i).reset();
        }
        // Hide the rest
        for (int i = count; i < 20; i++) {
            circles.get(i).hideCircle();
        }
    }

    /** Set speed of simulation from 1 (slow) to 5 (fast) */
    public void setSpeed(int newSpeed) {
        // speed is between 1 (slow) and 5 (fastest)
        // low speed = high step size
        if (newSpeed < 1) {
            newSpeed = 1;
        } else if (newSpeed > 5) {
            newSpeed = 5;
        }
        stepSize = (6 - newSpeed) * 160; // 80 to 400ms
    }

    public void checkOverlap() {
        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count; j++) {
                if (circles.get(i).overlaps(circles.get(j))) {
                    if (changeColor && changeDirection) {
                        changeDirection(circles.get(i), circles.get(j));
                        changeColor(circles.get(i), circles.get(j));
                    } else if (changeColor) {
                        changeColor(circles.get(i), circles.get(j));
                    } else if (changeDirection) {
                        changeDirection(circles.get(i), circles.get(j));
                    }
                }
            }
        }
    }

    public void changeDirection(Circle circle, Circle other) {
        if (circle.getXY().x == other.getXY().x) {
            circle.setDirectionY(circle.getXY().y * -1);
        }
        if (circle.getXY().y == other.getXY().y) {
            circle.setDirectionX(circle.getXY().x * -1);
        }
    }

    public void changeColor(Circle circle, Circle other) {
        circle.randomColor();
        other.setColor(circle.color());
    }


    /** Calculates the average position of the circles */
    public void averagePosition(){
        int xAverage;
        int yAverage;
        int sumX = 0;
        int sumY = 0;
        for(int i =0; i < count;i++){
            sumX = circles.get(i).getXY().x;
            sumY = circles.get(i).getXY().y;
        }
        xAverage = sumX / count;
        yAverage = sumY / count;

        for(int j = 0; j < count ; j++){
            double changeX = circles.get(j).getXY().x - xAverage;
            double changeY = circles.get(j).getXY().y - yAverage;
            double hyp = Math.pow((Math.pow(changeX,2) + Math.pow(changeY, 2)), 0.5);
            circles.get(j).setDirectionX((circles.get(j).getXDirection() + hyp));
            circles.get(j).setDirectionY((circles.get(j).getYDirection() + hyp));

        }

    }

    public void averageDirection() {
        int xAverage;
        int yAverage;
        int posXAverage;
        int posYAverage;
        int sumX = 0;
        int sumY = 0;
        int posY = 0;
        int posX = 0;
        for (int i = 0; i < count; i++) {
            sumX += circles.get(i).getXDirection();
            sumY += circles.get(i).getYDirection();
            posX += circles.get(i).getXY().x;
            posY += circles.get(i).getXY().y;
        }
        posXAverage = posX / count;
        posYAverage = posY / count;
        xAverage = sumX / count;
        yAverage = sumY / count;


        for (int j = 0; j < count; j++) {
           circles.get(j).setDirectionX(xAverage * 1+ circles.get(j).getXDirection());
             circles.get(j).setDirectionY(yAverage * 1+ circles.get(j).getYDirection());
           int dX = posXAverage - circles.get(j).getXY().x;
           int dY= posYAverage - circles.get(j).getXY().y;
           circles.get(j).setDirectionX(dX+ circles.get(j).getXDirection());
            circles.get(j).setDirectionY(dY+ circles.get(j).getYDirection());
            System.out.println(posXAverage + " "  + posYAverage + " " + dX + " " + dY);
        }
       /*for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count; j++) {
                if (circles.get(i).setDirectionX(circles.get(j))) {
                }*/
    }

    public void seperation(){
        
        double moveX;
        double moveY;

        for(int i = 0; i < count; i++){
            for(int j = 0; j < count; j++){
                if ((circles.get(i).distance(circles.get(j))) <= desiredSeperation){
                    moveX = circles.get(i).getX() - circles.get(j).getX();
                    moveY = circles.get(i).getY() - circles.get(j).getY();
                    circles.get(i).setDirectionX(circles.get(i).getXDirection() + moveX);
                    circles.get(i).setDirectionY(circles.get(i).getYDirection() + moveY);
                }
            }
        }

    }

    
}
