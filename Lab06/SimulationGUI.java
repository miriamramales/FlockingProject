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

    private final JSlider coheranceSlide = new JSlider(JSlider.HORIZONTAL,1,50,25);
    private final JSlider seperationSlide = new JSlider(JSlider.HORIZONTAL,1,50,25);
    private final JSlider alignmentSlide = new JSlider(JSlider.HORIZONTAL,1,50,25);
    private final JSlider visualRangeSlide = new JSlider(JSlider.HORIZONTAL,10,200,100);


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

        //place the coherance slider
        this.coheranceSlide.setBounds(580, 20,180,40);
        //this.coheranceSlide.setMajorTickSpacing(50);
        //this.coheranceSlide.setPaintTicks(true);
        //this.coheranceSlide.setPaintLabels(true);
        this.getContentPane().add(this.coheranceSlide);
        this.coheranceSlide.addChangeListener(control);

        //place seperation slider
        this.seperationSlide.setBounds(580, 90,180,40);
        //this.seperationSlide.setMajorTickSpacing(50);
        //this.seperationSlide.setPaintTicks(true);
        //this.seperationSlide.setPaintLabels(true);
        this.getContentPane().add(this.seperationSlide);
        this.seperationSlide.addChangeListener(control);

        //place alignment slider
        this.alignmentSlide.setBounds(360, 20,180,40);
        this.getContentPane().add(this.alignmentSlide);
        this.alignmentSlide.addChangeListener(control);

        //place alignment slider
        this.visualRangeSlide.setBounds(360, 90,180,40);
        this.getContentPane().add(this.visualRangeSlide);
        this.visualRangeSlide.addChangeListener(control);

    }

    

    
}
