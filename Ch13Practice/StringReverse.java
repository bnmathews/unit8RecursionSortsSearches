public class StringReverse
{
    public static String reverse( String text )
    {
        if( text.length() <= 1)
        {
            return text;
        }
        
        char c = text.charAt(0);
        String rest = text.substring(1);
        String reverseText = reverse(rest) + c;
        
        return reverseText;
    }
    
    public static void main( String[] args)
    {
        System.out.println(reverse("Apples"));
    }
}
