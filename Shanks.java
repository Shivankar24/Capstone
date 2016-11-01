package capstone;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.lang.*;
public class Shanks {

	public static ArrayList<ArrayList<Integer>> point_multiple(ArrayList<ArrayList<Integer>> alpha,int x,Elliptic_Curve ec, Fields gf)
	{
		if (x==0)
			return null;
		if (x==1)
			return alpha;
		ArrayList<ArrayList<Integer>> new_point=new ArrayList<ArrayList<Integer>>();
		new_point.addAll(alpha);
		for (int i=2;i<=x;i++)
		{
			new_point=ec.point_addition(alpha, new_point, gf);
			//System.out.println("newpoint: "+new_point);
		}
		return new_point;
	}
	public static ArrayList<ArrayList<Integer>> double_add(ArrayList<ArrayList<Integer>> alpha,int x,Elliptic_Curve ec, Fields gf)
	
	{
		ArrayList<ArrayList<Integer>> Q=new ArrayList<ArrayList<Integer>>();
		Q=null;
		ArrayList<ArrayList<Integer>> N=new ArrayList<ArrayList<Integer>>();
		N.addAll(alpha);
		//System.out.println("N: "+N);
		String s=Integer.toBinaryString(x);
		ArrayList<Integer> m=new ArrayList<Integer>();
		//System.out.println(s);
		
		m=gf.StringToList(s);
		for (int i=0;i<m.size();i++)
		{
			if (m.get(i)==1)
				Q=ec.point_addition(Q, N, gf);
			N=ec.point_addition(N, N, gf);
		}
		return Q;
	}
	
	public static int shanks(ArrayList<ArrayList<Integer>> alpha,ArrayList<ArrayList<Integer>> beta, long n,Elliptic_Curve ec, Fields gf)
	{
		int m=(int) Math.ceil(Math.sqrt(n));
		System.out.println("m: "+m);
		ArrayList<ArrayList<Integer>> temp_point=new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> temp_point1=new ArrayList<ArrayList<Integer>>();
		Map<Integer,ArrayList<ArrayList<Integer>>> j_vals=new HashMap<Integer,ArrayList<ArrayList<Integer>>>();
		Map<Integer,ArrayList<ArrayList<Integer>>> i_vals=new HashMap<Integer,ArrayList<ArrayList<Integer>>>();
		
		for (int j=0;j<m;j++)
		{
			temp_point=double_add(alpha,(m*j),ec,gf);
			j_vals.put(j, temp_point);
			//System.out.println("in jvals " +j);
		}
		
		for (int i=0;i<m;i++)
		{
		
			temp_point1=double_add(alpha,i,ec,gf);
			temp_point1=ec.point_inverse(temp_point1,gf);
			temp_point=ec.point_addition(beta,temp_point1, gf);
			i_vals.put(i, temp_point);
			//System.out.println("in ivals " +i);
		}
		
		 List<Map.Entry<Integer, ArrayList<ArrayList<Integer>>>> list = 
			        new ArrayList<>(i_vals.entrySet());
			    Collections.sort(list, new ListComparator());

		//Map new_i_vals = sort_map_values(i_vals);
//		System.out.println(i_vals);
//		System.out.println(j_vals);
		int final_j=0,final_i=0,flag=0;
		for (Entry<Integer, ArrayList<ArrayList<Integer>>> entry1 : j_vals.entrySet()) 
		{
		    int key1 = entry1.getKey();
		    ArrayList<ArrayList<Integer>> value1 = new ArrayList<ArrayList<Integer>>();
		    	value1=entry1.getValue();
		    	if (i_vals.containsValue(value1))
		    	{
		    		for (Entry<Integer, ArrayList<ArrayList<Integer>>> entry2 : i_vals.entrySet()) 
		    		{
		    		    int key2 = entry2.getKey();
		    		    ArrayList<ArrayList<Integer>> value2 = new ArrayList<ArrayList<Integer>>();
		    		    	value2=entry2.getValue();
		    		    	if (ec.point_equals(value1, value2))
		    		    	{
		    		    		flag=1;
		    		    		final_j=key1;
		    		    		final_i=key2;
		    		    		System.out.println("i and j: "+final_i+" "+final_j+" "+value1+" "+value2);
		    		    		break;
		    		   
		    		    	}
		    		}
		    	}
		    	else
			    	continue;
		    	if (flag==1)
		    	return (m*final_j+final_i);
		}    	
		   
		return 0;
	}
	public static LinkedHashMap sort_map_values(HashMap<Integer, ArrayList<ArrayList<Integer>>> i_vals)
	{
		LinkedHashMap sorted_map = new LinkedHashMap();
		int x1=0,y1=0,x2=0,y2=0;
		for (int i : i_vals.keySet()) 
		{
		    int key = 0;
			
		    ArrayList<ArrayList<Integer>> value1 = new ArrayList<ArrayList<Integer>>();
		    	value1=i_vals.get(i);
		    	if (value1==null)
	  	    	  {x1=0;y1=0;}
	  	    	  else if (value1.isEmpty())
	  	    	  {x1=0;y1=0;}
	  	    	  else if (value1.get(0)==null)
	  	    	  {x1=0;y1=0;}
	  	    	  else 
	  	    	  {
	  	    		  x1=ListToInt(value1.get(0));
	  	    		  y1=ListToInt(value1.get(1));
	  	    	  }
		    		for (int j: i_vals.keySet()) 
		    		{
		    		 //   int key2 = j;
		    		    ArrayList<ArrayList<Integer>> value2 = new ArrayList<ArrayList<Integer>>();
		    		    value2=i_vals.get(j);
		    		    
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
		  	    	  int val1=x1*x1+y1;
		  	    	  int val2=x2*x2+y2;
		  	    	  if (val2<val1)
		  	    	  {
		  	    		ArrayList<ArrayList<Integer>> value = new ArrayList<ArrayList<Integer>>();
		  	    		value.addAll(value2);
		  	    		key=j;							  
		  	    	  }
//		  	    	ArrayList<ArrayList<Integer>> value = new ArrayList<ArrayList<Integer>>();
//					int key=0;
				    

		    		    	
		    		}
		    	}
		    	
		return sorted_map;
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
	public static void main(String args[])throws IOException
	{
		
		BufferedReader inp=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the value of k for GF(2^k):" );
		int k=Integer.parseInt(inp.readLine());
//		ArrayList<Integer> prime_factors=new ArrayList<Integer>();
//		prime_factors.addAll(factors(n));
		//System.out.println("Prime factors of (2^k)-1: "+prime_factors);
		Fields gf=new Fields(k);
		System.out.println("irreducible: "+gf.irreducible);
		System.out.println("Elliptic curve type: y^2 + x*y = x^3 + a*x^2 + b,");
//		System.out.println("Enter the value of A for elliptic curve from GF(2^k): ");
//		String a=(inp.readLine());
//		System.out.println("Enter the value of B for elliptic curve from GF(2^k): ");
//		String b=(inp.readLine());	
//		System.out.println("Enter the value of n: ");
//		int n=Integer.parseInt(inp.readLine());
		String a="100000";String b="100001";int n=56;
		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
		ArrayList<ArrayList<Integer>> alpha=new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> beta=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> x_point=new ArrayList<Integer>();
		ArrayList<Integer> y_point=new ArrayList<Integer>();
		String x,y;
		
//		System.out.println("Enter the value of alpha x coordinate: ");
//		x=inp.readLine();
//		System.out.println("Enter the value of alpha y coordinate: ");
//		y=inp.readLine();
//		x_point.addAll(gf.StringToList(x));
//		y_point.addAll(gf.StringToList(y));
//		alpha.add(x_point);alpha.add(y_point);		
//		System.out.println("Enter the value of beta x coordinate: ");
//		x=inp.readLine();
//		System.out.println("Enter the value of beta y coordinate: ");
//		y=inp.readLine();
//		x_point=new ArrayList<Integer>();
//		y_point=new ArrayList<Integer>();
//		x_point.addAll(gf.StringToList(x));
//		y_point.addAll(gf.StringToList(y));
//		beta.add(x_point);beta.add(y_point);
		

		x="001011";
		y="011100";
		x_point.addAll(gf.StringToList(x));
		y_point.addAll(gf.StringToList(y));
		alpha.add(x_point);alpha.add(y_point);
		
		x_point=new ArrayList<Integer>();
		y_point=new ArrayList<Integer>();
		
		
		beta.addAll(Shanks.double_add(alpha, 54, ec, gf));
		System.out.println(alpha+" "+beta );
		System.out.println(shanks(alpha,beta,n, ec, gf));
		System.out.println(double_add(alpha, 8, ec, gf));

		

		
		
	}
	
}
