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
   public void drawFractal (int order, int x1, int y1, int x2, int y2,
                            Graphics page)
   {
      int deltaX;
      int deltaY = 0;
      page.drawLine (x1, y1, x2, y2);
      page.drawLine (x1, y1, x2, y2);

      if (order == 1)
        {
            page.drawLine (x1, y1, x2, y2);
            page.drawLine (x1-(2*x1), y1, x2, y2);
        }                         
      else
      {
         deltaX = x2 - x1;  // distance between end points
         deltaY = y2 - y1;

         //x2 = x1 + deltaX / 2;
         //y2 = y1 + deltaY / 2;

         drawFractal (order-1, x1 + 5, deltaY / 2, x1, y1, page);
      }
   }

   //-----------------------------------------------------------------
   //  Performs the initial calls to the drawFractal method.
   //-----------------------------------------------------------------
   public void paintComponent (Graphics page)
   {
      super.paintComponent (page);

      page.setColor (Color.green);

      drawFractal (current, TOPX, TOPY, BOTTOMX, BOTTOMY, page);
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
