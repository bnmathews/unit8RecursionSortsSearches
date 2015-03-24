import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BabyNames
{
   public static final double LIMIT = 100;

   public static void main(String[] args) 
      throws FileNotFoundException
   {  
      try 
      {
          Scanner s = new Scanner(System.in);
          System.out.print("Enter a filename, man.\n>> ");
          String filename = s.next();
          Scanner in = new Scanner(new File(filename));
      }
      catch(FileNotFoundException fnf)
      {
          System.out.println(fnf.getMessage());
      }
         
      RecordReader boys = new RecordReader(LIMIT);
      RecordReader girls = new RecordReader(LIMIT);
      
      while (boys.hasMore() || girls.hasMore())
      {
         int rank = in.nextInt();
         System.out.print(rank + " ");
         boys.process(in);
         girls.process(in);
         System.out.println();
      }

      in.close();
   }
}   
