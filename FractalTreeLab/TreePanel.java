//********************************************************************
//  KochPanel.java       Author: Lewis/Loftus/Cocking
//
//  Represents a drawing surface on which to paint a Koch Snowflake.
//********************************************************************

import java.awt.*;
import javax.swing.JPanel;

public class TreePanel extends JPanel
{
   private final int PANEL_WIDTH = 400;
   private final int PANEL_HEIGHT = 400;

   private final double SQ = Math.sqrt(3.0) / 6;

   private final int TOPX = 200, TOPY = 300;
   private final int BOTTOMX = 200, BOTTOMY = 350;

   private int current; //current order

   //-----------------------------------------------------------------
   //  Sets the initial fractal order to the value specified.
   //-----------------------------------------------------------------
   public TreePanel (int currentOrder)
   {
      current = currentOrder;
      setBackground (Color.black);
      setPreferredSize (new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
   }

   //-----------------------------------------------------------------
   //  Draws the fractal recursively. Base case is an order of 1 for
   //  which a simple straight line is drawn. Otherwise three
   //  intermediate points are computed, and each line segment is
   //  drawn as a fractal.
   //-----------------------------------------------------------------
   public void drawFractal (int order, int x, int y, int x2, int y2, int lastAng,
   
                            Graphics page)
   {
      double len = Math.sqrt(Math.pow((y2-y),2)+Math.pow((x2-x),2)); 
      System.out.println(len);
      
      int lX = x + (int)(len*.8*Math.sin(Math.toRadians(lastAng-50)));
      int lY = y - (int)(len*.8*Math.cos(Math.toRadians(lastAng-50)));
      
      int rX = x + (int)(len*.8*Math.sin(Math.toRadians(lastAng+50)));
      int rY = y - (int)(len*.8*Math.sin(Math.toRadians(lastAng+50)));
      
      page.drawLine (x, y, x2, y2);
      if (order == 1)
      {
         page.drawLine (x, y, x2, y2); //draw the first line
      }                         
      else
      {
         page.drawLine (lX, lY, x, y);
         page.drawLine (rX, rY, x, y);
         drawFractal (order-1, lX, lY, x, y, lastAng-50, page);
         drawFractal (order-1, rX, rY, x, y, lastAng+50, page);
      }
   }

   //-----------------------------------------------------------------
   //  Performs the initial calls to the drawFractal method.
   //-----------------------------------------------------------------
   public void paintComponent (Graphics page)
   {
      super.paintComponent (page);

      page.setColor (Color.green);

      drawFractal (current, TOPX, TOPY, BOTTOMX, BOTTOMY, 0, page);
   }

   //-----------------------------------------------------------------
   //  Sets the fractal order to the value specified.
   //-----------------------------------------------------------------
   public void setOrder (int order)
   {
      current = order;
   }

   //-----------------------------------------------------------------
   //  Returns the current order.
   //-----------------------------------------------------------------
   public int getOrder ()
   {
      return current;
   }
}
