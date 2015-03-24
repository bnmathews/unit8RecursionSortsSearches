import java.util.*;

public class ListMethods
{
    private static ArrayList<Integer> tempList = new ArrayList<Integer>();

    public static ArrayList makeList(int n)
    {
        if (n <= 0)  // The smallest list we can make
        {

        }
        else        // All other size lists are created here
        {
            tempList.add(0,n);
            makeList(n-1);
        }
        return tempList;
    }
    
    public static ArrayList<Integer> even(ArrayList<Integer> list)
    {
        ArrayList<Integer> newlist = deepClone(list);
        if (list.size() == 0) 
        {
            return list;
        }
        else
        {
            int val = newlist.get(0);
            
            list.remove(0);
            list.remove(0);
            
            newlist = even(list);
            newlist.add(0,val);
            return newlist;
        }
    }
    
    public static ArrayList<Integer> odd(ArrayList<Integer> list)
    {
        ArrayList<Integer> newlist = deepClone(list);
        if (list.size() == 0) 
        {
            return list;
        }
        else
        {
            int val = newlist.get(1);
            
            list.remove(1);
            list.remove(1);
            
            newlist = even(list);
            newlist.add(0,val);
            return newlist;
        }
    }
    
    public static ArrayList<Integer> deepClone(ArrayList<Integer> tList)
    {
        ArrayList<Integer> list = new ArrayList<Integer>(); 
        for (Integer i : tList)
        {
            list.add(new Integer(i));
        }
        return list;
    }

    public static ArrayList<Integer> reverseList(ArrayList<Integer> list)
    {
        ArrayList<Integer> newlist = deepClone(list); 
        int i = newlist.get(0);
        if(list.size() <= 1)
        {
            return list;
        }
        else
        {
            list.remove(0);
            newlist = reverseList(list);
            newlist.add(i);
            return newlist;
        }
    }
}