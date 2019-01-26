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
 * ���ı��ļ����� ����100G��
 * 
 * @author zdpwilliam
 *
 */
public class BitTextSort {

	public static void main(String[] args) throws IOException {
		// ��������ļ�
		MergeSort.createDir("F:\\test\\bit-text");
		MergeSort.createFile(MergeSort.INPUT_FILE);
		MergeSort.numSize = MergeSort.createRandomNum(MergeSort.THOUSAND_MILLION);

		MergeSort.sortFile(MergeSort.INPUT_FILE);

		long beginTime = System.currentTimeMillis();
		System.out.println("begin=" + beginTime);

		// ����ļ�
		MergeSort.splitFile(MergeSort.INPUT_FILE, MergeSort.numSize);

		List<String> splitFilePathList = new ArrayList<String>();
		File dir = new File("F:\\test\\bit-text\\temp");
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			splitFilePathList.add(file.getAbsolutePath());
		}
		
		// �ϲ��ļ�
		MergeSort.createFile(MergeSort.OUTPUT_FILE);
		MergeSort.mergeFile(splitFilePathList, MergeSort.OUTPUT_FILE);

		long endTime = System.currentTimeMillis();
		System.out.println("end=" + endTime);
		System.out.println("end-begin=" + (endTime - beginTime));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		System.out.println(simpleDateFormat.format(endTime - beginTime));

		// ɾ������ļ�
		System.gc();
	}

	static class MergeSort {
		/** ʮ */
		private static long TEN = 10;
		/** �� */
		private static long HUNDRED = 100;
		/** ǧ */
		private static long THOUSAND = 1000L;
		/** �� */
		private static long MILLION = 10000L; // 107800:00:01 078
		/** ʮ�� */
		private static long TEN_MILLION = 100000L; // 965600:00:09 656
		/** ���� */
		private static long HUNDRED_MILLION = 1000000L; // 9373300:01:33 733
		/** ǧ�� */
		private static long THOUSAND_MILLION = 10000000L; // 97014400:16:10 144
		/** �� */
		private static long BILLION = 100000000L;
		/** ʮ�� */
		private static long TEN_BILLION = 1000000000L;
		/** ���� */
		private static long HUNDRED_BILLION = 10000000000L;
		/** ǧ�� */
		private static long THOUSAND_BILLION = 100000000000L;

		private static String INPUT_FILE = "F:\\test\\bit-text\\input.txt";
		private static String OUTPUT_FILE = "F:\\test\\bit-text\\output.txt";

		/** ��ִ�С */
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
			createFile("F:\\test\\bit-text\\input����.txt");
			writeFile("F:\\test\\bit-text\\input����.txt", set, false);
		}

		/**
		 * ����ļ�
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
					// �����������д�����ļ�
					if (count >= SPLIT_SIZE) {
						createFile("F:\\test\\bit-text\\temp\\" + fileNum + ".txt");
						writeFile("F:\\test\\bit-text\\temp\\" + fileNum + ".txt", set,
								false);
						set.clear();
						count = 0;
						fileNum++;
					}
					count++;// ��ȡ�ļ���ǰ����
				}
			}
			// ����δ�ﵽ�������д�����ļ�
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
		 * �ϲ��ļ�
		 *
		 * <p>
		 * 1.txt��1��3��5��7��9����2.txt��6��8��9��<br/>
		 * ����1��6����treeset�� <br/>
		 * ���1��������������1.txt�ģ��ٶ���3����ʱset�е�Ԫ����6��3��<br/>
		 * ���3�����ֻ���������1.txt�ģ��ٶ���5����ʱset�е�Ԫ����6��5�� <br/>
		 * ���5�����ֻ���������1.txt�ģ��ٶ���7����ʱset�е�Ԫ����6��7�� <br/>
		 * ���6������������2.txt�ģ�����8����ʱset�е�Ԫ����8��7�� <br/>
		 * ���7������������1.txt�ģ�����9����ʱset�е�Ԫ����8��9�� <br/>
		 * ���8������������2.txt�ģ�����9����ʱset�е�Ԫ����9��9��
		 * </p>
		 *
		 * @param splitFilePathList
		 * @param outputPath
		 * @throws IOException
		 */
		private static void mergeFile(List<String> splitFilePathList,
				String outputPath) throws IOException {
			// fileInfo��ӵ�set
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
							.substring(splitFilePath.lastIndexOf("\\") + 1, splitFilePath.indexOf(".txt"))));// �ļ���
					fileInfo.setReader(bufferedReader);// reader����
					String value = bufferedReader.readLine();
					if (value != null) {
						fileInfo.setValue(value);// ��ǰֵ
						fileInfo.setLineNum(fileInfo.getLineNum() + 1);// ��ǰ�к�
						fileInfoSet.add(fileInfo);
					}
				}
			}

			Set<Integer> valueSet = new LinkedHashSet<Integer>();
			boolean isSplit = false;
			int count = 1;
			// ���setԪ��
			while (!fileInfoSet.isEmpty()) {
				FileInfo currentFileInfo = fileInfoSet.first();
				valueSet.add(Integer.parseInt(currentFileInfo.getValue()));
				// �������д���ļ�
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

				// δ���ļ�ĩβ��set��fileInfo��������
				if (isSuccess) {
					if (fileInfoSet.remove(currentFileInfo)) {
						fileInfoSet.add(nextFileInfo);
					}
				}
				// �ѵ��ļ�ĩβ����set���Ƴ���fileInfo
				else {
					fileInfoSet.remove(currentFileInfo);
				}

				System.out.println("----- MergeFile:" + count++ + " -----");
				System.out.println("fileNum=" + currentFileInfo.getFileNum());
				System.out.println("lineNum=" + currentFileInfo.getLineNum());
				System.out.println("value=" + currentFileInfo.getValue());
				System.out.println("----------------------------");
			}

			// ��δ��ֹ���һ����д���ļ�
			if (valueSet.size() > 0 && valueSet.size() < SPLIT_SIZE && !isSplit) {
				writeFile(outputPath, valueSet, false);
			}
			// ����ֹ�ʣ�ಿ��д���ļ�
			else if (valueSet.size() > 0 && valueSet.size() < SPLIT_SIZE
					&& isSplit) {
				writeFile(outputPath, valueSet, true);
			}
		}

		/**
		 * ���������
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
				// �������д���ļ�
				if (set.size() >= SPLIT_SIZE) {
					writeFile(INPUT_FILE, set, true);
					set.clear();
					isSplit = true;
				}
			}

			// ��δ��ֹ���һ��д���ļ�
			if (set.size() > 0 && set.size() < SPLIT_SIZE && !isSplit) {
				writeFile(INPUT_FILE, set, false);
			}
			// ����ֹ�ʣ�ಿ��д���ļ�
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
			FileWriter fileWriter = new FileWriter(file, isAppend);// �ڶ���������ʾ���Ƿ�Ϊ׷��ģ
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
			// ��������ظ�ֵ��ʹ���ļ��űȽ�
			else {
				return o1.getFileNum() - o2.getFileNum();
			}
		}
	}

	static class FileInfo {
		/**
		 * �ļ���
		 */
		private int fileNum;
		/**
		 * ��ǰ�к�
		 */
		private int lineNum = 0;
		/**
		 * ��ǰֵ
		 */
		private String value;
		/**
		 * BufferedReader����
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
