package com.william.example;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by william on 2016/12/2.
 */
public class BigSort {

    /**
     * @author william
     *
     * 整合map,shuffler,merge程序，定时将数据处理写到指定文件中
     */
    /*static class MapReduceDriver {

        private static final Logger logger = LoggerFactory.getLogger(MapReduceDriver.class);
        private static final Logger mail_log = LoggerFactory.getLogger("mail_log");

        public static void main(String[] args) {
            logger.info("starting.....");
            long start=System.currentTimeMillis();
            String inputDir = args[0];
            File dir = new File(inputDir);
            if (!dir.exists()) {
                logger.warn("目录不存在");
                System.exit(-1);
            }
            Collection<File> sourceFile = FileUtils.listFiles(dir, new String[] {"json" }, false);
            logger.info("sourceFiles size:" + sourceFile.size());
            logger.info("开始mapSplit");
            for (File f : sourceFile) {
                logger.info("mapSplit:"+f.getAbsolutePath());
                MapSplit map = new MapSplit();
                map.setInputFile(f);
                map.setOutputFile(new File(f.getAbsolutePath() + ".map"));
                map.setBatchSize(5000);
                map.init();
                map.map();
            }
            logger.info("完成mapsplit");

            Collection<File> mapFile = FileUtils.listFiles(dir, new String[] { "map" }, false);
            for (File f : mapFile) {
                logger.info("shuffler:"+f.getAbsolutePath());
                Shuffler shuffler=new Shuffler();
                shuffler.toShuffler(f);
            }
            logger.info("完成shuffler");
            logger.info("开始merge.......");
            String mergerFile="merger_refer";
            MergeFile merge=new MergeFile(new File(dir,mergerFile));

            Collection<File> shufflerFile = FileUtils.listFiles(dir, new String[] { "shuffler" }, false);
            try {
                merge.merge(shufflerFile);
            } catch (IOException e) {
                logger.error("",e);
            }
            logger.info("merge结束.......");
            long end = System.currentTimeMillis();
            long total=(end-start)/1000;
            logger.info("总共耗时："+total);
            logger.info("end!");
        }
    }

    static class MapSplit{
        private static final Logger logger = LoggerFactory.getLogger(MapSplit.class);
        private static final Logger mail_log = LoggerFactory.getLogger("mail_log");
        private File inputFile;
        private File outputFile;

        private LineIterator lineIter;
        private Map<String, Integer> domainMapNum = new HashMap<String, Integer>();

        private int batchSize = 5000;

        public boolean hasNext() {
            return lineIter.hasNext();
        }

        public void init() {
            logger.info("初始化");
            try {
                LineIterator lineIter = FileUtils.lineIterator(inputFile);
                if (lineIter != null) {
                    this.lineIter = lineIter;
                }
            } catch (Exception e) {
                logger.error("", e);
                mail_log.info("from "+MapSplit.class.toString());
                mail_log.error("文件是否配置有问题，读取失败");
                throw new RuntimeException("文件是否配置有问题，读取失败");

            }

            domainMapNum.put("renren.com", 1);
            domainMapNum.put("qq.com", 2);

        }

        private TIntObjectHashMap<TIntIntHashMap> vidMap = new TIntObjectHashMap<TIntIntHashMap>();

        *//**
         * 统计总共加载的数据记录数
         *//*
        private int count = 0;

        private int bigDomainCount = 0;

        public void nextData() {
            while (lineIter.hasNext()) {
                String line = lineIter.nextLine();
                try {
                    JSONObject json = new JSONObject(line);

                    Integer obj_count = json.getInt("count");
                    String obj_domain = json.getString("domain");

                    // 处理domain,只取后缀,找出对应的num
                    int obj_vid = json.getInt("vid");
                    TIntIntHashMap domain_count_map = vidMap.get(obj_vid);
                    if (domain_count_map == null) {
                        domain_count_map = new TIntIntHashMap();
                        vidMap.put(obj_vid, domain_count_map);
                    }
                    // 查找并统计
                    int domainNum = 0;
                    Iterator<String> domainIter = domainMapNum.keySet().iterator();
                    while (domainIter.hasNext()) {
                        String key = domainIter.next();
                        if (obj_domain.indexOf(key) != -1) {
                            domainNum = domainMapNum.get(key);
                            break;
                        }
                    }
                    int domainCount = domain_count_map.get(domainNum);
                    domainCount += obj_count;
                    domain_count_map.put(domainNum, domainCount);
                } catch (JSONException e) {
                    logger.error("", e);
                }
                count++;
                if (count % batchSize == 0) {
                    break;
                }
            }
            logger.info("count:" + count);
        }

        final Set<String> domainSet = new HashSet<String>();

        public void map() {
            while (hasNext()) {
                nextData();
            }
            // 写入文件
            try {
                outputFile();
            } catch (IOException e) {
                logger.error("", e);
            }
        }

        final static String separator = System.getProperty("line.separator");
        private void outputFile() throws IOException {

            final String split = ",";
            final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));
            vidMap.forEachEntry(new TIntObjectProcedure<TIntIntHashMap>() {
                @Override
                public boolean execute(int vid, TIntIntHashMap domain_count_map) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(vid);
                    sb.append(split);
                    domain_count_map.forEachEntry(new TIntIntProcedure() {
                        @Override
                        public boolean execute(int domainNum, int domain_count) {
                            sb.append(domainNum);
                            sb.append(":");
                            sb.append(domain_count);
                            sb.append(split);
                            return true;
                        }
                    });
                    sb.append(separator);
                    String line = sb.toString();
                    try {
                        out.write(line.getBytes("utf-8"), 0, line.length());
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                    return true;
                }
            });

            try {
                out.close();
            } catch (Exception e) {
                logger.error("", e);
            }
            logger.info("写文件完成");

        }

        public void setInputFile(File inputFile) {
            this.inputFile = inputFile;
        }

        public void setOutputFile(File outputFile) {
            this.outputFile = outputFile;
        }

        public void setBatchSize(int batchSize) {
            this.batchSize = batchSize;
        }

        public static void main(String[] args) {
            String inputFile = args[0];
            String outputFile = args[1] + ".map";

            MapSplit map = new MapSplit();
            map.setInputFile(new File(inputFile));
            map.setOutputFile(new File(outputFile));
            map.setBatchSize(5000);
            map.init();
            map.map();
        }
    }

    static class Shuffler {
        private static final Logger logger = LoggerFactory.getLogger(MapSplit.class);
        private static final Logger mail_log = LoggerFactory.getLogger("mail_log");

        public void toShuffler(File inputFile) {
            LineIterator iter = null;
            try {
                iter = FileUtils.lineIterator(inputFile);
            } catch (IOException e) {
                logger.error("", e);
            }
            if (iter == null) {
                return;
            }
            //读取文件到list集合
            List<VidInfo> list = new ArrayList<VidInfo>();
            while (iter.hasNext()) {
                String line = (String) iter.next();
                VidInfo vidInfo=VidInfo.lineToVidInfo(line);
                list.add(vidInfo);
            }

            //将数组做排序
            VidInfo[] array = new VidInfo[list.size()];
            list.toArray(array);
            Sort.heapsort(array);


            logger.info("写入文件 shuffler");
            String destFile = inputFile + ".shuffler";
            final String separator = System.getProperty("line.separator");
            // 排序完的数据写到中间结果文件
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(new FileOutputStream(destFile));
                for (VidInfo vidInfo : array) {
                    String line = vidInfo.toLine()+separator;
                    out.write(line.getBytes());
                }
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                try {
                    out.close();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
            logger.info("结束");

        }

        public static void main(String[] args) {
            String inputFile = args[0];
            Shuffler shuffler = new Shuffler();
            shuffler.toShuffler(new File(inputFile));
        }

    }

    static class MergeFile {
        private final static  Logger logger = LoggerFactory.getLogger(MergeFile.class);
        private final static  Logger mail_log = LoggerFactory.getLogger("mail_log");
        final static String separator = System.getProperty("line.separator");
        private File mergeFile;

        public MergeFile(File mergeFile) {
            this.mergeFile = mergeFile;
        }

        class Node implements Comparable<Node> {
            LineIterator iter;
            VidInfo currentVidInfo;

            public Node(LineIterator iter) {
                this.iter = iter;
            }

            @Override
            public int compareTo(Node o2) {
                VidInfo vidInfo2 = o2.currentVidInfo;
                //最小堆，小变大，大变小
                return -currentVidInfo.compareTo(vidInfo2);
            }

            public boolean hasNext() {
                if (iter.hasNext()) {
                    String line = (String) iter.next();
                    this.currentVidInfo = VidInfo.lineToVidInfo(line);
                    return true;
                }
                return false;
            }

        }

        *//**
         * 合并多个已排序完的文件，作合并排序写入一个文件
         * @param shufflerFile
         * @throws IOException
         *//*
        public void merge(Collection<File> shufflerFile) throws IOException {
            logger.info("开始合并操作");
            logger.info("fileList's size :" + shufflerFile.size());
            //初始化每个文件流的迭代器
            List<LineIterator> iterList = new ArrayList<LineIterator>(shufflerFile.size());
            try {
                for (File f : shufflerFile) {
                    logger.info("file:"+f.getPath());
                    LineIterator iter = FileUtils.lineIterator(f);
                    iterList.add(iter);
                }
            } catch (Exception e) {
                logger.error("", e);
            }
            if(iterList.isEmpty()){
                mail_log.info("iterList.isEmpty");
                mail_log.error("待合并的文件打开有问题，需要检查下");
                return ;
            }

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(mergeFile));
            // 将多个已排完序的迭代器合并结果写入文件
            PriorityQueue<Node> queue = new PriorityQueue<Node>();
            for (LineIterator iter : iterList) {
                Node node = new Node(iter);
                if (node.hasNext()) {
                    queue.add(node);
                }
            }
            int count=0;
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                VidInfo vidInfo = node.currentVidInfo;
                // 将从队列里取出头放并写到合并的文件
                String line = vidInfo.toLine() + separator;
                out.write(line.getBytes());
                count++;
                if(count%3000==0){
                    logger.info("已合并写入文件数："+count);
                }
                //如果还有数据则再加入队列
                if(node.hasNext()){
                    queue.add(node);
                }
            }

            logger.info("合并完文件，关闭文件流");
            //关闭文件 流
            try {
                out.close();
            } catch (Exception e) {
                logger.error("", e);
            }
            logger.info("结束");

        }

        public static void main(String[] args) {
            String[] inputFiles = args;
            if (inputFiles == null || inputFiles.length < 0)
                return;
            List<File> fileList = new ArrayList<File>(inputFiles.length);
            for (String f : inputFiles) {
                fileList.add(new File(f));
            }
            String mergerFile = "refer_sort_merger";
            MergeFile merge = new MergeFile(new File(fileList.get(0).getParent() ,mergerFile));
            try {
                merge.merge(fileList);
            } catch (IOException e) {
                logger.error("",e);

            }
        }
    }*/
}
