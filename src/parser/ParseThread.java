package parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.HashSet;

import org.jsoup.HttpStatusException;

public class ParseThread implements Runnable
{
	private int threadID;
	private int min;
	private int max;
	private int ttl;
	private double rate;
	private Proxy proxy;
	
	private final String sql="insert into 2015_2016_playerinfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String sql2="insert into 2015_2016_matchinfo values(?,?,?,?,?,?,?,?,?,?,?,?)";
	public int getThreadID() {
		return threadID;
	}
	public void setThreadID(int threadID) {
		this.threadID = threadID;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public Proxy getProxy() {
		return proxy;
	}
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	
	public int getTTL() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("0.00");
		System.out.println("ThreadID="+threadID+" , minID="+min+" , maxID="+max+" , Sample Rate="+rate+" , Proxy="+proxy.toString());
		System.out.println("Start Parsing...");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String dbUrl="jdbc:mysql://localhost:3306/dotamatch";
		String userName="root";
		String userPass="123456";
		try (Connection conn=DriverManager.getConnection(dbUrl, userName, userPass)){	
			PreparedStatement preStmt=conn.prepareStatement(sql);
			PreparedStatement preStmt2=conn.prepareStatement(sql2);
			int nums=(int) ((max-min)*rate);
			String url =new String();
			HashSet<Integer> randSet = new HashSet<Integer>();
			GetRandom.randomSet(min,max,nums,randSet);
			//System.out.println(randSet.size());
			int count=0;
			int validCount=0;
			int setSize=randSet.size();
			for(Integer i:randSet)
			{
				url="http://dotamax.com/match/detail/"+i.toString();
				Parse parse=new Parse(conn,proxy,threadID,preStmt,preStmt2);
				parse.parseMatch(url);
				count++;
				if(parse.writtenFlag)
				{
					validCount++;					
					double percentage=(double)count/(double)setSize*100;
					String perStr=df.format(percentage);
					System.out.println("Thread ID = "+threadID+" Match ID = "+i.toString()+" Parsed Completely! Complete :"+validCount+",Count = "+count+" of "+setSize+", Percentage = "+perStr+"%");
				}
				//Thread.sleep(100);
			}
			System.out.println("Thread ID = "+threadID+" terminated. Parsed "+validCount+" lines totally.");
			preStmt.close();
		} catch (HttpStatusException e) {
			System.out.println("This match doesn't exist.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}

}
