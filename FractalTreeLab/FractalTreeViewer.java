import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Fractal Tree Viewer
 * 
 * @author @bnmathews
 * @version 30 March 2015
 */
public class FractalTreeViewer implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener
{
    // the dimensions of the window itself
    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;

    // the minimum and maximum size the tree can get (how many iterations it can go through)
    private final int MIN = 1, MAX = 15;

    // create button instance variables
    private JButton increase, decrease, animate, stop, color, epilepsy, loop,
    m1, m2, m3, m4, m5, m6, m7, m8, m9, m10;
    
    // create label instance variables
    private JLabel titleLabel, statusLabel, orderLabel, modLabel, epiLabel, speedLabel, loopLabel;
    
    // create an instance variable for the tree drawing
    private TreePanel drawing;

    // create panel instance variables
    private JPanel panel, tools, tools2;
    
    // create an instance variable for the frame
    private JFrame frame;

    // the value passed in to the modifiers to act on the tree's positioning
    private int mod;

    // the number of times the mouse's position has been checked
    private int updates = 0;

    // the previous Y positon of the mouse
    private int previousY;

    // the previous X position of the mouse
    private int previousX;

    // the mouse button pressed
    private int buttonPressed;

    // the current mode the program is in - either "Animation" or "Still"
    private String mode;

    // the delay between frames of animation (makes this kinda misleading)
    private int animSpeed = 10;

    // an array of boolean values pertaining to which modifiers are enabled or disabled (10 in total)
    private boolean[] modStatus = new boolean[10];

    // the current status of 'epilepsy' (randomly generated colors for every frame) mode
    private boolean epilepsyMode;

    // if the loop is enabled, this checks if the mod value has increased enough times and should begin reversing
    private boolean reverseMod = false;

    // if the mod value should reverse and then increase again in a loop
    private boolean shouldLoop = true;

    // how many times the user wants there to be in between reversing the mod value
    private int loopTimes = 3;
    
    /**
     * Runs the interesting part of the program
     *
     */
    public static void main(String[] args)
    throws InterruptedException
    {
        // make the tree viewer
        FractalTreeViewer viewer = new FractalTreeViewer();
    }

    /**
     * Constructor for the fractal tree viewer, sets up the actual ui as well as its components
     *
     * @post    the viewer will be created and will have generated a fractal tree
     * 
     */
    public FractalTreeViewer()
    throws InterruptedException
    {
        // sets the initial state to paused
        mode = "Still";
        
        // flashing colors are disabled
        epilepsyMode = false;

        // sets all the modifiers to an inactive state
        boolean[] modStatus = {false,false,false,false,false,false,false,false,false,false};

        // initialize the tools panel, this one will have readouts of what the user is doing
        tools = new JPanel();
        tools.setLayout (new BoxLayout(tools,BoxLayout.Y_AXIS));
        tools.setOpaque (false);

        // initialize the other tools panel, this will have the controls
        tools2 = new JPanel();
        tools2.setOpaque (false);

        // labels are pretty self explanatory, they display various things
        titleLabel = new JLabel ("Fractal Tree");
        titleLabel.setForeground (Color.white);

        statusLabel = new JLabel ("Current Mode: " + mode);
        statusLabel.setForeground (Color.white);

        speedLabel = new JLabel ("Animation Delay: " + animSpeed);
        speedLabel.setForeground (Color.white);

        loopLabel = new JLabel ("Reverse Loop: on - Times Before Loop: " + loopTimes );
        loopLabel.setForeground (Color.white);

        // set up the button for changing the color of the tree
        color = new JButton (new ImageIcon ("color.gif"));
        color.setBorder(null);
        color.setContentAreaFilled(false);
        color.setPressedIcon (new ImageIcon ("color_down.gif"));
        color.setMargin (new Insets (0, 0, 0, 0));
        color.addActionListener (this);

        // set up the button for enabling and disabling "epilepsy mode"
        epilepsy = new JButton (new ImageIcon ("epilep.gif"));
        epilepsy.setSelectedIcon( new ImageIcon ("epilep_on.gif"));
        epilepsy.setBorder(null);
        epilepsy.setContentAreaFilled(false);
        epilepsy.setPressedIcon (new ImageIcon ("epilep_down.gif"));
        epilepsy.setMargin (new Insets (0, 0, 0, 0));
        epilepsy.addActionListener (this);

        // set up the play button
        animate = new JButton (new ImageIcon ("anim.gif"));
        animate.setSelectedIcon( new ImageIcon ("anim_on.gif"));
        animate.setSelected(false);
        animate.setBorder(null);
        animate.setContentAreaFilled(false);
        animate.setPressedIcon (new ImageIcon ("anim_down.gif"));
        animate.setMargin (new Insets (0, 0, 0, 0));
        animate.addActionListener (this);

        // set up the pause button - enabled by default
        stop = new JButton (new ImageIcon ("stop.gif"));
        stop.setSelectedIcon( new ImageIcon ("stop_on.gif"));
        stop.setSelected(true);
        stop.setBorder(null);
        stop.setContentAreaFilled(false);
        stop.setPressedIcon (new ImageIcon ("stop_down.gif"));
        stop.setMargin (new Insets (0, 0, 0, 0));
        stop.addActionListener (this);

        // set up a button for toggling whether or not the tree's animation should reverse after a period of time - enabled by default
        loop = new JButton (new ImageIcon ("loop.gif"));
        loop.setSelectedIcon( new ImageIcon ("loop_on.gif"));
        loop.setSelected(true);
        loop.setBorder(null);
        loop.setContentAreaFilled(false);
        loop.setPressedIcon (new ImageIcon ("loop_down.gif"));
        loop.setMargin (new Insets (0, 0, 0, 0));
        loop.addActionListener (this);

        // all the 'm's are the modifier check boxes, they are used to enable or disable a modifier that will change how the tree
        // is generated
        m1 = new JButton (new ImageIcon ("checkbox.gif"));
        m1.setSelectedIcon( new ImageIcon ("checkbox_on.gif"));
        m1.setSelected(false);
        m1.setBorder(null);
        m1.setContentAreaFilled(false);
        m1.setPressedIcon (new ImageIcon ("checkbox_down.gif"));
        m1.setMargin (new Insets (0, 0, 0, 0));
        m1.setActionCommand("0"); // this is used to let the program know what number the modifier is later on
        m1.addActionListener (this);

        m2 = new JButton (new ImageIcon ("checkbox.gif"));
        m2.setSelectedIcon( new ImageIcon ("checkbox_on.gif"));
        m2.setSelected(false);
        m2.setBorder(null);
        m2.setContentAreaFilled(false);
        m2.setPressedIcon (new ImageIcon ("checkbox_down.gif"));
        m2.setMargin (new Insets (0, 0, 0, 0));
        m2.setActionCommand("1");
        m2.addActionListener (this);

        m3 = new JButton (new ImageIcon ("checkbox.gif"));
        m3.setSelectedIcon( new ImageIcon ("checkbox_on.gif"));
        m3.setSelected(false);
        m3.setBorder(null);
        m3.setContentAreaFilled(false);
        m3.setPressedIcon (new ImageIcon ("checkbox_down.gif"));
        m3.setMargin (new Insets (0, 0, 0, 0));
        m3.setActionCommand("2");
        m3.addActionListener (this);

        m4 = new JButton (new ImageIcon ("checkbox.gif"));
        m4.setSelectedIcon( new ImageIcon ("checkbox_on.gif"));
        m4.setSelected(false);
        m4.setBorder(null);
        m4.setContentAreaFilled(false);
        m4.setPressedIcon (new ImageIcon ("checkbox_down.gif"));
        m4.setMargin (new Insets (0, 0, 0, 0));
        m4.setActionCommand("3");
        m4.addActionListener (this);

        m5 = new JButton (new ImageIcon ("checkbox.gif"));
        m5.setSelectedIcon( new ImageIcon ("checkbox_on.gif"));
        m5.setSelected(false);
        m5.setBorder(null);
        m5.setContentAreaFilled(false);
        m5.setPressedIcon (new ImageIcon ("checkbox_down.gif"));
        m5.setMargin (new Insets (0, 0, 0, 0));
        m5.setActionCommand("4");
        m5.addActionListener (this);

        m6 = new JButton (new ImageIcon ("checkbox.gif"));
        m6.setSelectedIcon( new ImageIcon ("checkbox_on.gif"));
        m6.setSelected(false);
        m6.setBorder(null);
        m6.setContentAreaFilled(false);
        m6.setPressedIcon (new ImageIcon ("checkbox_down.gif"));
        m6.setMargin (new Insets (0, 0, 0, 0));
        m6.setActionCommand("5");
        m6.addActionListener (this);

        m7 = new JButton (new ImageIcon ("checkbox.gif"));
        m7.setSelectedIcon( new ImageIcon ("checkbox_on.gif"));
        m7.setSelected(false);
        m7.setBorder(null);
        m7.setContentAreaFilled(false);
        m7.setPressedIcon (new ImageIcon ("checkbox_down.gif"));
        m7.setMargin (new Insets (0, 0, 0, 0));
        m7.setActionCommand("6");
        m7.addActionListener (this);

        m8 = new JButton (new ImageIcon ("checkbox.gif"));
        m8.setSelectedIcon( new ImageIcon ("checkbox_on.gif"));
        m8.setSelected(false);
        m8.setBorder(null);
        m8.setContentAreaFilled(false);
        m8.setPressedIcon (new ImageIcon ("checkbox_down.gif"));
        m8.setMargin (new Insets (0, 0, 0, 0));
        m8.setActionCommand("7");
        m8.addActionListener (this);

        m9 = new JButton (new ImageIcon ("checkbox.gif"));
        m9.setSelectedIcon( new ImageIcon ("checkbox_on.gif"));
        m9.setSelected(false);
        m9.setBorder(null);
        m9.setContentAreaFilled(false);
        m9.setPressedIcon (new ImageIcon ("checkbox_down.gif"));
        m9.setMargin (new Insets (0, 0, 0, 0));
        m9.setActionCommand("8");
        m9.addActionListener (this);

        m10 = new JButton (new ImageIcon ("checkbox.gif"));
        m10.setSelectedIcon( new ImageIcon ("checkbox_on.gif"));
        m10.setSelected(false);
        m10.setBorder(null);
        m10.setContentAreaFilled(false);
        m10.setPressedIcon (new ImageIcon ("checkbox_down.gif"));
        m10.setMargin (new Insets (0, 0, 0, 0));
        m10.setActionCommand("9");
        m10.addActionListener (this);

        // more labels, same deal as before
        orderLabel = new JLabel ("Iteration: 1");
        orderLabel.setForeground (Color.white);

        modLabel = new JLabel ("Mods Enabled: none");
        modLabel.setForeground (Color.white);

        epiLabel = new JLabel ("Epilepsy Mode: off");
        epiLabel.setForeground (Color.white);

        // add all the labels to the first tool panel
        tools.add (titleLabel);
        tools.add (statusLabel);
        tools.add (speedLabel);
        tools.add (loopLabel);
        tools.add (modLabel);
        tools.add (epiLabel);
        tools.add (orderLabel);

        // add everything else to the second tool panel, and create a 300 pixel wide gap in between
        tools2.add (Box.createHorizontalStrut (300));
        tools2.add(color);
        tools2.add(epilepsy);
        tools2.add(loop);
        tools2.add(animate);
        tools2.add(stop);
        tools2.add(m1);
        tools2.add(m2);
        tools2.add(m3);
        tools2.add(m4);
        tools2.add(m5);
        tools2.add(m6);
        tools2.add(m7);
        tools2.add(m8);
        tools2.add(m9);
        tools2.add(m10);

        // initialize the fractal tree object with a starting iteration of 1
        drawing = new TreePanel(1);

        // create the master panel
        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.add (tools);
        panel.add (tools2);
        panel.add (drawing);
        
        // add listeners so the tree can be modified without the use of multiple buttons
        panel.addMouseListener(this); //since our class is already a MouseListener, we can add it here
        panel.addMouseMotionListener(this);
        panel.addMouseWheelListener(this);

        // create the window itself
        frame = new JFrame();
        frame.setTitle("Fractal Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(panel);
        frame.setVisible(true);

        while(true) // generic while loop to constantly update the frame
        {
            if(mode.equals("Animation")) // if an animation should be happening
            {
                // check the current iteration (called 'order' throughout)
                int order = drawing.getOrder(); 
                if (shouldLoop) // if the user wants to have a more clean loop
                {
                    // this segment is good for variants that otherwise do not loop properly, so this increases their deformation
                    // and then decreases it in a loop to make things look a little better
                    if (mod < (359 * loopTimes - 1) && reverseMod == false) // 359 is one full rotation
                    {
                        mod++;
                    }
                    else if (mod >= (359 * loopTimes - 1))
                    {
                        reverseMod = true;
                    }

                    if (mod > 0 && reverseMod == true)
                    {
                        mod--;
                    }
                    else if (mod == 0 && reverseMod == true)
                    {
                        reverseMod = false;
                    }
                }
                else // if the variant is already looping, it will look just fine unlooped
                {
                    if (mod < 359000) // for the variants that don't loop properly, this will give them 1000 runs
                    {
                        mod++;
                    }
                    else
                    {
                        mod = 0;
                    }
                }

                // send the TreePanel information about the current iteration, the modification value, the status of the modifiers themselves, and 
                // whether or not epilepsy mode is enabled
                drawing.setStuff (order,mod,updateModStatus(),epilepsyMode);
                
                // create a delay in the animation
                Thread.sleep(animSpeed);
                
                // redraw the updated graphics
                frame.repaint();
            }
        }
    }
    
    /**
     * Used as a way for the constructor to gain access to a modified record of the status of the modifiers
     * 
     * @pre     the modifiers have all been initialized with starting states
     * @return  an array of boolean values that represent the status of the modifers
     * 
     */
    private boolean[] updateModStatus()
    {
        return modStatus;
    }
    
    /**
     * Makes a string of all the modifiers currently in use, otherwise it will return "none"
     * 
     * @pre     the modifiers are initialized
     * @return  a String representing the currently enabled modifiers
     * 
     */
    private String getMods()
    {
        String modList = "";
        boolean hasFoundTrue = false;
        for (int i = 0; i < modStatus.length; i++) // go through each of the mods
        {
            if (modStatus[i] == true) // if the mod is enabled, add its number to the string representing enabled mods
            {
                // let the program know that there is at least one enabled mod
                hasFoundTrue = true;
                
                if (i != 0) // cleans up the presentation a little bit
                {
                    modList += " "+(i+1);
                }
                else
                {
                    modList += i+1;
                }
            }
        }

        if (hasFoundTrue == false) // if there are no mods enabled, returning "none" looks prettier than returning an empty string 
        {
            modList = "none";
        }

        // return the new string
        return modList;
    }

    /**
     * Determines what button is pushed and what to do when said button is pushed
     * 
     * @param   e the action event performed
     * @pre     all buttons are created
     * @post    depending on the button pressed, the program will execute a certain command
     * 
     */
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == animate) // if the animation button is clicked, set everything to "Animation" mode
        {
            // uncheck the stop button, as there can only be one mode at a time
            stop.setSelected(false);
            
            // select the animation button
            animate.setSelected(true);
            
            // set the mode to "Animation"
            mode = "Animation";
        }
        else if (e.getSource() == stop) // if the stop button is clicked, set the mode to "Still"
        {
            // select the stop button
            stop.setSelected(true);
            
            // deselect the animation button
            animate.setSelected(false);
            
            // set the mode to "Still"
            mode = "Still";
        }
        else if (e.getSource() == loop) // if the loop button is clicked, turn on looping
        {
            if (shouldLoop) // if the button is clicked when looping is already on
            {
                // disable looping
                shouldLoop = false;
                
                // deselect the button
                loop.setSelected(false);
                
                // update the loop label with the current loop status
                loopLabel.setText("Reverse Loop: off - Times Before Loop: " + loopTimes);
            }
            else // if the button is clicked while looping is off, turn looping on
            {
                shouldLoop = true;
                loop.setSelected(true);
                loopLabel.setText("Reverse Loop: on - Times Before Loop: " + loopTimes);
            }    
        }
        else if (e.getSource() == color) // if the color button is clicked, change the tree's color
        {
            // calls the TreePanel's method to generate a random color for itself
            drawing.makeNewColor();
            
            // updates the graphics
            frame.repaint();
        }
        else if (e.getSource() == epilepsy) // if the epilepsy mode button is clicked
        {
            if (epilepsyMode == false) // if e. mode is off, enable it
            {
                // set epilepsy mode to on
                epilepsyMode = true;
                
                // select the epilepsy mode button
                epilepsy.setSelected(true);
                
                // update the readout for epilepsy mode
                epiLabel.setText("Epilepsy Mode: on");
            }
            else // if e. mode is already on, disable it
            {
                epilepsyMode = false;
                epilepsy.setSelected(false);
                epiLabel.setText("Epilepsy Mode: off");
            }
        }
        else if (e.getSource() == m1 || e.getSource() == m2 || e.getSource() == m3 || e.getSource() == m4
        || e.getSource() == m5 || e.getSource() == m6 || e.getSource() == m7 || e.getSource() == m8
        || e.getSource() == m9 || e.getSource() == m10) // if any of the checkboxes for the modifiers are clicked, turn the modifier on
        {
            // the actual checkbox clicked
            JButton btn = ((JButton)e.getSource());
            
            if (!btn.isSelected()) // if the box is not currently checked
            {
                // check the box
                btn.setSelected(true);
                
                // get the name of the button, or in this case, the number of the modifier described by the button's actioncommand
                String btnName = btn.getActionCommand();
                
                // convert the button's name
                int modNumber = Integer.parseInt(btnName);
                
                // change the modifier's status to enabled in the array of mod statuses
                modStatus[modNumber] = true;
            }
            else // if the box selected is already checked
            {
                // get the mod's number
                String btnName = btn.getActionCommand();
                
                // convert the mod's number in string form to an integer
                int modNumber = Integer.parseInt(btnName);
                
                // set the modifier's status to off in the mod status array
                modStatus[modNumber] = false;
                
                // uncheck the box
                btn.setSelected(false);
            }
        }
        // update the status label
        statusLabel.setText ("Current Mode: " + mode);
        
        // update the mod label
        modLabel.setText ("Mods Enabled: " + getMods());
    }
    
    /**
     * Checks if the user has moved the mouse wheel up or down, and changes the speed of the animation based on that
     * 
     * @param e the mouse wheel event
     * 
     */
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        // update the animation delay label (more like reverse speed)
        speedLabel.setText("Animation Delay: " + animSpeed);
        
        if (e.getWheelRotation() < 1) // if the user has scrolled up
        {
            // increase the delay of the animation
            animSpeed++;
        }
        else // if the user scrolls down
        {
            // decrease the animation delay until it hits zero
            if (animSpeed > 0)
                animSpeed--;
        }
    }
    
    
    /**
     * Gets the current mouse button pressed at any time
     * 
     * @param e the mouse event
     * 
     */
    public void mousePressed(MouseEvent e)
    {
        buttonPressed = e.getButton();
    }
    
    public void mouseClicked(MouseEvent e)
    {}
    public void mouseReleased(MouseEvent e)
    {}
    public void mouseEntered(MouseEvent e)
    {}
    public void mouseExited(MouseEvent e)
    {}
    
    public void mouseMoved(MouseEvent e)
    {}

    /**
     * Performs actions based on if the user holds down a mouse button and drags the mouse
     * 
     * @ param e the mouse event
     * 
     */
    public void mouseDragged(MouseEvent e)
    {
        // check the current iteration of the tree
        int order = drawing.getOrder();
        
        if (buttonPressed == e.BUTTON1) // if the left mouse button is clicked and the mouse is dragged...
        {
            // record the current y value of the mouse
            int y = (int)e.getPoint().getY();
            
            if (updates == 1) // if the mouse's position has not yet been recorded in since the program started, this gives an initial value
            {
                // set the record of the previous y position to the current one, but just this time 
                previousY = y;
                
                // increase the iteration of the tree drawing (make it grow upwards one time)
                order++;
            }
            
            // when the mouse is moved, this compares its current y value to the last y value
            if (y < previousY) // if the mouse is moved upwards
            {
                if (updates % 2 == 0) // sets the mouses previous y value every other time it checks for the mouses current location
                {
                    previousY = y;
                }
                
                // make the tree grow once
                order++;
            }
            else // if the mouse is moved downwards
            {
                if (updates % 2 == 0)
                {
                    previousY = y;
                }
                
                // shrink the tree
                order--;
            }

            if (order >= MIN && order <= MAX) // redraw the tree using the new iteration, providing it is within the boudaries of how big or small it can get
            {
                // update the iteration label
                orderLabel.setText ("Iteration: " + order);
                
                // send new data to the TreePanel so it can redraw the tree
                drawing.setStuff (order,mod,modStatus,epilepsyMode);
                
                // update the graphics
                frame.repaint();
            }
        }
        else if (buttonPressed == e.BUTTON3) // if the user holds down the right mouse button and drags...
        {
            // keep track of the mouse's current x value
            int x = (int)e.getPoint().getX();

            if (updates == 1) // if the previous x value has not yet been recorded, do so here
            {
                previousX = x;
            }

            // works the same way as the growing or shinking of the tree, only this uses the x coordinates instead of the y
            if (x < previousX) // if the mouse is moved to the left
            {
                if (updates % 2 == 0) // sets the record of the mouse's last x coordinate every other time
                {
                    previousX = x;
                }
                
                // incerease the modification value for the modifiers to use
                mod++;
            }
            else // if the mouse is moved to the right
            {
                if (updates % 2 == 0)
                {
                    previousX = x;
                }
                mod--;
            }

            // update the iteration label
            orderLabel.setText ("Iteration: " + order);
            
            // send the new data to the TreePanel
            drawing.setStuff (order,mod,modStatus,epilepsyMode);
            
            // update the graphics
            frame.repaint();
        }
        else if (buttonPressed == e.BUTTON2) // if the middle mouse button is held and the mouse is dragged...
        {
            // get the mouse's current y value
            int y = (int)e.getPoint().getY();

            // this works the same way as before, only it modifies the number of times the tree's animation will run before reversing in its loop
            if (y < previousY) // if the mouse is moved upwards
            {
                if (updates % 2 == 0)
                {
                    previousY = y;
                }
                loopTimes++;
            }
            else // if the mouse is moved downwards
            {
                if (updates % 2 == 0)
                {
                    previousY = y;
                }
                if (loopTimes > 1) // must at least loop once
                    loopTimes--;
            }
            
            // if looping is enabled or disabled, update the loop label accordingly
            if (shouldLoop) 
            {
                loopLabel.setText("Reverse Loop: on - Times Before Loop: " + loopTimes);
            }
            else
            {
                loopLabel.setText("Reverse Loop: off - Times Before Loop: " + loopTimes);
            }
        }

        // increase the number of times the mouse's position has been recorded in this method
        updates++;
    }
}
