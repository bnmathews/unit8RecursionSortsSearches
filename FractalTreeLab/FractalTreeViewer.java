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

    private JButton increase, decrease, animate, draw;
    private JLabel titleLabel, orderLabel;
    private TreePanel drawing;
    private JPanel panel, tools;
    private JFrame frame;

    private int mod;
    
    private int updates = 0;
    
    private int previousY;
    
    private int previousX;
    
    private int buttonPressed;
    
    private String mode;
    
    private boolean[] modStatus = new boolean[8];
    
    
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
        mode = "Drawing";
        
        boolean[] modStatus = {false,false,false,false,false,false,false,false};
        System.out.println(modStatus[1]);
        
        tools = new JPanel ();
        tools.setLayout (new BoxLayout(tools, BoxLayout.X_AXIS));
        tools.setBackground (Color.yellow);
        tools.setOpaque (true);

        titleLabel = new JLabel ("Fractal Tree");
        titleLabel.setForeground (Color.black);
        
        animate = new JButton (new ImageIcon ("increase.gif"));
        animate.setPressedIcon (new ImageIcon ("increasePressed.gif"));
        animate.setMargin (new Insets (0, 0, 0, 0));
        animate.addActionListener (this);
        draw = new JButton (new ImageIcon ("draw.gif"));
        draw.setSelectedIcon( new ImageIcon ("draw_on.gif"));
        draw.setSelected(true);
        draw.setPressedIcon (new ImageIcon ("draw_down.gif"));
        draw.setMargin (new Insets (0, 0, 0, 0));
        draw.addActionListener (this);

        orderLabel = new JLabel ("Order: 1");
        orderLabel.setForeground (Color.black);

        tools.add (titleLabel);
        tools.add (Box.createHorizontalStrut (20));
        tools.add (animate);
        tools.add (draw);
        tools.add (Box.createHorizontalStrut (20));
        tools.add (orderLabel);

        drawing = new TreePanel(1);

        panel = new JPanel();
        panel.add (tools);
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
                drawing.setStuff (order,mod,modStatus);
                mod++;
                frame.repaint();
                Thread.sleep(5);
            }
        }
    }

    //-----------------------------------------------------------------
    //  Determines which button was pushed, and sets the new order
    //  if it is in range.
    //-----------------------------------------------------------------
    public void actionPerformed (ActionEvent e)
    {
        
        if (e.getSource() == animate)
        {
            draw.setSelected(false);
            mode = "Animation";
        }
        else
        {
            draw.setSelected(true);
            mode = "Drawing";
        }
    }
    
    public void mouseMoved(MouseEvent e)
    {
        
    }

    public void mouseDragged(MouseEvent e)
    {
        if(mode.equals("Drawing"))
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
                    orderLabel.setText ("Order: " + order);
                    drawing.setStuff (order,mod,modStatus);
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
        
                orderLabel.setText ("Order: " + order);
                drawing.setStuff (order,mod,modStatus);
                frame.repaint();
            }
            
            updates++;
        }
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
