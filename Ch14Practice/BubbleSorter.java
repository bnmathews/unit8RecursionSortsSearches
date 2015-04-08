import java.util.Arrays;
public class BubbleSorter
{
    public static void sort(String[] s)
    {
        boolean sorted = false;
        while (!sorted)
        {
            for (int i = 0; i < s.length-1; i++)
            {
                String temp = s[i];
                sorted = true;
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
        String[] str = {"z","y","x","w","v","u","t"};
        System.out.println(Arrays.toString(str));
        sort(str);
        System.out.println(Arrays.toString(str));
    }

}
