import java.awt.*;
import javax.swing.JPanel;
import java.util.Random; 

/**
 * Fractal Tree Panel
 * 
 * @author @bnmathews
 * @version 30 March 2015
 */
public class TreePanel extends JPanel
{
    // the dimensions of the Tree Panel
    private final int PANEL_WIDTH = 1000;
    private final int PANEL_HEIGHT = 1000;
    
    // the coordinates of the first line drawn, or the 'trunk'
    private final int TOPX = 500, TOPY = 650;
    private final int BOTTOMX = 500, BOTTOMY = 800;

    // keeps track of the curren iteration of the tree
    private int current;

    // holds the current mod value set by the FractalTreeViewer object
    private int curMod = 0;
    
    // instance variables of the modTracker subclass
    private modTracker m1, m2, m3, m4, m5, m6, m7, m8, m9, m10;

    // the color that the tree will be drawn in
    private Color drawColor = Color.green;

    // keeps track of if epilepsyMode is enabled or not
    private boolean epilepsyMode;

    /**
     * Sets up the tree panel
     * 
     * @param   int the current iteration of the tree
     * @post    the tree panel will be created
     *
     */
    public TreePanel (int currentOrder)
    {
        // set the current iteration to the one passed in
        current = currentOrder;
        
        // set the background color to flat black
        setBackground (Color.black);
        
        // set the dimensions
        setPreferredSize (new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        // start with random colors disabled
        epilepsyMode = false;

        // create the modTrackers
        m1 = new modTracker();
        m2 = new modTracker();
        m3 = new modTracker();
        m4 = new modTracker();
        m5 = new modTracker();
        m6 = new modTracker();
        m7 = new modTracker();
        m8 = new modTracker();
        m9 = new modTracker();
        m10 = new modTracker();
    }

    public class modTracker // this class is used to pass in one of two values to places where the user wants
                            // to modify the tree's generation, those being the current modification value itself,
                            // of 0 if the modifier is disabled, meaning it will have no effect
    {
        // the status of the modifier
        private boolean enabled;
        /**
        * Sets up the modTracker
        * 
        * @pre  the modTracker has been defined elsewhere
        * 
        */
        public modTracker()
        {
            enabled = false;
        }

        /**
         * Returns either the current mod value, meaning the modifier is active, or 0, meaning it is inactive
         * 
         * @param   mod the current mod value, set by the FractalTreeViewer object
         * @post    the tree's generation will be either affected or unaffected based on what value is returned
         * @return  either the current mod value or 0 if the modifier is disabled
         * 
         */
        public int fillValue(int mod)
        {
            if (enabled == true) // if the modifier is enabled, use the current mod value
            {
                return mod;
            }
            else // if not, returning 0 will be like if the modifier didn't exist at all
            {
                return 0;
            }
        }

        /**
         * Enables or disables the modifier
         * 
         * @param   s whether or not the modifier should be enabled
         * @post    the modifier's status is changed - if enabled it will insert the current mod value into the tree's
         *          generation
         * 
         */
        public void setStatus(boolean s) 
        {
            enabled = s;
        }
    }

    /**
     * Draws the lines that make up the tree
     * 
     * @param   x the top x coordinate of the line
     * @param   y the top y coordinate of the line
     * @param   x2 the bottom x coordinate of the line
     * @param   y2 the bottom y coordinate of the line
     * @param   lastAng the previous angle
     * @param   page the Graphics object to draw the lines on
     * @param   mod the current modification value used to change various positionings
     * @pre     the Graphics object must be created
     * @post    the tree will be drawn
     * 
     */
    public void drawFractal (int order, int x, int y, int x2, int y2, int lastAng, Graphics page, int mod)
    {
        // the length between the top and bottom of each line
        double len = Math.sqrt(Math.pow((y2-y),2)+Math.pow((x2-x),2)); 

        // the top x and y values of the left line
        int lX = x + m1.fillValue(mod) + (int)(len*.8*Math.sin(Math.toRadians(lastAng-30 + m2.fillValue(mod) )));
        int lY = y + - (int)(len*.8*Math.cos(Math.toRadians(lastAng-30 + m3.fillValue(mod) )));

        // the top x and y values of the right line
        int rX = x + m4.fillValue(mod) + (int)(len*.8*Math.sin(Math.toRadians(lastAng+30 + m5.fillValue(mod))));
        int rY = y + - (int)(len*.8*Math.cos(Math.toRadians(lastAng+30 + m7.fillValue(mod))));

        // draws the line on the page
        page.drawLine (x, y, x2, y2);
        
        if (order == 1) // if the number of iterations is at one, the first line will be drawn
        {
            // draw the initial line on the page
            page.drawLine (x, y, x2, y2);
        }                         
        else // if there is more of the tree to draw, keep drawing until the number of iterations (order) reaches 1 again
        {
            // draw the left line
            page.drawLine (lX, lY, x, y);
            
            // draw the right line
            page.drawLine (rX, rY, x, y);
            
            if (m10.fillValue(mod) != 0) // call the drawFractal method again with a differnt calculation method if
                                         // modifier 10 is turned on
            {
                // call the drawFractal method twice, this time using the top of the last line as the base of the new one
                // and decrese the iteration so eventually the recursion stops
                drawFractal (order-1, lX, lY, x, y, lastAng-m8.fillValue(mod), page, mod);
                drawFractal (order-1, rX, rY, x , y, lastAng+m9.fillValue(mod), page, mod);
            } 
            else // if modifier 10 is off, do it the normal way
            {
                drawFractal (order-1, lX, lY, x, y, lastAng-20 + m8.fillValue(mod), page, mod);
                drawFractal (order-1, rX, rY, x , y, lastAng+20 + m9.fillValue(mod), page, mod);
            }
        }
    }
    
    /**
     * Randomly generates a new color to use for drawing the tree
     * 
     * @post    a random color will be generated
     * 
     */
    public void makeNewColor()
    {
        // create a new Random object
        Random r = new Random();
        
        // make a new Color based on a full range of random integers created by object r
        Color newColor = new Color(r.nextInt(255-1),r.nextInt(255-1),r.nextInt(255-1));
        
        // set the drawColor to the color just created
        drawColor = newColor;
    }

    /**
     * Creates the actual graphics each time the frame is repainted
     * 
     * @param   page the Graphics object to draw on
     * @post    the image will be displayed
     * 
     */
    public void paintComponent (Graphics page)
    {
        // call the superclasse's method
        super.paintComponent (page);

        if (epilepsyMode == true) // if the user wants to generate a new color each time the graphics are updated, this will
                                  // create a new one each time and assign the current Color to it
        {
            makeNewColor();
        }

        // set the color to drawColor, which could be a random value at this point
        page.setColor (drawColor);
        
        // draw the tree object
        drawFractal (current, TOPX, TOPY, BOTTOMX, BOTTOMY, 0, page, curMod);
    }

    /**
     * Takes in various info specified by the FractalTreeViewer object
     * 
     * @param    order the amount of times to recursively build up the tree
     * @param    mod the number for altering aspects of the tree's creation
     * @param    modStatus an array of the state each modifier is currently in
     * @param    epilepsy whether or not epilepsy mode is on
     * 
     * @pre      everything is already initialized
     * @post     new values are taken in and used in the TreePanel's methods
     * 
     */
    public void setStuff (int order, int mod, boolean[] modStatus, boolean epilepsy)
    {
        // the current number to generate the tree up until
        current = order;
        
        // the current modification value
        curMod = mod;

        // the state of epilepsyMode
        epilepsyMode = epilepsy;
        
        // sets the statuses of each of the modifiers, on or off
        m1.setStatus(modStatus[0]);
        m2.setStatus(modStatus[1]);
        m3.setStatus(modStatus[2]);
        m4.setStatus(modStatus[3]);
        m5.setStatus(modStatus[4]);
        m6.setStatus(modStatus[5]);
        m7.setStatus(modStatus[6]);
        m8.setStatus(modStatus[7]);
        m9.setStatus(modStatus[8]);
        m10.setStatus(modStatus[9]);
    }

    /**
     * Returns the current iteration of the tree
     * 
     * @return  the tree's current iteration
     * 
     */
    public int getOrder ()
    {
        return current;
    }
}
