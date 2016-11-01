package capstone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class point_generator {
	
public static void display_points(Elliptic_Curve ec,int k)
{
	System.out.println("The points on the curve: ");
	for (int i=0;i<ec.list_of_points.size();i++)
	{
	//	System.out.print(i+" ");
		System.out.println("("+ec.ListToString(ec.list_of_points.get(i).get(0),k)+","+ec.ListToString(ec.list_of_points.get(i).get(1),k)+")");	
	
	}
	System.out.println("Number of points including point at infinity: "+ (ec.list_of_points.size()+1));
}
	
	public static void point_generation(Elliptic_Curve ec, Fields gf,int k)
	{
		ArrayList<Integer> alpha=new ArrayList<Integer>();
		ArrayList<Integer> prime_factors=new ArrayList<Integer>();
		int n=(int)Math.pow(2, k)-1;	
		ArrayList<Integer> one=new ArrayList<Integer>();
		one.add(1);
		ArrayList<Integer> zero=new ArrayList<Integer>();
		zero.add(0);
		int counter=2;
		ArrayList<Integer> x_point=new ArrayList<Integer>();
		ArrayList<Integer> left_equation_value=new ArrayList<Integer>();
		ArrayList<Integer> right_equation_value=new ArrayList<Integer>();
		ArrayList<Integer> y1=new ArrayList<Integer>();
		ArrayList<Integer> y2=new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> point1=new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> point2=new ArrayList<ArrayList<Integer>>();
		//y^2 + x*y = x^3 + a*x^2 + b, 
		for (int i=0;i<Math.pow(2, k);i++)
		{
			point1=new ArrayList<ArrayList<Integer>>();
			point2=new ArrayList<ArrayList<Integer>>();
			x_point=new ArrayList<Integer>();
			x_point=Fields.StringToList(Integer.toBinaryString(i));
			right_equation_value=new ArrayList<Integer>();
			right_equation_value=ec.right_equation_value(x_point,gf);
			int flag=0, c=0;
			for (int j=0;j<Math.pow(2, k);j++)
			{
				left_equation_value=new ArrayList<Integer>();
				left_equation_value=ec.left_equation_value(x_point, Fields.StringToList(Integer.toBinaryString(j)), gf);
				if (ec.equals(left_equation_value,right_equation_value))
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
				y1=Fields.StringToList(Integer.toBinaryString(c));
				point1.add(x_point);point1.add(y1);
				ec.list_of_points.add(point1);
				
				point2=ec.point_inverse(point1, gf);
				if (!ec.point_equals(point1, point2))
				ec.list_of_points.add(point2);
//				if (ec.list_of_points.size()>=5)
//					return;
			}
		}
	}
	
	public static void main(String args[])throws IOException
	{
		
		ArrayList<ArrayList<Integer>> alpha=new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> beta=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> A=new ArrayList<Integer>();
		ArrayList<Integer> B=new ArrayList<Integer>();
		ArrayList<Integer> x_point=new ArrayList<Integer>();
		ArrayList<Integer> y_point=new ArrayList<Integer>();
		String x,y,a,b;int k;
		
		
		//point 1 for k=49
		k=49;
		//int n=(int)Math.pow(2, k)-1;
		long n=562949961122844L;
		ArrayList<Integer> prime_factors=new ArrayList<Integer>();
		a="10000";b="10001";
		Fields gf=new Fields(k);
		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
		x="101";y="10100";
		x_point=Fields.StringToList(x);y_point=Fields.StringToList(y);
		alpha.add(x_point);alpha.add(y_point);
//		
		//point 2 for k=7
//		k=7;
//		//int n=(int)Math.pow(2, k)-1;
//		long n=124;
//		ArrayList<Integer> prime_factors=new ArrayList<Integer>();
//		a="1000000";b="1000001";
//		Fields gf=new Fields(k);
//		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
//		point_generation(ec, gf, k);
//		display_points(ec, k);
		
//		x="1111011";y="1110000";
//		x_point=Fields.StringToList(x);y_point=Fields.StringToList(y);
//		alpha.add(x_point);alpha.add(y_point);
	
//		 x_point=new ArrayList<Integer>();
//		 y_point=new ArrayList<Integer>();
//		 
		 for (int i=2;i<10;i++)
		 {
		System.out.println((Shanks.double_add(alpha, i, ec, gf)));
		 }
	//		 System.out.println(Shanks.shanks(alpha, beta, n, ec, gf));
		//System.out.println(ec.point_addition(alpha, alpha, gf));
				
		
		
	}
		

}
