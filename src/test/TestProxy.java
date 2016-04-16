package test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import parser.Proxy;

public class TestProxy {
	/**
	 * @param proxy
	 * @throws IOException
	 */
	public static void test(Proxy proxy) throws IOException
	{
		//Set Proxy
		System.setProperty("http.proxySet", "true"); 
		System.setProperty("http.proxyHost", proxy.getIP()); 
		System.setProperty("http.proxyPort", proxy.getPort());
		
		String url="http://1111.ip138.com/ic.asp";
		String useragent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QIHU 360EE";
		Document doc = Jsoup.connect(url).userAgent(useragent).get();
		String ip138FeedBack=doc.text();
		System.out.println(ip138FeedBack);
	}
}
