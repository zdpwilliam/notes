package com.william.algorithm.concrete_practice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 大文本文件排序： 比如100G等
 * 
 * @author zdpwilliam
 *
 */
public class BitTextSort {

	public static void main(String[] args) throws IOException {
		// 生成随机文件
		MergeSort.createDir("F:\\test\\bit-text");
		MergeSort.createFile(MergeSort.INPUT_FILE);
		MergeSort.numSize = MergeSort.createRandomNum(MergeSort.THOUSAND_MILLION);

		MergeSort.sortFile(MergeSort.INPUT_FILE);

		long beginTime = System.currentTimeMillis();
		System.out.println("begin=" + beginTime);

		// 拆分文件
		MergeSort.splitFile(MergeSort.INPUT_FILE, MergeSort.numSize);

		List<String> splitFilePathList = new ArrayList<String>();
		File dir = new File("F:\\test\\bit-text\\temp");
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			splitFilePathList.add(file.getAbsolutePath());
		}
		
		// 合并文件
		MergeSort.createFile(MergeSort.OUTPUT_FILE);
		MergeSort.mergeFile(splitFilePathList, MergeSort.OUTPUT_FILE);

		long endTime = System.currentTimeMillis();
		System.out.println("end=" + endTime);
		System.out.println("end-begin=" + (endTime - beginTime));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		System.out.println(simpleDateFormat.format(endTime - beginTime));

		// 删除拆分文件
		System.gc();
	}

	static class MergeSort {
		/** 十 */
		private static long TEN = 10;
		/** 百 */
		private static long HUNDRED = 100;
		/** 千 */
		private static long THOUSAND = 1000L;
		/** 万 */
		private static long MILLION = 10000L; // 107800:00:01 078
		/** 十万 */
		private static long TEN_MILLION = 100000L; // 965600:00:09 656
		/** 百万 */
		private static long HUNDRED_MILLION = 1000000L; // 9373300:01:33 733
		/** 千万 */
		private static long THOUSAND_MILLION = 10000000L; // 97014400:16:10 144
		/** 亿 */
		private static long BILLION = 100000000L;
		/** 十亿 */
		private static long TEN_BILLION = 1000000000L;
		/** 百亿 */
		private static long HUNDRED_BILLION = 10000000000L;
		/** 千亿 */
		private static long THOUSAND_BILLION = 100000000000L;

		private static String INPUT_FILE = "F:\\test\\bit-text\\input.txt";
		private static String OUTPUT_FILE = "F:\\test\\bit-text\\output.txt";

		/** 拆分大小 */
		private static int SPLIT_SIZE = 10 * 10000;

		private static int numSize;

		private static void sortFile(String path) throws IOException {
			SortedSet<Integer> set = new TreeSet<Integer>();
			File file = new File(path);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String value;
			while ((value = bufferedReader.readLine()) != null) {
				set.add(Integer.parseInt(value));
			}
			bufferedReader.close();
			fileReader.close();
			createFile("F:\\test\\bit-text\\input排序.txt");
			writeFile("F:\\test\\bit-text\\input排序.txt", set, false);
		}

		/**
		 * 拆分文件
		 *
		 * @param inputPath
		 * @param numSize
		 * @throws Exception
		 */
		private static void splitFile(String inputPath, int numSize)
				throws IOException {
			File file = new File(inputPath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			SortedSet<Integer> set = new TreeSet<Integer>();
			String str;
			createDir("F:\\test\\bit-text\\temp");
			if (numSize > SPLIT_SIZE) {
				int count = 1;
				int fileNum = 1;
				while ((str = bufferedReader.readLine()) != null) {
					set.add(Integer.parseInt(str));
					// 超过拆分数，写入子文件
					if (count >= SPLIT_SIZE) {
						createFile("F:\\test\\bit-text\\temp\\" + fileNum + ".txt");
						writeFile("F:\\test\\bit-text\\temp\\" + fileNum + ".txt", set,
								false);
						set.clear();
						count = 0;
						fileNum++;
					}
					count++;// 读取文件当前行数
				}
			}
			// 总量未达到拆分数，写入子文件
			else {
				while ((str = bufferedReader.readLine()) != null) {
					set.add(Integer.parseInt(str));
				}
				createFile("F:\\test\\bit-text\\temp\\1.txt");
				writeFile("F:\\test\\bit-text\\temp\\1.txt", set, false);
			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}

		/**
		 * 合并文件
		 *
		 * <p>
		 * 1.txt（1、3、5、7、9）和2.txt（6、8、9）<br/>
		 * 首先1和6进入treeset。 <br/>
		 * 输出1，发现是来自于1.txt的，再读入3，此时set中的元素是6和3。<br/>
		 * 输出3，发现还是来自于1.txt的，再读入5，此时set中的元素是6和5。 <br/>
		 * 输出5，发现还是来自于1.txt的，再读入7，此时set中的元素是6和7。 <br/>
		 * 输出6，发现来自于2.txt的，读入8，此时set中的元素是8和7。 <br/>
		 * 输出7，发现来自于1.txt的，读入9，此时set中的元素是8和9。 <br/>
		 * 输出8，发现来自于2.txt的，读入9，此时set中的元素是9和9。
		 * </p>
		 *
		 * @param splitFilePathList
		 * @param outputPath
		 * @throws IOException
		 */
		private static void mergeFile(List<String> splitFilePathList,
				String outputPath) throws IOException {
			// fileInfo添加到set
			SortedSet<FileInfo> fileInfoSet = new TreeSet<FileInfo>(
					new FileInfoComparator());
			if (fileInfoSet.isEmpty()) {
				for (int i = 0; i < splitFilePathList.size(); i++) {
					File file = new File(splitFilePathList.get(i));
					FileReader fileReader = new FileReader(file);
					BufferedReader bufferedReader = new BufferedReader(fileReader);

					FileInfo fileInfo = new FileInfo();
					String splitFilePath = splitFilePathList.get(i);
					fileInfo.setFileNum(Integer.parseInt(splitFilePath
							.substring(splitFilePath.lastIndexOf("\\") + 1, splitFilePath.indexOf(".txt"))));// 文件号
					fileInfo.setReader(bufferedReader);// reader引用
					String value = bufferedReader.readLine();
					if (value != null) {
						fileInfo.setValue(value);// 当前值
						fileInfo.setLineNum(fileInfo.getLineNum() + 1);// 当前行号
						fileInfoSet.add(fileInfo);
					}
				}
			}

			Set<Integer> valueSet = new LinkedHashSet<Integer>();
			boolean isSplit = false;
			int count = 1;
			// 输出set元素
			while (!fileInfoSet.isEmpty()) {
				FileInfo currentFileInfo = fileInfoSet.first();
				valueSet.add(Integer.parseInt(currentFileInfo.getValue()));
				// 拆分批量写入文件
				if (valueSet.size() >= SPLIT_SIZE) {
					writeFile(outputPath, valueSet, true);
					valueSet.clear();
					isSplit = true;
				}

				// clone fileInfo
				FileInfo nextFileInfo = new FileInfo();
				nextFileInfo.setFileNum(currentFileInfo.getFileNum());
				nextFileInfo.setLineNum(currentFileInfo.getLineNum());
				nextFileInfo.setValue(currentFileInfo.getValue());
				nextFileInfo.setReader(currentFileInfo.getReader());

				boolean isSuccess = nextFileInfo.readNextValue();

				// 未到文件末尾，set中fileInfo重新排序
				if (isSuccess) {
					if (fileInfoSet.remove(currentFileInfo)) {
						fileInfoSet.add(nextFileInfo);
					}
				}
				// 已到文件末尾，从set中移除该fileInfo
				else {
					fileInfoSet.remove(currentFileInfo);
				}

				System.out.println("----- MergeFile:" + count++ + " -----");
				System.out.println("fileNum=" + currentFileInfo.getFileNum());
				System.out.println("lineNum=" + currentFileInfo.getLineNum());
				System.out.println("value=" + currentFileInfo.getValue());
				System.out.println("----------------------------");
			}

			// 从未拆分过则一次性写入文件
			if (valueSet.size() > 0 && valueSet.size() < SPLIT_SIZE && !isSplit) {
				writeFile(outputPath, valueSet, false);
			}
			// 曾拆分过剩余部分写入文件
			else if (valueSet.size() > 0 && valueSet.size() < SPLIT_SIZE
					&& isSplit) {
				writeFile(outputPath, valueSet, true);
			}
		}

		/**
		 * 生成随机数
		 *
		 * @param numSize
		 * @return
		 * @throws IOException 
		 */
		private static int createRandomNum(long numSize) throws IOException {
			Set<Integer> set = new LinkedHashSet<Integer>();
			int count = 0;
			boolean isSplit = false;
			while (count < numSize) {
				int num = (int) (Math.random() * numSize + 1);
				if (set.add(num)) {
					count++;
				}
				// 拆分批量写入文件
				if (set.size() >= SPLIT_SIZE) {
					writeFile(INPUT_FILE, set, true);
					set.clear();
					isSplit = true;
				}
			}

			// 从未拆分过则一次写入文件
			if (set.size() > 0 && set.size() < SPLIT_SIZE && !isSplit) {
				writeFile(INPUT_FILE, set, false);
			}
			// 曾拆分过剩余部分写入文件
			else if (set.size() > 0 && set.size() < SPLIT_SIZE && isSplit) {
				writeFile(INPUT_FILE, set, true);
			}
			return count;
		}

		private static void createDir(String dirPath) {
			File dir = new File(dirPath);
			if (!dir.exists()) {
				if (dir.mkdir()) {
					System.out.println(dir.getName() + " is create.");
				}
			}
		}

		private static void createFile(String path) throws IOException {
			File file = new File(path);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println(file.getName() + " is create.");
				}
			}
		}

		private static void writeFile(String path, Set<Integer> set,
				boolean isAppend) throws IOException  {
			File file = new File(path);
			FileWriter fileWriter = new FileWriter(file, isAppend);// 第二个参数表示：是否为追加模
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			Iterator<Integer> iterator = set.iterator();
			while (iterator.hasNext()) {
				bufferedWriter.write(iterator.next().toString());
				bufferedWriter.newLine();
			}
			bufferedWriter.flush();
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
			if (fileWriter != null) {
				fileWriter.close();
			}
		}
	}

	static class FileInfoComparator implements Comparator<FileInfo> {
		public int compare(FileInfo o1, FileInfo o2) {
			if (Integer.parseInt(o1.getValue()) != Integer.parseInt(o2.getValue())) {
				return Integer.parseInt(o1.getValue()) - Integer.parseInt(o2.getValue());
			}
			// 如果存在重复值则使用文件号比较
			else {
				return o1.getFileNum() - o2.getFileNum();
			}
		}
	}

	static class FileInfo {
		/**
		 * 文件号
		 */
		private int fileNum;
		/**
		 * 当前行号
		 */
		private int lineNum = 0;
		/**
		 * 当前值
		 */
		private String value;
		/**
		 * BufferedReader引用
		 */
		private BufferedReader reader;

		public boolean readNextValue() throws IOException {
			String value;
			if ((value = this.reader.readLine()) != null) {
				this.value = value;
				this.lineNum++;
				return true;
			} else {
				this.reader.close();
				return false;
			}
		}

		public int getFileNum() {
			return fileNum;
		}

		public void setFileNum(int fileNum) {
			this.fileNum = fileNum;
		}

		public int getLineNum() {
			return lineNum;
		}

		public void setLineNum(int lineNum) {
			this.lineNum = lineNum;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public BufferedReader getReader() {
			return reader;
		}

		public void setReader(BufferedReader reader) {
			this.reader = reader;
		}
	}
}
