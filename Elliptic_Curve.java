package capstone;


import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Elliptic_Curve {
	/** 
	   
	 * @author      Shivankar Ojha
	 * 
	 
	 */
	
	static ArrayList<Integer> A;
	ArrayList<Integer> B;
	ArrayList<ArrayList<ArrayList<Integer>>> list_of_points;
	
	public Elliptic_Curve(String a, String b,Fields gf) {
		// TODO Auto-generated constructor stub
		A=gf.StringToList(a);
		B=gf.StringToList(b);
		
		list_of_points=new ArrayList<ArrayList<ArrayList<Integer>>>();
		
		
	}
		public static ArrayList<Integer> factors(int n) {
		// TODO Auto-generated method stub
		ArrayList<Integer> factors=new ArrayList<Integer>(); 
		for (int i=2;i<=n;i++)
		{
			if (n%i==0 && prime_check(i))
				factors.add(i);
		}
		return factors;
	}
	public static boolean prime_check(int n) {
		// TODO Auto-generated method stub
		for (int i=2;i<n;i++)
			if (n%i==0)
				return false;
		return true;	
	}
	public static String ListToString(ArrayList<Integer> list,int k) {
		// TODO Auto-generated method stub
		String s=new String();
		String s1=new String();
		for (int i=0;i<list.size();i++)
			{
			s=s+list.get(i);
			}
		if (s.length()<k)
		{
			while(s.length()<k)
			{
				s=s+0;
			}
		}
		//s1=s1+s;
		s=Reverse(s);
		return s;	
	}
	
	public static String Reverse(String s) {
		// TODO Auto-generated method stub
		String s1=new String();
		for (int i=s.length()-1;i>=0;i--)
			s1=s1+s.charAt(i);
		
		return s1;	
	}
	public static boolean generator_check(ArrayList<Integer> poly,int n,ArrayList<Integer> factors,Fields gf)
	{
		ArrayList<Integer> one=new ArrayList<Integer>();
		one.add(1);
		ArrayList<Integer> b=new ArrayList<Integer>();
		for (int i=0;i<factors.size();i++)
		{
			int x=n/(int)factors.get(i);
			b=gf.SquareAndMult(poly, x);
			if (equals(b, one))
				return false;
		}
		return true;
	}
	
	public ArrayList<Integer> right_equation_value(ArrayList<Integer> x,Fields gf)
	{
		//y^2 + x*y = x^3 + a*x^2 + b,
		ArrayList<Integer> result=new ArrayList<Integer>();
		result.addAll(gf.SquareAndMult(x, 3));
		ArrayList<Integer> poly=new ArrayList<Integer>();
		poly=gf.SquareAndMult(x, 2);
		poly=gf.helper_mod(gf.Multiply(poly, A));
		result=gf.Add(result, poly);
		result=gf.Add(result, B);
		
		return result;
	}
	
	public ArrayList<Integer> left_equation_value(ArrayList<Integer> x,ArrayList<Integer> y,Fields gf)
	{
		//y^2 + x*y = x^3 + a*x^2 + b,
		ArrayList<Integer> result=new ArrayList<Integer>();
		result.addAll(gf.SquareAndMult(y, 2));
		ArrayList<Integer> poly=new ArrayList<Integer>();
		poly=gf.helper_mod(gf.Multiply(x, y));
		result=gf.Add(result, poly);
		return result;
		
	}
	
	public static Boolean equals(ArrayList<Integer> a,ArrayList<Integer> b)
	{
		if ((a==null && b!=null) || (b==null && a!=null) || (a.isEmpty() && !b.isEmpty()) || (b.isEmpty() && !a.isEmpty()))
			return false;
		
		if (a.size()!=b.size())
			return false;
		for (int i=0;i<a.size();i++)
			if (a.get(i)!=b.get(i))
			return false;
		return true;
	}
	public static ArrayList<ArrayList<Integer>> point_addition(ArrayList<ArrayList<Integer>> point1,ArrayList<ArrayList<Integer>> point2,Fields gf)
	{
		//This function returns the point which is 
		//the result of the addition of two points. 
		if (point1==null || point1.isEmpty())
			return point2;
		if (point2==null || point2.isEmpty())
			return point1;
		if (point1.get(0)==null)
			return point2;
//		if (point1==null && point2.isEmpty())
//			return null;
//		if (point2==null || point1.isEmpty())
//			return null;
		//Array List to store the coordinates of the new point. 
		ArrayList<ArrayList<Integer>> new_point=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> x1=new ArrayList<Integer>();
		ArrayList<Integer> y1=new ArrayList<Integer>();
		ArrayList<Integer> x2=new ArrayList<Integer>();
		ArrayList<Integer> y2=new ArrayList<Integer>();
		ArrayList<Integer> one=new ArrayList<Integer>();
		one.add(1);
		//Lambda is basically the slope of the line
		//passing through the two points. 
		ArrayList<Integer> lambda=new ArrayList<Integer>();
		
		//x1,x2,y1,y2 store the respective x and y
		//values of the two points to be added. 
		x1.addAll(point1.get(0));
		y1.addAll(point1.get(1));
		x2.addAll(point2.get(0));
		y2.addAll(point2.get(1));
		
		//if the points are inverse of each other 
		//then I have used Null to denote O
		
		
		if (equals(x1,x2) && equals(y2,gf.Add(x1,y1)))
			return null;
		
		//if the points are same, we calculate the 
		//slope of the line accordingly. 
		ArrayList<Integer> bi=new ArrayList<Integer>();
		ArrayList<Integer> bi_inverse=new ArrayList<Integer>();
		ArrayList<Integer> temp=new ArrayList<Integer>();
		ArrayList<Integer> temp1=new ArrayList<Integer>();
		ArrayList<Integer> x3=new ArrayList<Integer>();
		ArrayList<Integer> y3=new ArrayList<Integer>();

		
		if (equals(x1,x2) && equals(y2,y1))
		{

			bi_inverse.addAll(gf.inverse(x1));
			
			temp=gf.helper_mod(gf.Multiply(y1,bi_inverse));
			lambda=gf.Add(x1,temp);
			
			
			lambda=gf.helper_mod(lambda);
			x3.addAll(gf.SquareAndMult(lambda, 2));
			x3=gf.Add(x3, lambda);
			x3=gf.Add(x3, A);
			y3.addAll(gf.SquareAndMult(x1, 2));
			y3=gf.Add(y3, gf.helper_mod(gf.Multiply(lambda,x3)));
			y3=gf.Add(y3, x3);
			if (x3.isEmpty())
				x3.add(0);
			if (y3.isEmpty())
				y3.add(0);
			new_point.add(x3);
			new_point.add(y3);
			return new_point;
		}		
		else 
		{
			bi.addAll(gf.Add(x2,x1));
			bi_inverse.addAll(gf.inverse(bi));
			lambda.addAll(gf.Multiply(bi_inverse, gf.Add(y2, y1)));
			lambda=gf.helper_mod(lambda);
			x3.addAll(gf.SquareAndMult(lambda, 2));
			x3=gf.Add(x3, lambda);
			x3=gf.Add(x3, x1);
			x3=gf.Add(x3, x2);
			x3=gf.Add(x3, A);
			y3.addAll(gf.helper_mod(gf.Multiply(lambda, gf.Add(x1, x3))));
			y3=gf.Add(y3,x3);
			y3=gf.Add(y3,y1);
			if (x3.isEmpty())
				x3.add(0);
			if (y3.isEmpty())
				y3.add(0);
			new_point.add(x3);
			new_point.add(y3);
			return new_point;
			
		}
		
	}
	public static ArrayList<ArrayList<Integer>> point_inverse(ArrayList<ArrayList<Integer>> p,Fields gf) {
		// TODO Auto-generated method stub
		if (p==null)
			return null;
		ArrayList<ArrayList<Integer>> inverse=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> x_point=new ArrayList<Integer>();
		ArrayList<Integer> y_point=new ArrayList<Integer>();
		x_point.addAll(p.get(0));
		y_point.addAll(gf.Add(x_point,p.get(1)));
		inverse.add(x_point);
		inverse.add(y_point);
		return inverse;
	}
	
	public static boolean point_equals(ArrayList<ArrayList<Integer>> p2, ArrayList<ArrayList<Integer>> p1) {
		// TODO Auto-generated method stub
		if (p1==null && p2==null)
			return true;
		if (p1==null && p2!=null)
			return false;
		if (p2==null && p1!=null)
			return false;
		if (p2==null)
			return false;
		if (p1==null)
			return false;
		if (p1.isEmpty())
			return false;
		if (p2.isEmpty())
			return false;
		if (p1.get(0)==null && p2.get(0)==null)
			return true;
		
		//System.out.println(p1+" "+p2);
		if (equals(p1.get(0),p2.get(0)) && equals(p1.get(1),p2.get(1)))
		return true;
		return false;
	}
	public static void main(String args[])throws IOException
	{
		BufferedReader inp=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the value of k for GF(2^k):" );
		int k=Integer.parseInt(inp.readLine());
		int n=(int)Math.pow(2, k)-1;
		ArrayList<Integer> prime_factors=new ArrayList<Integer>();
		prime_factors.addAll(factors(n));
		System.out.println("Prime factors of (2^k)-1: "+prime_factors);
		Fields gf=new Fields(k);
		System.out.println("irreducible: "+gf.irreducible);
		System.out.println("Elliptic curve type: y^2 + x*y = x^3 + a*x^2 + b");
		System.out.println("Enter the value of A for elliptic curve from GF(2^k): ");
		String a=(inp.readLine());
		System.out.println("Enter the value of B for elliptic curve from GF(2^k): ");
		String b=(inp.readLine());		
		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
		
		ArrayList<Integer> alpha=new ArrayList<Integer>();
		for (int i=1;i<=Math.pow(2,k)-1;i++)
		{
			alpha=gf.StringToList(Integer.toBinaryString(i));
			if (generator_check(alpha, n, prime_factors, gf))
				break;
		}
		System.out.println("generator/primitive element: "+alpha);
		
		ArrayList<ArrayList<Integer>> generator_powers=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> element=new ArrayList<Integer>();
		element.addAll(alpha);
		ArrayList<Integer> one=new ArrayList<Integer>();
		one.add(1);
		ArrayList<Integer> zero=new ArrayList<Integer>();
		zero.add(0);
		int counter=2;
		generator_powers.add(zero);
		generator_powers.add(alpha);
		while(!equals(element, one))
		{
			element=Fields.Multiply(element, alpha);
			element=Fields.helper_mod(element);
			//System.out.println("Counter: "+counter+" "+element+" "+generator_powers.contains(element));
			generator_powers.add(counter,element);
			counter++;
			
		}
//		for (int i=0;i<generator_powers.size();i++)
//			System.out.println(generator_powers.get(i));
		//System.exit(0);
		ArrayList<Integer> x_point=new ArrayList<Integer>();
		ArrayList<Integer> left_equation_value=new ArrayList<Integer>();
		ArrayList<Integer> right_equation_value=new ArrayList<Integer>();
		ArrayList<Integer> y1=new ArrayList<Integer>();
		ArrayList<Integer> y2=new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> point1=new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> point2=new ArrayList<ArrayList<Integer>>();
		//y^2 + x*y = x^3 + a*x^2 + b, 
		for (int i=0;i<generator_powers.size();i++)
		{
			point1=new ArrayList<ArrayList<Integer>>();
			point2=new ArrayList<ArrayList<Integer>>();
			x_point=new ArrayList<Integer>();
			right_equation_value=new ArrayList<Integer>();
			//System.out.println("loop "+i);
			x_point=generator_powers.get(i);
			right_equation_value=ec.right_equation_value(x_point,gf);
			int flag=0, c=0;
			for (int j=0;j<generator_powers.size();j++)
			{
				left_equation_value=new ArrayList<Integer>();
				left_equation_value=ec.left_equation_value(x_point, generator_powers.get(j), gf);
				if (equals(left_equation_value,right_equation_value))
				{
					flag=1;
					c=j;
					break;
				}
			}
			if (flag!=1)
				continue;
			if (flag==1)
			{		
				y1=generator_powers.get(c);
				point1.add(x_point);point1.add(y1);
				ec.list_of_points.add(point1);
				
				point2=ec.point_inverse(point1, gf);
				if (!point_equals(point1, point2))
				ec.list_of_points.add(point2);
			}
		}
	
		System.out.println("The points on the curve: ");
		for (int i=0;i<ec.list_of_points.size();i++)
		{
		//	System.out.print(i+" ");
			System.out.println("("+ListToString(ec.list_of_points.get(i).get(0),k)+","+ListToString(ec.list_of_points.get(i).get(1),k)+")");	
		
		}
		System.out.println("Number of points including point at infinity: "+ (ec.list_of_points.size()+1));
		
		
		for (int i=0;i<ec.list_of_points.size();i++)
		{
			ArrayList<ArrayList<Integer>> P1=new ArrayList<ArrayList<Integer>>();
			ArrayList<ArrayList<Integer>> P2=new ArrayList<ArrayList<Integer>>();
			P1.addAll(ec.list_of_points.get(i));
			P2.addAll(P1);
			//System.out.println("order search for "+P1);
			int order=1;
			int flag=0;
			do
			{
				//we keep on adding the point with itself
				//till we get the unit element 
				//which in this case i have taken as null. 
				//the moment that happens, we have found the 
				//order of the element in consideration. 
				order++;
				//System.out.println(P1+" "+P2);
				
				P2=point_addition(P1, P2,gf);
				//System.out.println("sum:"+P2);
				if (order>ec.list_of_points.size())
				{
					flag=1;
					break;
				}
			}while(P2!=null);
			if (flag==1)
				continue;
			else
				System.out.println("Order for point "+"("+ListToString(ec.list_of_points.get(i).get(0),k)+","+ListToString(ec.list_of_points.get(i).get(1),k)+")"+": "+order);
			
	
		}
//		

		//[[1, 1, 1, 1], [1, 1, 0, 1]]
			//	[[1, 1, 1, 1], [0, 0, 1]]
//		ArrayList<Integer> X1=new ArrayList<Integer>();
//		ArrayList<Integer> Y1=new ArrayList<Integer>();
//		ArrayList<Integer> X2=new ArrayList<Integer>();
//		ArrayList<Integer> Y2=new ArrayList<Integer>();
//		ArrayList<ArrayList<Integer>> P1=new ArrayList<ArrayList<Integer>>();
//		ArrayList<ArrayList<Integer>> P2=new ArrayList<ArrayList<Integer>>();
//		X1=Fields.StringToList("1111");
//		Y1=Fields.StringToList("1011");
//		X2=Fields.StringToList("1111");
//		Y2=Fields.StringToList("100");
//		P1.add(X1);P1.add(Y1);
//		P2.add(X2);P2.add(Y2);
//		System.out.println(P1+" "+P2);
//		System.out.println("point sum: "+point_addition(P1,P2,gf));
//		P1.add(X1);P1.add(Y1);
//		P2.addAll(P1);
		
		
	}
	
}
