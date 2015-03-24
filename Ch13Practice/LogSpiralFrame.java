import javax.swing.JFrame;

public class LogSpiralFrame
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        
        frame.setSize(500,500);
        frame.setTitle("LogSpiral");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        LogSpiralPanel component = new LogSpiralPanel();
        
        frame.add(component);
        
        frame.setVisible(true);
        
        /*
        while (true)
        {
            component.repaint();
        }
        */
    }
}
