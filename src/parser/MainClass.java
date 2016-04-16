package parser;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainClass {

	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException 
	{

		System.out.println("Finding proxies from Internet...");
		System.out.println("-------------------------------------------------------------------------------------------");

		ArrayList<Proxy> proxyList=GetProxy.getUsefulProxiesFromFile();
		//ArrayList<Proxy> proxyList=GetProxy.getUsefulProxiesFromKuaidaili(1);
		int proxyAvailable=proxyList.size();
		//int proxyAvailable=6;
		int threadBonus=10;//Threads for each proxy available
		int totalThreads=proxyAvailable*threadBonus;
		//int totalThreads=150;
		
		int min=2015000000;
		int max=2016000000;
		double rate=0.9;
		
		System.out.println("There are "+proxyAvailable+" proxy(s) available.");
		System.out.println("For each proxy(s), "+threadBonus+" thread(s) ("+totalThreads+" thread(s) totally) will be used to parse dotamax.com.");
		System.out.println("Minimum Match ID="+min+" ,Maximum Match ID="+max+" ,Sample Rate="+rate);
		System.out.println("-------------------------------------------------------------------------------------------");
		
		ParseThread[] pThreads=new ParseThread[totalThreads+1];
		for(int i=0;i<totalThreads;i++)
		{
			int threadMin=min+i*(max-min)/totalThreads;
			int threadMax=threadMin+(max-min)/totalThreads-1;
			
			pThreads[i]=new ParseThread();
			pThreads[i].setThreadID(i);
			pThreads[i].setMin(threadMin);
			pThreads[i].setMax(threadMax);
			pThreads[i].setRate(rate);
			pThreads[i].setProxy(proxyList.get(i/threadBonus));
			//pThreads[i].setTtl(60000);
			
			new Thread(pThreads[i]).start();
			
			//System.out.println("Thread ID="+i+",Minimum ID is "+threadMin+" ,Maximum ID is "+threadMax);
		}
		
		
	}

}
