import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.events.Event;

import javax.swing.JPanel;
import javax.swing.JSlider;

import java.awt.Color;
import java.util.ArrayList;

/**
 * The "VIEW" of Model-View-Controller
 * An instance of this gui contains a reference to the Controller and the Model.
 * @author Amy Larson (with Erik Steinmetz)
 */
public class SimulationGUI extends JFrame {

    // Controller GUI Components
    private final JLabel countLabel = new JLabel("Circles (2-20): ");
    protected final JTextField count = new JTextField(10);

    private final JLabel speedLabel = new JLabel("Speed (1-5): ");
    protected final JTextField speed = new JTextField(10);

    private final JButton stop = new JButton("Stop");
    private final JButton play = new JButton("Play");
    private final JButton restart = new JButton("Set Up");

    private final JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL,20,60,30);

    private final JCheckBox checkDirection = new JCheckBox("Change direction");
    private final JCheckBox checkColor = new JCheckBox("Change color");


    private ArrayList<Circle> circles;

    /**
     * Creates a Simulation GUI application.
     * Sets the components and their positions in the gui.
     * Sets the Controller as the buttons' action listener.
     */
    public SimulationGUI(Controller control, CircleModel model) {

        // Initialize the graphics window
        super("Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800,800);
        // You control the layout
        this.getContentPane().setLayout(null);

        // Play Area
        BoundingBox playArea = new BoundingBox(700,600);
        playArea.setLocation(50,150);
        getContentPane().add(playArea);

        // The Circles
        circles = model.getCircles();
        for (Circle circle: circles) {
            circle.setLocation(circle.getXY().x,circle.getXY().y);
            getContentPane().add(circle);
        }
        
        // Controller Display

        // Place the circle count label and text box
        this.countLabel.setBounds(20,20,100,30);
        this.getContentPane().add(this.countLabel);
        
        this.count.setBounds(115, 20, 80, 30);
        this.getContentPane().add(count);
        
        // place the sim speed label and text box
        this.speedLabel.setBounds( 20, 50, 100, 30);
        this.getContentPane().add(this.speedLabel);
        
        this.speed.setBounds(115, 50, 80, 30);
        this.getContentPane().add(this.speed);

        // place the restart button 
        this.restart.setBounds(200, 20, 120, 30);
        this.restart.addActionListener(control);
        this.getContentPane().add(this.restart);
        
        // place the play and stop buttons
        this.play.setBounds(40, 100, 120, 30);
        this.play.addActionListener(control);
        this.getContentPane().add(this.play);
        
        this.stop.setBounds(150, 100, 120, 30);
        this.stop.addActionListener(control);
        this.getContentPane().add(this.stop);
 
        //place the size slider
        this.sizeSlider.setBounds(400, 100,200,60);
        this.sizeSlider.setMajorTickSpacing(10);
        this.sizeSlider.setPaintTicks(true);
        this.sizeSlider.setPaintLabels(true);
        this.getContentPane().add(this.sizeSlider);
        Event e = new Event();
        this.sizeSlider.addChangeListener(e);

    //Testing checkbox
        this.checkColor.setBounds(400, 40, 120, 40);
        this.getContentPane().add(this.checkColor);
        this.checkColor.addItemListener();

        this.checkDirection.setBounds(400, 70, 140, 40);
        this.getContentPane().add(this.checkDirection);
        this.checkDirection.addItemListener(this);
    }

    /**
     * 
     */
    public class Event implements ChangeListener{
       
        @Override
        public void stateChanged(ChangeEvent e) {
            
            int radius = sizeSlider.getValue();
            for(int i = 0; i < circles.size();i++){
                circles.get(i).changeSize(radius);
            }
            
        }
    }

    
}
