package capstone;
import java.util.*;

public class Shanks_tuple implements Comparable<Shanks_tuple> {

	int x;
	ArrayList<ArrayList<Integer>> point;
	
	public Shanks_tuple()
	{
		point=new ArrayList<ArrayList<Integer>> ();
	}
	@Override
	public int compareTo(Shanks_tuple point1) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Integer>> value1 = new ArrayList<ArrayList<Integer>>();
		int x1=0,x2=0,y1=0,y2=0;
    	value1=this.point;
    	if (value1==null)
	    	  {x1=0;y1=0;}
    		   else
	    	  if (value1.isEmpty())
	    	  {x1=0;y1=0;}
	    	  else if (value1.get(0)==null)
	    	  {x1=0;y1=0;}
	    	  else 
	    	  {
	    		  x1=ListToInt(value1.get(0));
	    		  y1=ListToInt(value1.get(1));
	    	  }
    	ArrayList<ArrayList<Integer>> value2 = new ArrayList<ArrayList<Integer>>();
	    value2=point1.point;
	    
  	  if (value2==null)
  	  {x2=0;y2=0;}
  	  else if (value2.isEmpty())
  	  {x2=0;y2=0;}
  	  else if (value2.get(0)==null)
  	  {x2=0;y2=0;}
  		  else 
	    	  {
	    		  x2=ListToInt(value2.get(0));
	    		  y2=ListToInt(value2.get(1));
	    	  }
  	  Integer val1=x1*x1+y1;
  	  Integer val2=x2*x2+y2;
    
		return val1.compareTo(val2);
	}
	public static int ListToInt(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		int ans=0;
		for (int i=0;i<list.size();i++)
		{
			if (list.get(i)==0)
				continue;
			ans= (ans+((int)Math.pow(2, i)*list.get(i)));
		}
		return ans;
	}

}
