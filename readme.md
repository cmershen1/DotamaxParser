
	
1.	dotamax.sqlΪ��ṹ�Ľ���ű����������ݿ�����������ȡ���밴ID��Χ���������������
	������Ϣ�������£�
	  `RecordID` 	��ÿһ����¼����һ��Ψһ��ID���������ݿ������
	  `MatchID` 	����ID
	  `PlayerID` 	��ҵ�����ID
	  `Duration` 	����ʱ������λ��min��
	  `Area` 		�������ص�
	  `FBTime` 		һѪʱ�䣨��λ��s��
	  `Skill` 		�����ȼ���1-Normal 2-High 3-VeryHigh 4-ְҵ��
	  `GameMode` 	����ģʽ��ֻ��¼����ͨ�����ݣ��ӳ�ģʽ��
	  `HeroName` 	Ӣ������
	  `HeroLevel` 	��������ʱ�ȼ�
	  `Kill` 		ɱ��
	  `Death` 		����
	  `Assist` 		����
	  `KDA` 		kda
	  `BattlePercentage` 	��ս��
	  `DamagePercentage` 	�˺���
	  `Lasthit` 	����
	  `Denied` 		����
	  `GPM` 		��Ǯ/min
	  `XPM` 		����/min
	  `TDamage`		�����˺�
	  `Heal` 		����
	  `Item0` 		װ������ͬ
	  `Item1` 		
	  `Item2` 	
	  `Item3` 	
	  `Item4` 	
	  `Item5`	
	  `Lasthit/min` ����/min
	  `Denied/min` 	����/min
	  `Damage/min` 	�˺�/min
	  `TDamage/min` �����˺�/min
	  `Heal/min` 	����/min
	  `WinOrLose` 	ʤ����1Ϊʤ��0Ϊʧ��
	  `IsStar` 		�Ƿ�Ϊ����ѡ�֣�Dotamax��һЩְҵѡ�� �����ȵ�ID���б�ǣ����Դ���ҳ�п�����ѡ���Ƿ�Ϊ����ѡ�֣���1��0��
	  `IsMVP` 		�Ƿ�ΪMVP��1��0��
	  
2.	����Դ�����л�����MySQL 5.5, jdk 1.7
	����˵����
		parser.GetProxy����ȡ����Ĺ����࣬��������������
			public static ArrayList<Proxy> getUsefulProxiesFromXici(int pages) ����xicidaili��ҳ�ϻ�ȡ���õ�http��������pages��ʾ��ȡǰ��ҳ��
			public static ArrayList<Proxy> getUsefulProxiesFromKuaidaili(int pages)����kuaidaili��ҳ�ϻ�ȡ���õ�http����
			public static ArrayList<Proxy> getUsefulProxiesFromFile() throws IOException���ӹ��̰��е�proxy.txt�ж�ȡ���õĴ�������proxy.txt��ô���ɣ��Ͳ��Ǳ����̵������ˡ�
		parser.GetRandom����ȡ��������ϣ��������ɲ���ID������Ķ���
			public static void randomSet(int min, int max, int n, HashSet<Integer> set)����ȡ��min��max��n����������洢�ڼ���set���档
		parser.MainClass�������������ࡣ
			ע�⣬ʵ����ȡ��ʱ����һЩ�ɵ�������
				��14��-��16�п��Ե�����ȡ����ķ�ʽ��
				��18�� threadBonus����ָ����ÿ�����õĴ��������ٸ��̣߳��̸߳���*����������MySQL�����������������޸������������ָ�
				��21-23�� �ֱ�ָ����ȡID����Сֵ ���ֵ ������
		parse.Parse����ȡ�߼��ࡣ
			ע�⣺��38-42��ָ���Ƿ����������ע�͵��򲻿��������ٶȻ�죬��dotamax���ص����IP�������÷��sb�ˡ���
		parse.ParseThread�������߳��ࡣ
			ע�⣺21��ָ�������ĸ�����δ��Ƴɸ�����������ID��Χ�Զ����ɱ��������ԡ���������ID��Χ��MySQL�ı����ͱ���ָ���ı���Ҫ�˹���һһ��Ӧ���������������Ҫ��~~��
		parse.Proxy��parse.RecordBean�����Ǵ洢���ݵ�JavaBean�ࡣ����Ķ���
		test.testProxy�����ڲ���һ�������Ƿ��ʹ������������룬û���κ��á�

3.	ʹ��˵����
	��1����װjdk �� MySQL�������û���������
	��2��ָ���û���Ϊroot������Ϊ123456�����½����ݿ���Ϊdotamatch����������Զ��壬����ParseThread.java��67-69���޸ġ���
	��3��������ű��½�һ���ձ�
	��4��Ѱ�Һ��ʵ�http����������й���
	��5���������������г���
	

		