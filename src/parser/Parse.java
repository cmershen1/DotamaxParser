package parser;


import java.io.IOException;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parse {
	public static final int RADIANT=1;
	public static final int DYRE=2;
	@SuppressWarnings("unused")
	private Connection conn;
	
	private PreparedStatement preStmt;
	private PreparedStatement preStmt2;
	private Proxy proxy;
	public boolean writtenFlag;
	public int threadID;
	public Parse(Connection connection,Proxy proxy, int threadID,PreparedStatement preStmt,PreparedStatement preStmt2) throws SQLException 
	{
		this.conn=connection;
		this.proxy=proxy;
		this.writtenFlag=false;
		this.threadID=threadID;
		this.preStmt=preStmt;
		this.preStmt2=preStmt2;
	}
	public void parseMatch(String url) throws IOException, SQLException
	{
		
//		System.setProperty("http.proxySet", "true"); 
//		System.setProperty("http.proxyHost", proxy.getIP()); 
//		System.setProperty("http.proxyPort", proxy.getPort());	
		
		String useragent=UUID.randomUUID().toString();
		try
		{
			Document doc = Jsoup.connect(url).userAgent(useragent).timeout(100000).get();
			Elements tds=doc.select("tr[style=font-size: 13px;font-weight: 400;]");
			
			//long endTime=System.currentTimeMillis();
			//long costTime1=endTime-startTime;
			//fw.write(df.format(new Date())+"   HTML Text get completely. Cost "+costTime1+" ms.\r\n");
			
			//startTime=System.currentTimeMillis();
			//fw.write(df.format(new Date())+"   Start Analysing URL "+url+" ...\r\n");
			
			if(tds.size() ==1)//The match exists
			{
				Elements basicInfo=tds.get(0).getElementsByTag("td");
				
				String matchID=basicInfo.get(0).text();
				String duration=basicInfo.get(2).text().replace("分钟", "");
				String area=basicInfo.get(3).text();
				String[] fbTimeTemp=basicInfo.get(4).text().split(":");
				int fbTime=Integer.parseInt(fbTimeTemp[0])*3600 + Integer.parseInt(fbTimeTemp[1])*60+Integer.parseInt(fbTimeTemp[2]);
				String skill=basicInfo.get(5).text();
				int skillCode=0;
				switch(skill)
				{
					case "Normal":skillCode=1;break;
					case "High":skillCode=2;break;
					case "Very High":skillCode=3;break;
					case "职业":skillCode=4;break;
				}
				String gameMode=basicInfo.get(6).text();
				boolean continueParse=false;
				if(gameMode.equals("天梯匹配")||gameMode.equals("全阵营")||gameMode.equals("队长模式")||gameMode.equals("随机征召"))
				{
					continueParse=true;
				}
				if(continueParse)
				{
					Elements matchInfo=doc.select("div[style=margin-top: 20px;width:100%;margin-left: auto;margin-right:auto;]");
					String radiant=matchInfo.get(0).select("p.radiant").text();
					int winner=0;
					if(radiant.equals("天辉 (获胜)"))
						winner=RADIANT;
					else
						winner=DYRE;
					
					RecordBean recordBean=new RecordBean();
					
					recordBean.setMatchID(Integer.parseInt(matchID));
					recordBean.setDuration(Integer.parseInt(duration));
					recordBean.setArea(area);
					recordBean.setFbTime(fbTime);
					recordBean.setSkill(skillCode);
					recordBean.setGameMode(gameMode);
					
					MatchBean matchBean=new MatchBean();
					
					matchBean.setMatchID(Integer.parseInt(matchID));
					
					Elements radiantInfo=matchInfo.get(0).select("table[class=table table-hover table-striped table-shadow table-match-detail table-match-detail-ra]")
							.get(0).select("tr[style=height: 53px;]");
					AnalysisInfo(radiantInfo,RADIANT,winner,recordBean,matchBean);
					
					Elements dyreInfo=matchInfo.get(0).select("table[class=table table-hover table-striped table-shadow table-match-detail table-match-detail-dire]")
							.get(0).select("tr[style=height: 53px;]");
					AnalysisInfo(dyreInfo,DYRE,winner,recordBean,matchBean);
					matchBean.setWinner(winner);
					insertSQL(matchBean);
				}
			}

		}

		catch(SocketException e)
		{
			System.out.println("Connention Reset.Sleeping thread "+threadID+" for 1s,waiting for dotamax server restart...");
			try {
				Thread.sleep(1000);// sleep 1 s,wait for dotamax server restart.
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	private void AnalysisInfo(Elements info,int side,int winner,RecordBean recordBean,MatchBean matchBean) throws SQLException
	{
		for(int i=0;i<5;i++)
		{
			String playerID=info.get(i).getElementsByTag("a").get(0).attr("href");
			Element playerInfo=info.get(i);
			String heroName=playerInfo.getElementsByTag("a").get(2).attr("href").replace("/hero/detail/", "");
			matchBean.addInfo(side,i,heroName);
			
			if(playerID.isEmpty()==false)
			{
				int duration=recordBean.getDuration();
				//Element playerInfo=info.get(i);
				playerID=playerID.replace("/player/detail/", "");
				//String heroName=playerInfo.getElementsByTag("a").get(2).attr("href").replace("/hero/detail/", "");
				String levelAndMVP=playerInfo.select("td[style=position: relative;").text();
				int isMVP=0;
				if(levelAndMVP.endsWith(" MVP"))
					isMVP=1;
				int heroLev=Integer.parseInt(levelAndMVP.replace(" MVP", ""));
				Elements playertds=playerInfo.getElementsByTag("td");
				String[] KDATemp=playertds.get(3).text().split(" ");
				double KDA = Double.parseDouble(KDATemp[0]);
				int kill=Integer.parseInt(KDATemp[1]);
				int death=Integer.parseInt(KDATemp[3]);
				int assist=Integer.parseInt(KDATemp[5]);
				double battlePercentage=Double.parseDouble(playertds.get(4).text().replace("%", ""));
				double damagePercentage=Double.parseDouble(playertds.get(5).text().replace("%", ""));
				int damage=Integer.parseInt(playertds.get(6).text());
				String[] ladTemp=playertds.get(7).text().split("/");
				int lastHit = Integer.parseInt(ladTemp[0]);
				int denied  = Integer.parseInt(ladTemp[1]);
				int gpm     = Integer.parseInt(playertds.get(8).text());
				int xpm     = Integer.parseInt(playertds.get(9).text());
				int tDamage = Integer.parseInt(playertds.get(10).text());
				int heal    = Integer.parseInt(playertds.get(11).text());
				
				
				double lastHitPerMin  = (double)lastHit/(double)duration;
				double deniedPerMin   = (double)denied/(double)duration;
				double damagePerMin   = (double)damage/(double)duration;
				double tDamagePerMin  = (double)tDamage/(double)duration;
				double healPerMin     = (double)heal/(double)duration;
				
				int winOrLose=0;
				if(winner==side)
					winOrLose=1;
				
				String[] items=new String[6];
				Elements itemInfo = playertds.get(12).getElementsByTag("a");
				for(int j=0;j<6;j++)
				{
					if(j<itemInfo.size())
						items[j]=itemInfo.get(j).attr("href").replace("/item/detail/", "");
					else
						items[j]="null";
				}
				Elements star=playerInfo.select("span[class=glyphicon glyphicon-ok-sign]");
				int isStar=star.size();

				String recordID=UUID.randomUUID().toString().replace("-", "");	
				
				//Insert into Database
				recordBean.setRecordID(recordID);
				recordBean.setPlayerID(Integer.parseInt(playerID));
				recordBean.setHeroName(heroName);
				recordBean.setHeroLevel(heroLev);
				recordBean.setKill(kill);
				recordBean.setDeath(death);
				recordBean.setAssist(assist);
				recordBean.setKda(KDA);
				recordBean.setBattlePercentage(battlePercentage);
				recordBean.setDamagePercentage(damagePercentage);
				recordBean.setLasthit(lastHit);
				recordBean.setDenied(denied);
				recordBean.setGpm(gpm);
				recordBean.setXpm(xpm);
				recordBean.settDamage(tDamage);
				recordBean.setHeal(heal);
				recordBean.setItem0(items[0]);
				recordBean.setItem1(items[1]);
				recordBean.setItem2(items[2]);
				recordBean.setItem3(items[3]);
				recordBean.setItem4(items[4]);
				recordBean.setItem5(items[5]);
				recordBean.setLastHitPerMin(lastHitPerMin);
				recordBean.setDeniedPerMin(deniedPerMin);
				recordBean.setDamagePerMin(damagePerMin);
				recordBean.settDamagePerMin(tDamagePerMin);
				recordBean.setHealPerMin(healPerMin);
				recordBean.setWinOrLose(winOrLose);
				recordBean.setIsStar(isStar);
				recordBean.setIsMVP(isMVP);
				insertSQL(recordBean);
				
				matchBean.setWinner(winner);
				
			}
		}
	}
	private void insertSQL(RecordBean recordBean) throws SQLException
	{
		preStmt.setString(1,recordBean.getRecordID());
		preStmt.setInt(2,recordBean.getMatchID());
		preStmt.setInt(3,recordBean.getPlayerID());
		preStmt.setInt(4,recordBean.getDuration());
		preStmt.setString(5,recordBean.getArea());
		preStmt.setInt(6,recordBean.getFbTime());
		preStmt.setInt(7,recordBean.getSkill());
		preStmt.setString(8,recordBean.getGameMode());
		preStmt.setString(9,recordBean.getHeroName());
		preStmt.setInt(10,recordBean.getHeroLevel());
		preStmt.setInt(11,recordBean.getKill());
		preStmt.setInt(12,recordBean.getDeath());
		preStmt.setInt(13,recordBean.getAssist());
		preStmt.setDouble(14,recordBean.getKda());
		preStmt.setDouble(15,recordBean.getBattlePercentage());
		preStmt.setDouble(16,recordBean.getDamagePercentage());
		preStmt.setInt(17,recordBean.getLasthit());
		preStmt.setInt(18,recordBean.getDenied());
		preStmt.setInt(19,recordBean.getGpm());
		preStmt.setInt(20,recordBean.getXpm());
		preStmt.setInt(21,recordBean.gettDamage());
		preStmt.setInt(22,recordBean.getHeal());
		preStmt.setString(23,recordBean.getItem0());
		preStmt.setString(24,recordBean.getItem1());
		preStmt.setString(25,recordBean.getItem2());
		preStmt.setString(26,recordBean.getItem3());
		preStmt.setString(27,recordBean.getItem4());
		preStmt.setString(28,recordBean.getItem5());
		preStmt.setDouble(29,recordBean.getLastHitPerMin());
		preStmt.setDouble(30,recordBean.getDeniedPerMin());
		preStmt.setDouble(31,recordBean.getDamagePerMin());
		preStmt.setDouble(32,recordBean.gettDamagePerMin());
		preStmt.setDouble(33,recordBean.getHealPerMin());
		preStmt.setInt(34,recordBean.getWinOrLose());
		preStmt.setInt(35,recordBean.getIsStar());
		preStmt.setInt(36,recordBean.getIsMVP());

		preStmt.executeUpdate();
		
	}
	private void insertSQL(MatchBean matchBean) throws SQLException
	{
		preStmt2.setInt(1, matchBean.getMatchID());
		preStmt2.setString(2, matchBean.getRadiant1());
		preStmt2.setString(3, matchBean.getRadiant2());
		preStmt2.setString(4, matchBean.getRadiant3());
		preStmt2.setString(5, matchBean.getRadiant4());
		preStmt2.setString(6, matchBean.getRadiant5());
		preStmt2.setString(7, matchBean.getDyre1());
		preStmt2.setString(8, matchBean.getDyre2());
		preStmt2.setString(9, matchBean.getDyre3());		
		preStmt2.setString(10, matchBean.getDyre4());
		preStmt2.setString(11, matchBean.getDyre5());
		preStmt2.setInt(12, matchBean.getWinner());
		
		preStmt2.executeUpdate();
		this.writtenFlag=true;
	}
}

