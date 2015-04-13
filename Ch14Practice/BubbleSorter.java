import java.util.Arrays;
public class BubbleSorter
{
    public static void sort(String[] s)
    {
        boolean sorted = false;
        while (!sorted)
        {
            sorted = true;
            for (int i = 0; i < s.length-1; i++)
            {
                String temp = s[i];
                if (temp.compareTo(s[i+1]) > 0)
                {
                    s[i] = s[i+1];
                    s[i+1] = temp;
                    sorted = false;
                }
            }
        }
    }
    
    public static void main(String[] args)
    {
        String[] str = {"ffdsf","afasfaew","feafaew","fzvze","ghtbf","fthf","ngyjcs"};
        System.out.println(Arrays.toString(str));
        sort(str);
        System.out.println(Arrays.toString(str));
    }

}
