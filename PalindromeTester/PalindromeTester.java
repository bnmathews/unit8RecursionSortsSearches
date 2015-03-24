import java.util.Scanner;

/**
 * Demonstrates the use of nested while loops.
 * 
 * @author Lewis/Loftus/Cocking
 */
public class PalindromeTester
{
    /**
     * Tests strings to see if they are palindromes.
     *
     */
    public static void main (String[] args)
    {
        String str, another = "y";
        int left, right;
        Scanner s = new Scanner(System.in);

        while (another.equalsIgnoreCase("y")) // allows y or Y
        {
            System.out.println ("Enter a potential palindrome:");
            str = s.nextLine();

            System.out.println();

            if (isPalindrome(str))
                System.out.println ("That string IS a palindrome.");
            else
                System.out.println ("That string is NOT a palindrome.");
                
            System.out.println();
            System.out.print ("Test another palindrome (y/n)? ");
            another = s.nextLine();
        }
    }

    private static boolean isPalindrome( String str )
    {
        if (str.length() <= 1)
        {
            return true;
        }
        
        int first = 0;
        int last = str.length()-1;
        String rest = str.substring( 1, last );
        
        if (str.charAt(first) == str.charAt(last) && isPalindrome(rest))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
