package parser;

public class MatchBean {
	private int MatchID;
	
	private String Radiant1;
	private String Radiant2;
	private String Radiant3;
	private String Radiant4;
	private String Radiant5;

	private String Dyre1;
	private String Dyre2;
	private String Dyre3;
	private String Dyre4;
	private String Dyre5;
	
	private int winner;
	public void addInfo(int side,int i,String name)
	{
		if(side==Parse.RADIANT)
		{
			switch (i)
			{
				case 0:this.Radiant1=name;break;
				case 1:this.Radiant2=name;break;
				case 2:this.Radiant3=name;break;
				case 3:this.Radiant4=name;break;
				case 4:this.Radiant5=name;break;
				default:break;
			}
		}
		else
		{
			switch (i)
			{
				case 0:this.Dyre1=name;break;
				case 1:this.Dyre2=name;break;
				case 2:this.Dyre3=name;break;
				case 3:this.Dyre4=name;break;
				case 4:this.Dyre5=name;break;
				default:break;
			}
		}
	}
	public int getMatchID() {
		return MatchID;
	}

	public void setMatchID(int matchID) {
		MatchID = matchID;
	}

	public String getRadiant1() {
		return Radiant1;
	}

	public void setRadiant1(String radiant1) {
		Radiant1 = radiant1;
	}

	public String getRadiant2() {
		return Radiant2;
	}

	public void setRadiant2(String radiant2) {
		Radiant2 = radiant2;
	}

	public String getRadiant3() {
		return Radiant3;
	}

	public void setRadiant3(String radiant3) {
		Radiant3 = radiant3;
	}

	public String getRadiant4() {
		return Radiant4;
	}

	public void setRadiant4(String radiant4) {
		Radiant4 = radiant4;
	}

	public String getRadiant5() {
		return Radiant5;
	}

	public void setRadiant5(String radiant5) {
		Radiant5 = radiant5;
	}

	public String getDyre1() {
		return Dyre1;
	}

	public void setDyre1(String dyre1) {
		Dyre1 = dyre1;
	}

	public String getDyre2() {
		return Dyre2;
	}

	public void setDyre2(String dyre2) {
		Dyre2 = dyre2;
	}

	public String getDyre3() {
		return Dyre3;
	}

	public void setDyre3(String dyre3) {
		Dyre3 = dyre3;
	}

	public String getDyre4() {
		return Dyre4;
	}

	public void setDyre4(String dyre4) {
		Dyre4 = dyre4;
	}

	public String getDyre5() {
		return Dyre5;
	}

	public void setDyre5(String dyre5) {
		Dyre5 = dyre5;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}


	
}
