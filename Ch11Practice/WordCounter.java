import java.util.Scanner;
import java.io.File;
public class WordCounter
{
    public WordCounter()
    {
    }

    public static void main(String[] args)
        throws java.io.FileNotFoundException
    {
        String filename = "";
        int charcount = 0;
        int wordcount = 0;
        int linecount = 0;
        
        if (args.length != 0)
        {
            filename = args[0];
        }
        else
        {
            Scanner s = new Scanner(System.in);
            System.out.print("Input a file name, yo\n>> ");
            filename = s.next();
        }
        
        File file = new File(filename);
        Scanner in = new Scanner(file);
        
        in.useDelimiter("");
        
        while ( in.hasNext() )
        {
            char ch = in.next().charAt(0);
            //System.out.println(ch);
            charcount++;
        }
        
        in.close();
        
        in = new Scanner(file);
        
        while ( in.hasNext() )
        {
            in.next();
            wordcount++;
        }
        
        in.close();
        
        in = new Scanner(file);
        
        in.useDelimiter("\n");
        
        while ( in.hasNext() )
        {
            in.next();
            linecount++;
        }
        
        in.close();
        
        System.out.println("# of Characters: " + charcount);
        System.out.println("# of Words: " + wordcount);
        System.out.println("# of Lines: " + linecount);
    }
}
