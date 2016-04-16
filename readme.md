
	
1.	dotamax.sql为表结构的建库脚本，导入数据库后，若想继续爬取，请按ID范围的命名规则改名。
	各列信息介绍如下：
	  `RecordID` 	对每一条记录建立一个唯一的ID，即本数据库的主键
	  `MatchID` 	比赛ID
	  `PlayerID` 	玩家的数字ID
	  `Duration` 	比赛时长（单位：min）
	  `Area` 		服务器地点
	  `FBTime` 		一血时间（单位：s）
	  `Skill` 		比赛等级（1-Normal 2-High 3-VeryHigh 4-职业）
	  `GameMode` 	比赛模式（只收录了普通，天梯，队长模式）
	  `HeroName` 	英雄名称
	  `HeroLevel` 	比赛结束时等级
	  `Kill` 		杀敌
	  `Death` 		死亡
	  `Assist` 		助攻
	  `KDA` 		kda
	  `BattlePercentage` 	参战率
	  `DamagePercentage` 	伤害率
	  `Lasthit` 	正补
	  `Denied` 		反补
	  `GPM` 		金钱/min
	  `XPM` 		经验/min
	  `TDamage`		建筑伤害
	  `Heal` 		治疗
	  `Item0` 		装备，下同
	  `Item1` 		
	  `Item2` 	
	  `Item3` 	
	  `Item4` 	
	  `Item5`	
	  `Lasthit/min` 正补/min
	  `Denied/min` 	反补/min
	  `Damage/min` 	伤害/min
	  `TDamage/min` 建筑伤害/min
	  `Heal/min` 	治疗/min
	  `WinOrLose` 	胜负，1为胜利0为失败
	  `IsStar` 		是否为明星选手（Dotamax对一些职业选手 主播等的ID进行标记，可以从网页中看到该选手是否为明星选手），1是0否
	  `IsMVP` 		是否为MVP，1是0否
	  
2.	爬虫源码运行环境：MySQL 5.5, jdk 1.7
	各类说明：
		parser.GetProxy：获取代理的工厂类，包含三个方法：
			public static ArrayList<Proxy> getUsefulProxiesFromXici(int pages) ：从xicidaili网页上获取可用的http代理，参数pages表示获取前几页。
			public static ArrayList<Proxy> getUsefulProxiesFromKuaidaili(int pages)：从kuaidaili网页上获取可用的http代理。
			public static ArrayList<Proxy> getUsefulProxiesFromFile() throws IOException：从工程包中的proxy.txt中读取可用的代理。至于proxy.txt怎么生成，就不是本工程的事情了。
		parser.GetRandom：获取随机数集合，用于生成采样ID。不需改动。
			public static void randomSet(int min, int max, int n, HashSet<Integer> set)：获取从min到max的n个随机数，存储在集合set里面。
		parser.MainClass；主函数所在类。
			注意，实际爬取的时候，有一些可调参数：
				第14行-第16行可以调整获取代理的方式；
				第18行 threadBonus变量指定对每个可用的代理开启多少个线程，线程个数*倍率若大于MySQL最大连接数，请查找修改最大连接数的指令。
				第21-23行 分别指定爬取ID的最小值 最大值 采样率
		parse.Parse：爬取逻辑类。
			注意：第38-42行指定是否开启代理，如果注释掉则不开启代理（速度会快，但dotamax会监控到你的IP，被永久封就sb了。）
		parse.ParseThread：爬虫线程类。
			注意：21行指定插入哪个表，暂未设计成根据主函数的ID范围自动生成表名。所以【主函数的ID范围，MySQL的表名和本行指定的表名要人工的一一对应。】（这个是最重要的~~）
		parse.Proxy，parse.RecordBean：都是存储数据的JavaBean类。不需改动。
		test.testProxy：用于测试一个代理是否好使。属于冗余代码，没有任何用。

3.	使用说明：
	（1）安装jdk 和 MySQL，并配置环境变量。
	（2）指定用户名为root，密码为123456，并新建数据库名为dotamatch。（如果想自定义，请在ParseThread.java的67-69行修改。）
	（3）按建库脚本新建一个空表。
	（4）寻找合适的http代理，最好在中国。
	（5）调整参数，运行程序。
	

		