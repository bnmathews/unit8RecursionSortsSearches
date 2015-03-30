//********************************************************************
//  KochSnowflakeViewer.java       Author: Lewis/Loftus/Cocking
//
//  Demonstrates the use of recursion.
//  @gcschmit (19 July 2014): converted from an applet to an application
//********************************************************************

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FractalTreeViewer implements ActionListener, MouseListener, MouseMotionListener
{
    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;

    private final int MIN = 1, MAX = 15;

    private JButton increase, decrease, animate, stop, color, epilepsy, m1, m2, m3, m4, m5, m6, m7, m8, m9, m10;
    private JLabel titleLabel, statusLabel, orderLabel, modLabel;
    private TreePanel drawing;
    private JPanel panel, tools, tools2;
    private JFrame frame;

    private int mod;
    
    private int updates = 0;
    
    private int previousY;
    
    private int previousX;
    
    private int buttonPressed;
    
    private String mode;
    
    private int animSpeed = 10;
    
    private boolean[] modStatus = new boolean[10];
    
    private boolean epilepsyMode;
    
    //-----------------------------------------------------------------
    //  Sets up the components for the applet.
    //-----------------------------------------------------------------
    public static void main(String[] args)
    throws InterruptedException
    {
        FractalTreeViewer viewer = new FractalTreeViewer();
    }

    public FractalTreeViewer()
    throws InterruptedException
    {
        mode = "Still";
        
        epilepsyMode = false;
        
        boolean[] modStatus = {false,false,false,false,false,false,false,false,false,false};
        
        tools = new JPanel();
        tools.setLayout (new BoxLayout(tools,BoxLayout.Y_AXIS));
        tools.setOpaque (false);
       
        tools2 = new JPanel();
        tools2.setOpaque (false);
        
        titleLabel = new JLabel ("Fractal Tree");
        titleLabel.setForeground (Color.white);
        
        statusLabel = new JLabel ("Current Mode: " + mode);
        statusLabel.setForeground (Color.white);
        
        color = new JButton (new ImageIcon ("color.gif"));
        color.setBorder(null);
        color.setContentAreaFilled(false);
        color.setPressedIcon (new ImageIcon ("color_down.gif"));
        color.setMargin (new Insets (0, 0, 0, 0));
        color.addActionListener (this);
        
        epilepsy = new JButton (new ImageIcon ("epilep.gif"));
        epilepsy.setSelectedIcon( new ImageIcon ("epilep_on.gif"));
        epilepsy.setBorder(null);
        epilepsy.setContentAreaFilled(false);
        epilepsy.setPressedIcon (new ImageIcon ("epilep_down.gif"));
        epilepsy.setMargin (new Insets (0, 0, 0, 0));
        epilepsy.addActionListener (this);
        
        animate = new JButton (new ImageIcon ("anim.gif"));
        animate.setSelectedIcon( new ImageIcon ("anim_on.gif"));
        animate.setSelected(false);
        animate.setBorder(null);
        animate.setContentAreaFilled(false);
        animate.setPressedIcon (new ImageIcon ("anim_down.gif"));
        animate.setMargin (new Insets (0, 0, 0, 0));
        animate.addActionListener (this);
        
        stop = new JButton (new ImageIcon ("stop.gif"));
        stop.setSelectedIcon( new ImageIcon ("stop_on.gif"));
        stop.setSelected(true);
        stop.setBorder(null);
        stop.setContentAreaFilled(false);
        stop.setPressedIcon (new ImageIcon ("stop_down.gif"));
        stop.setMargin (new Insets (0, 0, 0, 0));
        stop.addActionListener (this);
        
        m1 = new JButton (new ImageIcon ("checkbox.gif"));
        m1.setSelectedIcon( new ImageIcon ("checkbox_on.gif"));
        m1.setSelected(false);
        m1.setBorder(null);
        m1.setContentAreaFilled(false);
        m1.setPressedIcon (new ImageIcon ("checkbox_down.gif"));
        m1.setMargin (new Insets (0, 0, 0, 0));
        m1.setActionCommand("0");
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
        
        orderLabel = new JLabel ("Iteration: 1");
        orderLabel.setForeground (Color.white);

        modLabel = new JLabel ("Mods Enabled: none");
        modLabel.setForeground (Color.white);
        
        tools.add (titleLabel);
        tools.add (statusLabel);
        tools.add (modLabel);
        tools.add (orderLabel);
        
        tools2.add (Box.createHorizontalStrut (300));
        tools2.add(color);
        tools2.add(epilepsy);
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
        //tools2.add (Box.createHorizontalStrut (20));

        drawing = new TreePanel(1);

        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.add (tools);
        panel.add (tools2);
        panel.add (drawing);
        panel.addMouseListener(this); //since our class is already a MouseListener, we can add it here
        panel.addMouseMotionListener(this);

        frame = new JFrame();
        frame.setTitle("Fractal Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(panel);
        frame.setVisible(true);
        
        while(true)
        {
            if(mode.equals("Animation"))
            {
                int order = drawing.getOrder();
                mod++;
                drawing.setStuff (order,mod,updateModStatus(),epilepsyMode);
                Thread.sleep(animSpeed);
                frame.repaint();
            }
        }
    }
    
    private boolean[] updateModStatus()
    {
        return modStatus;
    }
    
    
    private String getMods()
    {
        String modList = "";
        for (int i = 0; i < modStatus.length; i++)
        {
            if (modStatus[i] == true)
            {
                if (i != 0)
                {
                    modList += " "+(i+1);
                }
                else
                {
                    modList += i+1;
                }
            }
        }
        
        return modList;
    }

    //-----------------------------------------------------------------
    //  Determines which button was pushed, and sets the new order
    //  if it is in range.
    //-----------------------------------------------------------------
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == animate)
        {
            stop.setSelected(false);
            animate.setSelected(true);
            mode = "Animation";
        }
        else if (e.getSource() == stop)
        {
            stop.setSelected(true);
            animate.setSelected(false);
            mode = "Still";
        }
        else if (e.getSource() == color)
        {
            drawing.makeNewColor();
            frame.repaint();
        }
        else if (e.getSource() == epilepsy)
        {
            if (epilepsyMode == false)
            {
                epilepsyMode = true;
                epilepsy.setSelected(true);
            }
            else
            {
                epilepsyMode = false;
                epilepsy.setSelected(false);
            }
        }
        else if (e.getSource() == m1 || e.getSource() == m2 || e.getSource() == m3 || e.getSource() == m4
        || e.getSource() == m5 || e.getSource() == m6 || e.getSource() == m7 || e.getSource() == m8
        || e.getSource() == m9 || e.getSource() == m10)
        {
            JButton btn = ((JButton)e.getSource());
            if (!btn.isSelected())
            {
                btn.setSelected(true);
                String btnName = btn.getActionCommand();
                int modNumber = Integer.parseInt(btnName);
                modStatus[modNumber] = true;
            }
            else
            {
                String btnName = btn.getActionCommand();
                int modNumber = Integer.parseInt(btnName);
                modStatus[modNumber] = false;
                btn.setSelected(false);
            }
        }
        statusLabel.setText ("Current Mode: " + mode);
        modLabel.setText ("Mods Enabled: " + getMods());
    }
    
    public void mouseMoved(MouseEvent e)
    {
        
    }

    public void mouseDragged(MouseEvent e)
    {
        int order = drawing.getOrder();
        if (buttonPressed == e.BUTTON1)
            {
            int y = (int)e.getPoint().getY();
            if (updates == 1)
            {
                previousY = y;
                order++;
            }
            
            if (y < previousY)
            {
                if (updates % 2 == 0)
                {
                    previousY = y;
                }
                order++;
            }
            else
            {
                if (updates % 2 == 0)
                {
                    previousY = y;
                }
                order--;
            }
    
            if (order >= MIN && order <= MAX)
            {
                orderLabel.setText ("Iteration: " + order);
                drawing.setStuff (order,mod,modStatus,epilepsyMode);
                frame.repaint();
            }
        }
        else
        {
            int x = (int)e.getPoint().getX();
            
            if (updates == 1)
            {
                previousX = x;
            }
            
            if (x < previousX)
            {
                if (updates % 2 == 0)
                {
                    previousX = x;
                }
                mod++;
            }
            else
            {
                if (updates % 2 == 0)
                {
                    previousX = x;
                }
                mod--;
            }
    
            orderLabel.setText ("Iteration: " + order);
            drawing.setStuff (order,mod,modStatus,epilepsyMode);
            frame.repaint();
        }
        
        updates++;
    }

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
        buttonPressed = e.getButton();
    }

    public void mouseReleased(MouseEvent e)
    {}

    public void mouseEntered(MouseEvent e)
    {}

    public void mouseExited(MouseEvent e)
    {}
}
