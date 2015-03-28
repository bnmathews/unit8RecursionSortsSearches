//********************************************************************
//  KochPanel.java       Author: Lewis/Loftus/Cocking
//
//  Represents a drawing surface on which to paint a Koch Snowflake.
//********************************************************************

import java.awt.*;
import javax.swing.JPanel;

public class TreePanel extends JPanel
{
   private final int PANEL_WIDTH = 1000;
   private final int PANEL_HEIGHT = 1000;

   private final double SQ = Math.sqrt(3.0) / 6;

   private final int TOPX = 500, TOPY = 650;
   private final int BOTTOMX = 500, BOTTOMY = 800;

   private int current; //current order
   
   private int curMod = 0;
   
   private modTracker m1, m2, m3, m4, m5, m6, m7, m8;

   public TreePanel (int currentOrder)
   {
      current = currentOrder;
      setBackground (Color.black);
      setPreferredSize (new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
      
      m1 = new modTracker();
      m2 = new modTracker();
      m3 = new modTracker();
      m4 = new modTracker();
      m5 = new modTracker();
      m6 = new modTracker();
      m7 = new modTracker();
      m8 = new modTracker();
   }
   
   public class modTracker
   {
       private boolean enabled;
       public modTracker()
       {
           enabled = false;
       }
       public int fillValue(int mod)
       {
           if (enabled == true)
           {
               return mod;
           }
           else
           {
               return 0;
           }
       }
       public void setStatus(boolean s)
       {
           enabled = s;
       }
   }
   
   public void drawFractal (int order, int x, int y, int x2, int y2, int lastAng,
   
                            Graphics page, int mod)
   {
      double len = Math.sqrt(Math.pow((y2-y),2)+Math.pow((x2-x),2)); 
      
      int lX = x + m1.fillValue(mod) + (int)(len*.8*Math.sin(Math.toRadians(lastAng-30 + m2.fillValue(mod) )));
      int lY = y + m3.fillValue(mod) - (int)(len*.8*Math.cos(Math.toRadians(lastAng-30 + m4.fillValue(mod) )));
      
      int rX = x + m5.fillValue(mod) + (int)(len*.8*Math.sin(Math.toRadians(lastAng+30 + m6.fillValue(mod))));
      int rY = y + m7.fillValue(mod) - (int)(len*.8*Math.cos(Math.toRadians(lastAng+30 + m8.fillValue(mod))));
      
      page.drawLine (x, y, x2, y2);
      if (order == 1)
      {
         page.drawLine (x, y, x2, y2); //draw the first line
      }                         
      else
      {
         page.drawLine (lX, lY, x, y);
         page.drawLine (rX, rY, x, y);
         drawFractal (order-1, lX, lY, x, y, lastAng-20, page, mod);
         drawFractal (order-1, rX, rY, x , y, lastAng+20, page, mod);
      }
   }

   //-----------------------------------------------------------------
   //  Performs the initial calls to the drawFractal method.
   //-----------------------------------------------------------------
   public void paintComponent (Graphics page)
   {
      super.paintComponent (page);

      page.setColor (Color.green);

      drawFractal (current, TOPX, TOPY, BOTTOMX, BOTTOMY, 0, page, curMod);
   }

   //-----------------------------------------------------------------
   //  Sets the fractal order to the value specified.
   //-----------------------------------------------------------------
   public void setStuff (int order, int mod, boolean[] modStatus)
   {
      current = order;
      curMod = mod;
      
      m1.setStatus(modStatus[0]);
      m2.setStatus(modStatus[1]);
      m3.setStatus(modStatus[2]);
      m4.setStatus(modStatus[3]);
      m5.setStatus(modStatus[4]);
      m6.setStatus(modStatus[5]);
      m7.setStatus(modStatus[6]);
      m8.setStatus(modStatus[7]);
   }

   //-----------------------------------------------------------------
   //  Returns the current order.
   //-----------------------------------------------------------------
   public int getOrder ()
   {
      return current;
   }
}