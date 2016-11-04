package capstone;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.lang.*;
public class Shanks {

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
		//System.out.println("m: "+m);
		ArrayList<ArrayList<Integer>> temp_point=new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> temp_point1=new ArrayList<ArrayList<Integer>>();
		List<Shanks_tuple> j_vals=new ArrayList<Shanks_tuple>();
		List<Shanks_tuple> i_vals=new ArrayList<Shanks_tuple>();
		Shanks_tuple st=new Shanks_tuple();
		for (int j=0;j<m;j++)
		{
			temp_point=new ArrayList<ArrayList<Integer>>();
			temp_point=double_add(alpha,(m*j),ec,gf);
			st=new Shanks_tuple();
			st.point=temp_point; st.x=j;
			j_vals.add(st);
			
			temp_point=new ArrayList<ArrayList<Integer>>();
			temp_point1=new ArrayList<ArrayList<Integer>>();
			temp_point1=double_add(alpha,j,ec,gf);
			temp_point1=ec.point_inverse(temp_point1,gf);
			temp_point=ec.point_addition(beta,temp_point1, gf);
			st=new Shanks_tuple();
			st.point=temp_point; st.x=j;
			//System.out.println("in jvals " +j);
			i_vals.add(st);
			
		}
		Collections.sort(j_vals);
		Collections.sort(i_vals);
		int final_j=0,final_i=0,flag=0;
		for (Shanks_tuple s_t1 : j_vals) 
		{
		    int key1 = s_t1.x;
		    ArrayList<ArrayList<Integer>> value1 = new ArrayList<ArrayList<Integer>>();
		    	value1=s_t1.point;
		    		for (Shanks_tuple s_t2 : i_vals) 
		    		{
		    		    int key2 = s_t2.x;
		    		    ArrayList<ArrayList<Integer>> value2 = new ArrayList<ArrayList<Integer>>();
		    		    	value2=s_t2.point;
		    		    	if (ec.point_equals(value1, value2))
		    		    	{
		    		    		flag=1;
		    		    		final_j=key1;
		    		    		final_i=key2;
		    		    	//	System.out.println("i and j: "+final_i+" "+final_j+" "+value1+" "+value2);
		    		    		break;
		    		   
		    		    	}
		    		}
		    	if (flag==1)
		    	return (m*final_j+final_i);
		}    
		return 0;
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
		int k;
		String a="1000000";String b="1000001";int n=124;
		ArrayList<ArrayList<Integer>> alpha=new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> beta=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> x_point=new ArrayList<Integer>();
		ArrayList<Integer> y_point=new ArrayList<Integer>();
		String x,y;
		
		
//		Order for point alpha= 31 for k=7
//		k=7;
//		Fields gf=new Fields(k);n=124;
//		a="1000000";b="1000001";
//		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
//		x="1100100";y="1100010";
//		x_point.addAll(gf.StringToList(x));
//		y_point.addAll(gf.StringToList(y));
//		alpha.add(x_point);alpha.add(y_point);
//		beta.addAll(Shanks.double_add(alpha, 136, ec, gf));
//		x_point=new ArrayList<Integer>();
//		y_point=new ArrayList<Integer>();
//		
//		k=35 
//				a=”10000”; b=”10001”;
//				cardinality=number of points=34359374628
//				Point P=(101,10100)
//				Order of P=3817708292

//		k=35;
//		Fields gf=new Fields(k);
//		long n1=34359374628L;
//		a="10000";b="10001";
//		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
//		x="101";y="10100";
//		x_point.addAll(gf.StringToList(x));
//		y_point.addAll(gf.StringToList(y));
//		alpha.add(x_point);alpha.add(y_point);
//		beta.addAll(Shanks.double_add(alpha, 136, ec, gf));
//		x_point=new ArrayList<Integer>();
//		y_point=new ArrayList<Integer>();


		k=25;
		Fields gf=new Fields(k);
		n=33561148;
		a="10000";b="10001";
		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
		x="101";y="10100";
		x_point.addAll(gf.StringToList(x));
		y_point.addAll(gf.StringToList(y));
		alpha.add(x_point);alpha.add(y_point);
		beta.addAll(Shanks.double_add(alpha, 136, ec, gf));
		x_point=new ArrayList<Integer>();
		y_point=new ArrayList<Integer>();

		int ans=shanks(alpha,beta,n, ec, gf);
		System.out.println(alpha+" "+beta );
		System.out.println("Shanks solution: "+ans);
		System.out.println(double_add(alpha, ans, ec, gf));

		

		
		
	}
	
}
