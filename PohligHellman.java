package capstone;

import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class PohligHellman {

	public static ArrayList<Integer> Pohlig_Hellman(ArrayList<ArrayList<Integer>> alpha,ArrayList<ArrayList<Integer>> beta, long n,Elliptic_Curve ec, Fields gf, int q,int c)
	{
		int j=0;
		ArrayList<Integer> a=new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> delta=new ArrayList<ArrayList<Integer>>(); 
		ArrayList<ArrayList<ArrayList<Integer>>> beta_list=new ArrayList<ArrayList<ArrayList<Integer>>>(); 
		beta_list.add(j,beta);
		while (j<=c-1)
		{
			int power=(int) (n/(int)(Math.pow(q, j+1)));
			delta=Shanks.double_add(beta_list.get(j), power, ec, gf);
			int i_val=0;
			i_val=Shanks.shanks(Shanks.double_add(alpha, (int) ((n)/q), ec, gf),delta,n,ec,gf);
			a.add(j,i_val);
			ArrayList<ArrayList<Integer>> temp=new ArrayList<ArrayList<Integer>>();
			power=(int) (a.get(j)*Math.pow(q, j));
			temp=Shanks.double_add(alpha, power, ec, gf);
			temp=Elliptic_Curve.point_inverse(temp, gf);
			temp=Elliptic_Curve.point_addition(temp, beta_list.get(j), gf);
			beta_list.add(j+1, temp);
			j++;
		}
		return a;
		
	}
	
	public static int CRT(ArrayList<Integer> x,ArrayList<Integer> n)
	{
		ArrayList<Integer> m=new ArrayList<Integer>();
		ArrayList<Integer> y=new ArrayList<Integer>();
		
		int n_val=1;
		for (int i=0;i<n.size();i++)
		{
			n_val=n_val*n.get(i);
			int ans=1;
			for (int j=0;j<n.size();j++)
			{
				if (j==i)
			continue;
				else
					ans=ans*n.get(j);
			}
			m.add(i,ans);
			
			BigInteger n1=new BigInteger(""+n.get(i));
			BigInteger m1=new BigInteger(""+m.get(i));
			int y_val=m1.modInverse(n1).intValue();
			y.add(i,y_val);
		}
		int answer=0;
		for (int i=0;i<n.size();i++)
		answer+=x.get(i)*m.get(i)*y.get(i);
		answer=answer%n_val;
		System.out.println(answer%n_val);
		return answer;
	}
	public static void main(String args[])throws IOException
	{
		String a,b;
		int n,k;
		ArrayList<ArrayList<Integer>> alpha=new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> beta=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> x_point=new ArrayList<Integer>();
		ArrayList<Integer> y_point=new ArrayList<Integer>();
		String x,y;
		
		ArrayList<Integer> primes=new ArrayList<Integer>();
		BufferedReader inp = new BufferedReader(new FileReader(new File("primes.txt"))) ;
		    String line;
		    while ((line = inp.readLine()) != null) {
		       // process the line.
		    	primes.add(Integer.parseInt(line));
		    }
		    
		   // System.out.println(primes.size());
		
		a="1000000";b="1000001";
		k=7;
		
		Fields gf=new Fields(k);
		Elliptic_Curve ec=new Elliptic_Curve(a,b,gf);
		//Order for point (0010000,0010010): 62 n=62
		x="10000";y="10010";n=62; //order n=62
		//x="1100100";y="1100010"; //order n=31
		x_point.addAll(gf.StringToList(x));
		y_point.addAll(gf.StringToList(y));
		alpha.add(x_point);alpha.add(y_point);
		beta.addAll(Shanks.double_add(alpha, 236, ec, gf));
		x_point=new ArrayList<Integer>();
		y_point=new ArrayList<Integer>();
		int prime_pointer=0;
		int counter=0;
		ArrayList<Integer> x_vals=new ArrayList<Integer>();
		ArrayList<Integer> n_vals=new ArrayList<Integer>();
		ArrayList<Integer> a_vals=new ArrayList<Integer>();
		int a1=0,ans=0;
		int n_initial=n;
		while (n!=1)
		{
			int c=1;
			int q=primes.get(prime_pointer);
			int n1=n;
			if (n1%q==0){
			while (n1%Math.pow(q, c)==0)
			{
				c++;
				//System.out.println(n+" "+p);
				
				n=(int) (n/q);
				
			}
			c--;
			//n=(int) (n%Math.pow(p, c));
		System.out.println(q+" "+c+" ");
		a_vals=new ArrayList<Integer>();
		a_vals=Pohlig_Hellman(alpha, beta, n_initial, ec, gf, q, c);
		ans=0;
		for (int i=0;i<a_vals.size();i++)
		ans+=(int) ((a_vals.get(i)*Math.pow(q, i))%Math.pow(q, c));
		x_vals.add(counter,ans);
		n_vals.add(counter,(int) Math.pow(q, c));
		counter++;
		}
			prime_pointer++;
		}
//		ArrayList<Integer> x1=new ArrayList<Integer>();
//		x1.add(7);x1.add(11);x1.add(13);
//		ArrayList<Integer> x2=new ArrayList<Integer>();
//		x2.add(5);x2.add(3);x2.add(10);
//		
//		long startTime = System.nanoTime();
//		//code
//		long endTime = System.nanoTime();
//		System.out.println("Took "+(endTime - startTime) + " ns"); 
		
		System.out.println("PohligHellman: "+CRT(x_vals,n_vals));
		System.out.println("Shanks: "+Shanks.shanks(alpha, beta, n_initial, ec, gf));
		//System.out.println("PollardRho: "+Pollard_rho.pollard_rho(alpha, beta, n_initial, ec, gf));
		System.out.println(beta);
		//System.out.println(Pohlig_Hellman(alpha, beta, n, ec, gf, c, q));
	}
}
