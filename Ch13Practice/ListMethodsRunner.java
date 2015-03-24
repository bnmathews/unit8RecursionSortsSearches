import java.util.ArrayList;

public class ListMethodsRunner
{
    public static void main(String[] args)
    {
        ArrayList<Integer> tempList = ListMethods.makeList(10);
        if (tempList.size() == 0)
        {
            System.out.println("The list is empty");
        }
        else
        {
            for (Integer i : tempList)
            {
                //System.out.println(i);
            }

            //ListMethods.reverseList(tempList);
            for (Integer i : tempList)
            {
                //System.out.println(i);
            }
            
            ListMethods.odd(tempList);
            for (Integer i : tempList)
            {
                System.out.println(i);
            }

            ListMethods.even(tempList);
            for (Integer i : tempList)
            {
                //System.out.println(i);
            }
        }
    }
}