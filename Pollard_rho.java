package capstone;

import java.util.ArrayList;
import java.io.*;
import java.math.BigInteger;

public class Pollard_rho {

	
		public static Tuple f(Tuple t,long n,ArrayList<ArrayList<Integer>> alpha,ArrayList<ArrayList<Integer>> beta,Elliptic_Curve ec,Fields gf)
	{
		Tuple result=new Tuple();
		ArrayList<ArrayList<Integer>> temp=new ArrayList<ArrayList<Integer>>();
		int test_val=point_test(t.x, gf.k);
		if (test_val==1){
			temp=ec.point_addition(t.x, beta, gf);
			if (temp!=null)
			result.x.addAll(temp);
			else 
				result.x.add(null);
			result.a=t.a;
			result.b=(int) ((t.b+1)%n);
		}
		else if (test_val==2){
			temp=ec.point_addition(t.x, t.x, gf);
			if (temp!=null)
			result.x.addAll(temp);
			else 
				result.x.add(null);
			result.a=(int) ((2*t.a)%n);
			result.b=(int) ((2*t.b)%n);
		}
		else {
			temp=ec.point_addition(t.x, alpha, gf);
			if (temp!=null)
			result.x.addAll(temp);
			else 
				result.x.add(null);
			result.a=(int) ((t.a+1)%n);
			result.b=t.b;
		}
		return result;
	}
	
		public static int pollard_rho(ArrayList<ArrayList<Integer>> alpha,ArrayList<ArrayList<Integer>> beta, long n,Elliptic_Curve ec, Fields gf)
		{
			Tuple Start=new Tuple();
			Start.x=null;
			Start.a=0;Start.b=0;
			Tuple t=new Tuple();
			Tuple t_dash=new Tuple();
			
			t=f(Start, n, alpha, beta, ec, gf);
			t_dash=f(t,n,alpha,beta,ec,gf);
			int counter=0;
			while(!ec.point_equals(t.x,t_dash.x) && counter<n)
			{
				System.out.println(counter++);
			
				t=f(t,n,alpha,beta,ec,gf);
				t_dash=f(t_dash,n,alpha,beta,ec,gf);
				t_dash=f(t_dash,n,alpha,beta,ec,gf);
				}
//			System.out.println("t "+t.x+" "+t.a+" "+t.b);
//			System.out.println("t "+t_dash.x+" "+t_dash.a+" "+t_dash.b);
			
			BigInteger b_diff=new BigInteger(""+(t_dash.b-t.b));
			BigInteger a_diff=new BigInteger(""+(t.a-t_dash.a));
			BigInteger n_val=new BigInteger(""+n);
			
			int ans=(int) ((a_diff.multiply(b_diff.modInverse(n_val))).intValue()%n);
			if (ans<0)
				ans=(int) (ans+n);
			
			return ans;
		}
	public static int point_test(ArrayList<ArrayList<Integer>> x,int k) 
	{
			
		if (x==null)
				return 1;
		if (x.isEmpty())
			return 1;
		if (x.get(0)==null)
				return 1;
		if (Shanks.ListToInt(x.get(0))%3==0)
			return 1;
		if (Shanks.ListToInt(x.get(0))%3==1)
			return 2;
		
		return 3;
	}
	public static void main(String args[])throws IOException
	{
		
		BufferedReader inp=new BufferedReader(new InputStreamReader(System.in));
		int k;
		String a,b;
		int n;
		ArrayList<ArrayList<Integer>> alpha=new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> beta=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> x_point=new ArrayList<Integer>();
		ArrayList<Integer> y_point=new ArrayList<Integer>();
		String x,y;
		

		
//		Order for point alpha= 31 for k=7
//		a="1000000";b="1000001";
//		k=7;
//		n=31;
//		Fields gf=new Fields(k);
//		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
//		x="1100100";y="1100010";
//		x_point.addAll(gf.StringToList(x));
//		y_point.addAll(gf.StringToList(y));
//		alpha.add(x_point);alpha.add(y_point);
//		beta.addAll(Shanks.double_add(alpha, 236, ec, gf));
//		x_point=new ArrayList<Integer>();
//		y_point=new ArrayList<Integer>();

	//	Order for point (00111001000,01011111000): 47
		k=11;n=47;
		a="1000";b="1001";
		Fields gf=new Fields(k);
		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
		x="111001000";y="1011111000";
		x_point.addAll(gf.StringToList(x));
		y_point.addAll(gf.StringToList(y));
		alpha.add(x_point);alpha.add(y_point);

		beta.addAll(Shanks.double_add(alpha, 299, ec, gf));
		
//		k=49;
//		long n1=562949961122844L;
//		ArrayList<Integer> prime_factors=new ArrayList<Integer>();
//		a="10000";b="10001";
//		Fields gf=new Fields(k);
//		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
//		x="101";y="10100";
//		x_point=Fields.StringToList(x);y_point=Fields.StringToList(y);
//		alpha.add(x_point);alpha.add(y_point);
//		beta.addAll(Shanks.double_add(alpha, 4, ec, gf));
//		System.out.println(beta);
//		
		
		
//		k=25;
//		Fields gf=new Fields(k);
//		long n1=33561148;
//		a="10000";b="10001";
//		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
//		x="101";y="10100";
//		x_point.addAll(gf.StringToList(x));
//		y_point.addAll(gf.StringToList(y));
//		alpha.add(x_point);alpha.add(y_point);
//		beta.addAll(Shanks.double_add(alpha, 131, ec, gf));

		
		System.out.println(alpha+" "+beta );
		int ans=pollard_rho(alpha, beta, n, ec, gf);
		System.out.println("Pollard rho solution: "+ans);
//		for (int i=1;i<10;i++)
		System.out.println(Shanks.double_add(alpha, ans, ec, gf));
		

		//System.out.println(shanks(alpha,beta,n, ec, gf));
		
}
}
