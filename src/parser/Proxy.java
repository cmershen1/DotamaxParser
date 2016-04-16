package parser;

public class Proxy {
	private String IP;
	private String port;
	public Proxy()
	{
		
	}
	public Proxy(String IP,String port)
	{
		this.IP=IP;
		this.port=port;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String toString()
	{
		return "IP="+this.IP+",Port="+this.port;
	}
}
