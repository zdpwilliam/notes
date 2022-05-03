package com.william.algorithm.concrete_practice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class LotteryExample {

	/**
	 * A: һ���㷨������һ���б��ֳɼ������䣬�����б���175��0-5��ѥ�ӵ����䣬6-15�����������ȣ�Ȼ�������175ȡ��һ�������������ĸ����䡣
	 * �㷨ʱ�临�Ӷȣ�Ԥ����O(MN)�����������O(1)���ռ临�Ӷ�O(MN)������N������Ʒ���࣬M������͸��ʾ�����
	 * 	����ͬ�� D����Ȩ����㷨��
	 */
	public static int getSimpleLottery() {
		final Integer[] lotteryRanges = {5, 10, 20, 40, 100};
		Random random = new Random();
		int lottery = random.nextInt(getSum(lotteryRanges));
		if(lottery < 5) {
			return 1;
		}
		else if(lottery >= 5 && lottery < 15) {
			return 2;
		}
		else if(lottery >= 15 && lottery < 35) {
			return 3;
		}
		else if(lottery >= 35 && lottery < 75) {
			return 4;
		}
		else {
			return 0;
		}
	}
	
	/**
	 * java.util.Random��next()������������seed����һ������������seed��ͬ������ͬһ�������
	 *
	 * Random rand = new Random(47); Random rand2 = new Random(47); int i0 =
	 * rand.nextInt(100); int j0 = rand.nextInt(100); System.out.print(i0+".");
	 * System.out.println(j0+"."); int i1 = rand2.nextInt(100); int j1 =
	 * rand2.nextInt(100); System.out.print(i1+"."); System.out.println(j1+".");
	 */

	/**
	 * java.lang.Math.Random��Math.Random()�����ܹ����ش����ŵ�doubleֵ�� ȡֵ��Χ��[0.0,
	 * 1.0)������ҿ����䣬����ֵ��һ��α���ѡ��������ڸ÷�Χ�ڣ����ƣ����ȷֲ�
	 * 
	 * for (int i = 0; i < 10; i++) { int random = (int) (Math.random() * 3);
	 * System.out.println(random); }
	 */

	/**
	 * B: ��ɢ�㷨�����ȡһ����R����˳������Щ����бȽϣ�֪���ҵ���һ����R��������±꣬��һ���㷨����ռ�ÿռ䣬�����Բ��ö��ַ��ҳ�R
	 * Ԥ����O(N)�����������O(logN)���ռ临�Ӷ�O(N)
	 * lotteryList �ֱ�Ϊһ�����������ĵȽ��Ľ�Ʒ���������һ��Ϊδ�н���������
	 */
	public static int getLotteryLevel() {
		final Integer[] lotteryList = {5, 10, 20, 40, 100};
		Random random = new Random(System.nanoTime());
		int sum = getSum(lotteryList);
		for (int i = 0; i < lotteryList.length; ++i) {
			int randNum = Math.abs(random.nextInt()) % sum;
			if (randNum <= lotteryList[i]) {
				return i;
			} else { 	
				sum -= lotteryList[i];
			}
		}
		return -1;
	}

	private static int getSum(Integer[] lotteryList) {
		int sum = 0;
		for (int v : lotteryList) {
			sum += v;
		}
		return sum;
	}
	
	/**
	 * C: ��һ�ֳ齱�㷨�����ڹ�˾�齱�����ܲ��������뽱Ʒ���̶���
	 */
	private static final int lotteryNum = 75;
	private static final int total = 175;
	private static Set<Integer> lotterySet = new HashSet<Integer>();

	static {
		for (int i = 1; i <= lotteryNum; ++i) {
			lotterySet.add(total * i / lotteryNum);
		}
	}

	public static int getLotteryNum() {
		Random rand = new Random(System.nanoTime());
		int randNum = Math.abs(rand.nextInt()) % total;
		if (lotterySet.contains(randNum)) {
			return randNum * lotteryNum / total;
		}
		return -1;
	}

	public static void main(String[] args) {
		int totalCount = 10000;
		Map<Integer, Integer> prizeCountMap = new TreeMap<Integer, Integer>();
		for (int i = 0; i < totalCount; i++) {
			Integer result = Integer.valueOf(getLotteryNum());
			if(prizeCountMap.containsKey(result)) {
				prizeCountMap.put(result, prizeCountMap.get(result) + 1);
			}
			else {
				prizeCountMap.put(result, 1);
			}
		}
		Iterator<Entry<Integer, Integer>> iterator = prizeCountMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Integer> prizeCount = iterator.next();
			System.out.println("prize " + prizeCount.getKey() + " count: " + prizeCount.getValue()
					+ " percent: " + divide(prizeCount.getValue(), totalCount) + " %");
		}
		System.out.println("total lottery count: " + totalCount);
	}

	public static double divide(int dividend, int divisor) {
		BigDecimal dividendDecimal = new BigDecimal(dividend).multiply(new BigDecimal(100));
		return dividendDecimal.divide(new BigDecimal(divisor), 2, RoundingMode.HALF_UP).doubleValue();
	}
	
	/**
	 * D����Ȩ����㷨һ�����ڣ��齱����Դ���ȵȳ���(׼ȷ)�� ��ͬ�� A: һ���㷨
	 */
	static class WeightRandom {
		static List<WeightCategory> categorys = new ArrayList<WeightCategory>();
		private static Random random = new Random();

		private static void initData() {
			WeightCategory wc1 = new WeightCategory("1", 5);
			WeightCategory wc2 = new WeightCategory("2", 10);
			WeightCategory wc3 = new WeightCategory("3", 20);
			WeightCategory wc4 = new WeightCategory("4", 40);
			WeightCategory wc0 = new WeightCategory("nothing", 100);
			categorys.add(wc1);
			categorys.add(wc2);
			categorys.add(wc3);
			categorys.add(wc4);
			categorys.add(wc0);
		}

		public static void main(String[] args) {
			int totalCount = 10000;
			Map<String, Integer> prizeCountMap = new TreeMap<String, Integer>();
			for (int i = 0; i < totalCount; i++) {
				String result = getPrize();
				if(prizeCountMap.containsKey(result)) {
					prizeCountMap.put(result, prizeCountMap.get(result) + 1);
				}
				else {
					prizeCountMap.put(result, 1);
				}
			}
			Iterator<Entry<String, Integer>> iterator = prizeCountMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Integer> prizeCount = iterator.next();
				System.out.println("prize " + prizeCount.getKey() + " count: " + prizeCount.getValue()
						+ " percent: " + divide(prizeCount.getValue(), totalCount) + " %");
			}
			System.out.println("total lottery count: " + totalCount);
		}
		
		private static String getPrize() {
			initData();
			
			Integer weightSum = 0;
			for (WeightCategory wc : categorys) {
				weightSum += wc.getWeight();
			}

			if (weightSum <= 0) {
				throw new RuntimeException("Error: weightSum=" + weightSum.toString());
			}
			
			Integer n = random.nextInt(weightSum); // n in [0, weightSum)
			Integer m = 0;
			for (WeightCategory wc : categorys) {
				if (m <= n && n < m + wc.getWeight()) {
					return wc.getCategory();
				}
				m += wc.getWeight();
			}
			return "nothing";
		}

		static class WeightCategory {
			private String category;
			private Integer weight;

			public WeightCategory() {
				super();
			}

			public WeightCategory(String category, Integer weight) {
				super();
				this.setCategory(category);
				this.setWeight(weight);
			}

			public Integer getWeight() {
				return weight;
			}

			public void setWeight(Integer weight) {
				this.weight = weight;
			}

			public String getCategory() {
				return category;
			}

			public void setCategory(String category) {
				this.category = category;
			}
		}
	}
	
	/**
	 * E��Alias Method �������ӣ�http://www.keithschwarz.com/darts-dice-coins/ 
	 * ������˼����N�ֿ�����ƴװ��һ�����Σ����壩���ֳ�N�У�ÿ�и߶�Ϊ1��������ֿ����ԣ������Գ���Ϊĳ����ɫ��
	 * ��ÿ�������������ɫ���ҵ�n���б��е�n�ֿ����ԣ����ｫ��n�ֿ����Գ�Ϊԭɫ�� �����׳�һ��Ӳ�ң�����������һ�У��������������ϵ�һ����ɫ��
	 * �����͵õ��������飺һ����¼����ԭɫ�ĸ����Ƕ��٣���ΪProb���飬��һ����¼���Ϸ�ԭɫ����ɫ���ƣ���ΪAlias���飬������ֻ��ԭɫ���Ϊnull��
	 */
	
	
	
	/**
	 * @TODO to think twice ???
	 * F���齱�㷨�Ľ� 
	 * ��ν���ʳ齱���������뵽�ĳ齱�㷨�ˣ�������ʿ�����һ�ɲ���ģ�Ҳ������һֱ�ڱ仯�����ģ����ѵ��ǲ��ö��ĸ��ʣ�
	 * ��������²��ú��ָ��ʡ����Ҳû��ʲôͨ�õķ�������ͬ��Ӧ�ó��������õĸ����㷨��ͬ��
	 * �������һ���㷨�����ݽ�Ʒ�Ĺ�����������������ǰʱ����н��ʣ���ʱ���𽥽ӽ���Ʒ����ʱ��ʱ��
	 * �н����ʻ��𽥷����仯�������Ϊ��1��ʾ����˥����2Ϊƽ��˥�����Դ����ơ�
	 */
	static class LotteryTool {
		private double factor;
		private double probability;
		private Random rand;

		private LotteryTool(double probability, long expireTime, int reduce) {
			this.factor = (double) System.currentTimeMillis() / expireTime;
			this.probability = probability * Math.pow(factor, reduce);
			this.rand = new Random(System.currentTimeMillis());
		}

		public static LotteryTool getInstance(double probability,
				long expireTime, int reduce) {
			return new LotteryTool(probability, expireTime, reduce);
		}

		public static boolean getLottory(double probability, Date endtime) {
			Random r = new Random(System.currentTimeMillis());
			LotteryTool tool = LotteryTool.getInstance(probability,
					endtime.getTime(), 1);
			return tool.isLucky(r.nextLong());
		}

		public boolean isLucky(long expected) {
			long token = generateLong();
			expected = expected % (int) (1 / probability);
			if (expected == token) {
				return true;
			}
			return false;
		}

		private long generateLong() {
			return rand.nextLong() % Double.valueOf(1 / probability).intValue();
		}
		
		public static void main(String[] args) {
			double probility = 0.20d;
			Date deadline = null;
			try {
				deadline = getSpecifiedDate("2016-11-20 23:59:59");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			int totalCount = 10000;
			Map<Boolean, Integer> prizeCountMap = new TreeMap<Boolean, Integer>();
			for (int i = 0; i < totalCount; i++) {
				Boolean result = Boolean.valueOf(getLottory(probility, deadline));
				if(prizeCountMap.containsKey(result)) {
					prizeCountMap.put(result, prizeCountMap.get(result) + 1);
				}
				else {
					prizeCountMap.put(result, 1);
				}
			}
			Iterator<Entry<Boolean, Integer>> iterator = prizeCountMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Boolean, Integer> prizeCount = iterator.next();
				System.out.println("prize " + prizeCount.getKey() + " count: " + prizeCount.getValue()
						+ " percent: " + divide(prizeCount.getValue(), totalCount) + " %");
			}
			System.out.println("total lottery count: " + totalCount);
		}

		private static Date getSpecifiedDate(String dateStr) throws ParseException {
			DateFormat dft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			return dft.parse(dateStr);
		}
	}
}
