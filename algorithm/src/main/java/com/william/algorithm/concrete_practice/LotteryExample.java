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
	 * A: 一般算法：生成一个列表，分成几个区间，例如列表长度175，0-5是靴子的区间，6-15是披风的区间等，然后随机从175取出一个数，看落在哪个区间。
	 * 算法时间复杂度：预处理O(MN)，随机数生成O(1)，空间复杂度O(MN)，其中N代表物品种类，M则由最低概率决定。
	 * 	“等同于 D、加权随机算法”
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
	 * java.util.Random的next()函数，依赖于seed生成一个随机数，如果seed相同则生成同一随机数列
	 *
	 * Random rand = new Random(47); Random rand2 = new Random(47); int i0 =
	 * rand.nextInt(100); int j0 = rand.nextInt(100); System.out.print(i0+".");
	 * System.out.println(j0+"."); int i1 = rand2.nextInt(100); int j1 =
	 * rand2.nextInt(100); System.out.print(i1+"."); System.out.println(j1+".");
	 */

	/**
	 * java.lang.Math.Random的Math.Random()函数能够返回带正号的double值， 取值范围是[0.0,
	 * 1.0)的左闭右开区间，返回值是一个伪随机选择的数，在该范围内（近似）均匀分布
	 * 
	 * for (int i = 0; i < 10; i++) { int random = (int) (Math.random() * 3);
	 * System.out.println(random); }
	 */

	/**
	 * B: 离散算法：随机取一个数R，按顺序在这些点进行比较，知道找到第一个比R大的数的下标，比一般算法减少占用空间，还可以采用二分法找出R
	 * 预处理O(N)，随机数生成O(logN)，空间复杂度O(N)
	 * lotteryList 分别为一、二、三、四等将的奖品数量，最后一个为未中奖的数量。
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
	 * C: 另一种抽奖算法，用于公司抽奖，即总参与人数与奖品数固定。
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
	 * D、加权随机算法一般用于：抽奖，资源调度等场景(准确)。 等同于 A: 一般算法
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
	 * E、Alias Method 完整例子：http://www.keithschwarz.com/darts-dice-coins/ 
	 * 大致意思：把N种可能性拼装成一个方形（整体），分成N列，每列高度为1且最多两种可能性，可能性抽象为某种颜色，
	 * 即每列最多有两种颜色，且第n列中必有第n种可能性，这里将第n种可能性称为原色。 想象抛出一个硬币，会落在其中一列，并且是落在列上的一种颜色。
	 * 这样就得到两个数组：一个记录落在原色的概率是多少，记为Prob数组，另一个记录列上非原色的颜色名称，记为Alias数组，若该列只有原色则记为null。
	 */
	
	
	
	/**
	 * @TODO to think twice ???
	 * F、抽奖算法改进 
	 * 所谓概率抽奖是最容易想到的抽奖算法了，这个概率可以是一成不变的，也可以是一直在变化调整的，最难的是采用多大的概率，
	 * 何种情况下采用何种概率。这个也没有什么通用的方案，不同的应用场景，所用的概率算法不同。
	 * 下面介绍一种算法，根据奖品的过期日期来计算它当前时间的中奖率，当时间逐渐接近奖品过期时间时，
	 * 中奖概率会逐渐发生变化，如果设为：1表示线性衰减，2为平方衰减，以此类推。
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
