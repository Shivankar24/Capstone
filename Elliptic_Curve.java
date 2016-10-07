package capstone;


import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Elliptic_Curve {
	
	ArrayList<Integer> A,B;
	ArrayList<ArrayList<ArrayList<Integer>>> list_of_points;
	
	public Elliptic_Curve(String a, String b,Fields gf) {
		// TODO Auto-generated constructor stub
		A=gf.StringToList(a);
		B=gf.StringToList(b);
		list_of_points=new ArrayList<ArrayList<ArrayList<Integer>>>();
	}
		private static ArrayList<Integer> factors(int n) {
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
		
		x1.addAll(point1.get(0));y1.addAll(point1.get(1));
		x2.addAll(point2.get(0));y2.addAll(point2.get(1));
		
		//if the points are inverse of each other 
		//then I have used Null to denote O
		
//		if (x1==x2 && y2==-y1)
//			return null;
		
		if (equals(x1,x2) && equals(y2,gf.Add(x1,y1)))
			return null;
		
		//if the points are same, we calculate the 
		//slope of the line accordingly. 
		ArrayList<Integer> bi=new ArrayList<Integer>();
		ArrayList<Integer> bi_inverse=new ArrayList<Integer>();
		ArrayList<Integer> temp=new ArrayList<Integer>();
		ArrayList<Integer> temp1=new ArrayList<Integer>();
		
		if (equals(x1,x2) && equals(y2,y1))
		{
			bi_inverse.addAll(gf.inverse(x1));
			temp=gf.helper_mod(gf.Multiply(y1,bi_inverse));
			lambda=gf.Add(x1,temp);
			lambda=gf.helper_mod(lambda);
//			bi=new BigInteger(""+(2*y1));
//			bi=mod_inverse(bi,new BigInteger(""+p));
//			lambda=(3*x1*x1+1)*bi.intValue();
//			lambda=lambda%p;
		}		
		else 
		{
			bi.addAll(gf.Subtract(x2,x1));
			bi_inverse.addAll(gf.inverse(bi));
			lambda.addAll(gf.Multiply(bi_inverse, gf.Subtract(y2, y1)));
			lambda=gf.helper_mod(lambda);
			
//			bi=new BigInteger(""+(x2-x1));
//			bi=mod_inverse(bi,new BigInteger(""+p));
//			lambda=(y2-y1)*bi.intValue();
//			lambda=lambda%p;
		}
		
		//calculation of the coordinates of the new point obtained 
		//after addition is done as follows: 
		//lambda=66;
		//int x3=lambda*lambda-x1-x2;
//		x3=x3%p;
//		
//		if (x3<0)
//			x3=x3+p;
//		int y3=lambda*(x1-x3)-y1;
//		y3=y3%p;

		ArrayList<Integer> x3=new ArrayList<Integer>();
		ArrayList<Integer> y3=new ArrayList<Integer>();
		x3.addAll(gf.helper_mod(gf.Subtract(gf.Subtract(gf.SquareAndMult(lambda,2),x1),x2)));
		//x3=x3%p;
//		if (x3<0)
//			x3=x3+p;
		y3.addAll(gf.helper_mod(gf.Subtract((gf.Multiply(lambda,gf.Subtract(x1,x3))),y1)));
	//	y3=y3%p;
//		System.out.println(x1+" "+y1+" "+x2+" "+y2);
//		System.out.println(lambda+" "+x3+" "+y3);
		
//		if (y3<0)
//			y3=y3+p;
		new_point.add(x3);
		new_point.add(y3);
		
		return new_point;
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
		System.out.println("Elliptic curve type: y^2 + x*y = x^3 + a*x^2 + b,");
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
		int counter=1;
		generator_powers.add(zero);
		generator_powers.add(alpha);
		while(!equals(element, one))
		{
			element=Fields.Multiply(element, alpha);
			element=Fields.helper_mod(element);
			generator_powers.add(counter,element);
			counter++;
		}
		
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
			left_equation_value=new ArrayList<Integer>();
			//System.out.println("loop "+i);
			x_point=generator_powers.get(i);
			right_equation_value=ec.right_equation_value(x_point,gf);
			int flag=0, c=0;
			for (int j=1;j<generator_powers.size();j++)
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
				y2=gf.Add(x_point,y1);
				point1.add(x_point);point1.add(y1);
				point2.add(x_point);point2.add(y2);
				//System.out.println(point1+" "+point2);
				ec.list_of_points.add(point1);
				if (!equals(y1,y2))
				ec.list_of_points.add(point2);
				
			}
		}
		System.out.println("The points on the curve: ");
		for (int i=0;i<ec.list_of_points.size();i++)
		{
			//System.out.print(ec.list_of_points.get(i)+" ");
			System.out.println("("+ListToString(ec.list_of_points.get(i).get(0),k)+","+ListToString(ec.list_of_points.get(i).get(1),k)+")");	
		}
		
		ArrayList<ArrayList<Integer>> largest_order_element=new ArrayList<ArrayList<Integer>>();
		int largest_order=0;
		
//		for (int i=0;i<ec.list_of_points.size();i++)
//		{
//			ArrayList<ArrayList<Integer>> P1=new ArrayList<ArrayList<Integer>>();
//			ArrayList<ArrayList<Integer>> P2=new ArrayList<ArrayList<Integer>>();
//			P1.addAll(ec.list_of_points.get(i));
//			P2.addAll(P1);
//			int order=1;
//			int flag=0;
//			do
//			{
//				//we keep on adding the point with itself
//				//till we get the unit element 
//				//which in this case i have taken as null. 
//				//the moment that happens, we have found the 
//				//order of the element in consideration. 
//				order++;
//				P2=point_addition(P1, P2,gf);
//				if (order>Math.pow(2, k))
//				{
//					flag=1;
//					break;
//				}
//			}while(point2!=null);
//			if (flag==1)
//				continue;
//			else
//				System.out.println("Order for point "+ec.list_of_points.get(i)+": "+order);
			
			//check for the largest order 
			//store the equivalent point on EC. 
//			if (order>largest_order)
//			{
//				largest_order_element=new ArrayList<ArrayList<Integer>>();
//				largest_order=order;
//				largest_order_element.addAll(P1);
//			}
//			if (order==largest_order)
//			{
//				largest_order_element.addAll(P1);
//			}
//
			
//		}

		
		//		ArrayList<Integer> X1=new ArrayList<Integer>();
//		ArrayList<Integer> Y1=new ArrayList<Integer>();
//		ArrayList<Integer> X2=new ArrayList<Integer>();
//		ArrayList<Integer> Y2=new ArrayList<Integer>();
//		X1=Fields.StringToList("110");
//		Y1=Fields.StringToList("1");
//		X2=Fields.StringToList("11");
//		Y2=Fields.StringToList("101");		
//		ArrayList<ArrayList<Integer>> P1=new ArrayList<ArrayList<Integer>>();
//		ArrayList<ArrayList<Integer>> P2=new ArrayList<ArrayList<Integer>>();
//		P1.add(X1);P1.add(Y1);
//		P2.add(X2);P2.add(Y2);
		//System.out.println(point_addition(P1, P2, gf));	
	}
}
