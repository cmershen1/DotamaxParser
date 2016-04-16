package parser;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import parser.Proxy;
public class GetProxy {
	
	public final static String standardTitle="首页 -  Dotamax - 做国内第一游戏数据门户";
	@SuppressWarnings("unused")
	public static ArrayList<Proxy> getUsefulProxiesFromXici(int pages) 
	{		
		ArrayList<Proxy> proxyList=new ArrayList<Proxy>();
		Proxy proxy=new Proxy();
		String useragent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QIHU 360EE";
		for(int j=1;j<=pages;j++)
		{
			String proxyUrl="http://www.xicidaili.com/nn/"+j;
			System.out.println("Finding available proxies from "+proxyUrl+" ...");
			Document doc;
			try {
				doc = Jsoup.connect(proxyUrl).userAgent(useragent).timeout(2000).get();
				Elements proxyInfo = doc.select("tr.odd");
				for(int i=0;i<proxyInfo.size();i++)
				{
					String proxyIP = proxyInfo.get(i).getElementsByTag("td").get(2).text();
					String proxyPort = proxyInfo.get(i).getElementsByTag("td").get(3).text();
					//if(proxyPing < 1.0 && proxyConnTime < 1.0)
					if(true)
					{
						//System.out.println(proxyIP+","+proxyPort);
						System.setProperty("http.proxySet", "true"); 
						System.setProperty("http.proxyHost", proxyIP); 
						System.setProperty("http.proxyPort", proxyPort);
						try{
							long startTime=System.currentTimeMillis();
							Document test=Jsoup.connect("http://dotamax.com/match/detail/1934929070").timeout(1000).get();
							//Jsoup.connect("http://dotamax.com/match/detail/1934929070").timeout(1000).getClass();
							long endTime=System.currentTimeMillis();
							long ping=endTime-startTime;
							System.out.println(proxyIP+","+proxyPort+" is useful.Ping = "+ping+" ms.");
							//if(test.hasAttr("title"))
							proxyList.add(new Proxy(proxyIP,proxyPort));
						}
						catch(SocketException | SocketTimeoutException e)
						{
							System.out.println(proxyIP+","+proxyPort+" is useless.");
						}
						
					}			
				}
				//System.out.println(proxyList.size());
				int random=(int)(Math.random()*(proxyList.size()-1));
				proxy=proxyList.get(random);
				//System.out.println(proxy.getIP()+","+proxy.getPort());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return proxyList;
	}
	@SuppressWarnings("unused")
	public static ArrayList<Proxy> getUsefulProxiesFromKuaidaili(int pages)
	{
		ArrayList<Proxy> proxyList=new ArrayList<Proxy>();
		Proxy proxy=new Proxy();
		String useragent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QIHU 360EE";
		for(int j=1;j<=pages;j++)
		{
			//String proxyUrl="http://www.kuaidaili.com/free/inha/"+j;
			String proxyUrl="http://www.kuaidaili.com/proxylist/"+j;
			System.out.println("Finding available proxies from "+proxyUrl+" ...");
			Document doc;
			try {
				doc = Jsoup.connect(proxyUrl).userAgent(useragent).timeout(1000).get();
				Elements proxyInfo = doc.select("tr");
				
				
				for(int i=1;i<proxyInfo.size();i++)
				{				
					String proxyIP = proxyInfo.get(i).getElementsByTag("td").get(0).text();
					String proxyPort = proxyInfo.get(i).getElementsByTag("td").get(1).text();
					String anonymous = proxyInfo.get(i).getElementsByTag("td").get(2).text();
					//double proxyPing = Double.parseDouble(proxyInfo.get(i).getElementsByTag("td").get(7).getElementsByTag("div").get(0).attr("title").replace("秒", ""));
					//double proxyConnTime = Double.parseDouble(proxyInfo.get(i).getElementsByTag("td").get(8).getElementsByTag("div").get(0).attr("title").replace("秒", ""));
					//if(proxyPing < 1.0 && proxyConnTime < 1.0)
					if(anonymous.equals("高匿名"))
					{
						//System.out.println(proxyIP+","+proxyPort);
						System.setProperty("http.proxySet", "true"); 
						System.setProperty("http.proxyHost", proxyIP); 
						System.setProperty("http.proxyPort", proxyPort);
						try{
							long startTime=System.currentTimeMillis();
							Document test=Jsoup.connect("http://dotamax.com/match/detail/1934929070").timeout(1000).get();
							long endTime=System.currentTimeMillis();
							long ping=endTime-startTime;
							System.out.println(proxyIP+","+proxyPort+" is useful.Ping = "+ping+" ms.");
							//if(test.hasAttr("title"))
							proxyList.add(new Proxy(proxyIP,proxyPort));
						}
						catch(SocketException | SocketTimeoutException | HttpStatusException e)
						{
							System.out.println(proxyIP+","+proxyPort+" is useless.");
						}
						
					}		
					
				}
				//System.out.println(proxyList.size());
				int random=(int)(Math.random()*(proxyList.size()-1));
				proxy=proxyList.get(random);
				//System.out.println(proxy.getIP()+","+proxy.getPort());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return proxyList;
	}
	@SuppressWarnings("resource")
	public static ArrayList<Proxy> getUsefulProxiesFromFile()
	{
		ArrayList<Proxy> proxyList=new ArrayList<Proxy>();
		
		FileReader fr;
		try {
			fr = new FileReader("Proxy.txt");
			BufferedReader br=new BufferedReader(fr);
			String lines=null;
			try {
				while((lines=br.readLine())!=null)
				{
					String IP=lines.split(":")[0];
					String port=lines.split(":")[1];
					int ping=Integer.parseInt(lines.split(":")[2].replace("ms", ""));
					if(ping<500)
					{
						proxyList.add(new Proxy(IP,port));
					}
				}
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return proxyList;
		
	}
}
