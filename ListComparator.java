package capstone;
import java.util.*;


	public class ListComparator implements Comparator<Map.Entry<Integer,ArrayList<ArrayList<Integer>>>> {
	//	Map map;
		
//		public ListComparator(Map map) {
//			this.map = map;
		//}
		
		
		@Override
	      public int compare(Map.Entry<Integer,ArrayList<ArrayList<Integer>>> entry1, Map.Entry<Integer,ArrayList<ArrayList<Integer>>> entry2) {
	         int x1,y1,x2,y2;
	         ArrayList<ArrayList<Integer>> point1=new ArrayList<ArrayList<Integer>>();
	         ArrayList<ArrayList<Integer>> point2=new ArrayList<ArrayList<Integer>>();
	         point1=entry1.getValue();
	         point2=entry2.getValue();
	    	  if (point1==null)
	    	  {x1=0;y1=0;}
	    	  else if (point1.isEmpty())
	    	  {x1=0;y1=0;}
	    	  else if (point1.get(0)==null)
	    	  {x1=0;y1=0;}
	    	  else 
	    	  {
	    		  x1=ListToInt(point1.get(0));
	    		  y1=ListToInt(point1.get(1));
	    	  }
	    	  
	    	  if (point2==null)
	    	  {x2=0;y2=0;}
	    	  else if (point2.isEmpty())
	    	  {x2=0;y2=0;}
	    	  else if (point2.get(0)==null)
	    	  {x2=0;y2=0;}
	    		  else 
		    	  {
		    		  x2=ListToInt(point2.get(0));
		    		  y2=ListToInt(point2.get(1));
		    	  }
	    	  int val1=x1*x1+y1*y1;
	    	  int val2=x2*x2+y2*y2;
	    	  
	    	  return Integer.compare(val1, val2);
	      }

		public int ListToInt(ArrayList<Integer> list) {
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

