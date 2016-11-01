package capstone;

import java.util.ArrayList;

public class test {
 public static ArrayList<Integer> irreducible;
	public static ArrayList<Integer> helper_mod(ArrayList<Integer> poly)
	{
		ArrayList<Integer> result=new ArrayList<Integer>();
		if (poly.size()<irreducible.size())
			return poly;
		result.addAll(poly);
		while (result.size()>=irreducible.size())
		result=shift_XOR(result);
		return result;
		
	}
	
	public static ArrayList<Integer> shift_XOR(ArrayList<Integer> poly)
	{
		ArrayList<Integer> result=new ArrayList<Integer>();
		ArrayList<Integer> temp_irreducible=new ArrayList<Integer>();
		temp_irreducible.addAll(irreducible);
		while (temp_irreducible.size()<poly.size())
			temp_irreducible.add(0,0);
		for (int i=0;i<poly.size();i++)
		{
			int x=((temp_irreducible.get(i)+poly.get(i))%2);
			//if (x<0) x=x+2;
			result.add(x);
		}
		
		while (result.get(result.size()-1)==0)
			result.remove(result.size()-1);
		//System.out.println(result);
		//System.exit(0);
		return result;
	}
	public static ArrayList<Integer> new_SquareAndMult(ArrayList<Integer> poly,int x)
	{
	
	ArrayList<Integer> Q=new ArrayList<Integer>();
	ArrayList<Integer> N=new ArrayList<Integer>();
	
//	Q=null;
	//ArrayList<ArrayList<Integer>> N=new ArrayList<ArrayList<Integer>>();
	N.addAll(poly);
	
	String s=Integer.toBinaryString(x);
	ArrayList<Integer> m=new ArrayList<Integer>();
	//System.out.println(s);
	Q.add(1);
		//m=Fields.StringToList(s);
	//ArrayList<Integer> answer=new ArrayList<Integer>();
	for (int i=0;i<s.length();i++)
	{
		//System.out.println("idhar"+s.charAt(i));
		if (Integer.parseInt(""+s.charAt(i))==1)
		{
			Q=helper_mod(Fields.Multiply(Q, Q));
		Q=helper_mod(Fields.Multiply(Q, N));
		}
		else 
			Q=helper_mod(Fields.Multiply(Q, Q));

			
	}
	return Q;
}


	
	public static ArrayList<Integer> SquareAndMult(ArrayList<Integer> poly,int x)
	{
		/**
		 * This method takes two polynomials in ArrayListform and returns 
		 * (poly^x)mod irreducible
		 * 
		 * @param       poly    Array list representing  polynomial
		 * @param       x    power to which polynomial is to be raised. 
		
		 
		 */
				ArrayList<Integer> answer=new ArrayList<Integer>();
		answer.add(1);
		
		for (int j=1;j<=x;j++)
		{
		answer=Fields.Multiply(answer,poly);
		answer=helper_mod(answer);
		}
		return answer;
	}
	public static ArrayList<ArrayList<Integer>> point_multiple(ArrayList<ArrayList<Integer>> alpha,int x,Elliptic_Curve ec, Fields gf)
	{
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
		String s=Integer.toBinaryString(x);
		ArrayList<Integer> m=new ArrayList<Integer>();
		m=gf.StringToList(s);
		for (int i=0;i<m.size();i++)
		{
			//System.out.println("idhar");
			if (m.get(i)==1)
				Q=ec.point_addition(Q, N, gf);
			N=ec.point_addition(N, N, gf);
		}
		return Q;
	}

	public static void main(String args[])
	{
//		irreducible=new ArrayList<Integer>() ;
//		irreducible.add(1);irreducible.add(1);irreducible.add(0);irreducible.add(1);
//		System.out.println(irreducible);
		//ArrayList<Integer> poly=new ArrayList<Integer>() ;
		//poly.add(1);poly.add(1);poly.add(0);poly.add(1);poly.add(1);
		//poly.add(0);poly.add(1);poly.add(1);
	//	System.out.println(poly);
		//helper_mod(poly);
		int m=11;
//		System.out.println(SquareAndMult(poly, m));
//		System.out.println(new_SquareAndMult(poly, m));
		ArrayList<ArrayList<Integer>> alpha=new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> beta=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> A=new ArrayList<Integer>();
		ArrayList<Integer> B=new ArrayList<Integer>();
		ArrayList<Integer> x_point=new ArrayList<Integer>();
		ArrayList<Integer> y_point=new ArrayList<Integer>();
		String x,y,a,b;
		
//		int k=7;
//		int n=(int)Math.pow(2, k)-1;
//		a="1000000";b="1000001";
//		Fields gf=new Fields(k);
//		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
//		x="1111011";y="1110000";
//		x_point=Fields.StringToList(x);y_point=Fields.StringToList(y);
//		alpha.add(x_point);alpha.add(y_point);
//		point_generator.point_generation(ec, gf, k);
//		point_generator.display_points(ec, k);
//		 x_point=new ArrayList<Integer>();
//		 y_point=new ArrayList<Integer>();
//		
//		System.out.println(Shanks.double_add(alpha, m, ec, gf));
//		System.out.println(Shanks.point_multiple(alpha, m, ec, gf));
//		
		for (int i=0;i<Math.pow(2, 20);i++)
			for (int j=0;j<Math.pow(2, 20);j++)
				System.out.println("going");
}
}