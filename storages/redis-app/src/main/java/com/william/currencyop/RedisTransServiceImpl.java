package com.william.currencyop;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

import java.util.*;

/**
 * Created by william on 17-8-2.
 */
public class RedisTransServiceImpl implements RedisTransService {

    private static final String SORTEDSET_KEY = "wo-device-group-lessonuids";
    private static final String QUERYLIST_KET = "wo-device-group-query-fields";

    private RedisConnectionFactory connFactory;

    public void setConnFactory(RedisConnectionFactory connFactory) {
        this.connFactory = connFactory;
    }

    @Override
    public List<String> searchBy(String name) {
        RedisConnection conn = connFactory.getConnection();
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(String.format("*%s*", name)).build();
        List<String> resultList = Lists.newArrayList();
        Cursor<Map.Entry<byte[], byte[]>> entryCursor =
                conn.hScan(QUERYLIST_KET.getBytes(), scanOptions);
        while (entryCursor.hasNext()) {
            Map.Entry<byte[], byte[]> entry = entryCursor.next();
            System.out.println("key: " + new String(entry.getKey())
                    + " ------ value: " + new String(entry.getValue()));
            resultList.add(new String(entry.getValue()));
        }
        close(conn);
        return resultList;
    }

    @Override
    public boolean addNormalByUidName(String uid, String name) {
        RedisConnection conn = connFactory.getConnection();

        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(String.format("lessonuid:%s:status:*", uid)).build();
        Cursor<RedisZSetCommands.Tuple> tupleCursor =
                conn.zScan(SORTEDSET_KEY.getBytes(), scanOptions);
        String existedKey = null;
        while (tupleCursor.hasNext()) {
            RedisZSetCommands.Tuple tuple = tupleCursor.next();
            existedKey = new String(tuple.getValue());
        }
        if(existedKey != null) {
            System.out.println(uid + "----" + name + " has existed key: " + existedKey);
            close(conn);
            return false;
        }

        Map<byte[], byte[]> keyValueMap = Maps.newHashMap();
        keyValueMap.put(String.format("lessonUid:%s:name:%s", uid, name).getBytes(), uid.getBytes());
        String sortedValue = String.format("lessonuid:%s:status:0", uid);

        conn.multi();
        conn.hMSet(QUERYLIST_KET.getBytes(), keyValueMap);
        conn.zAdd(SORTEDSET_KEY.getBytes(), Long.valueOf(System.currentTimeMillis()).doubleValue(), sortedValue.getBytes());
        conn.exec();
        close(conn);
        return true;
    }

    @Override
    public boolean updateByUid(String uid, Integer status) {
        RedisConnection conn = connFactory.getConnection();
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(String.format("lessonuid:%s:status:*", uid)).build();
        Cursor<RedisZSetCommands.Tuple> tupleCursor =
                conn.zScan(SORTEDSET_KEY.getBytes(), scanOptions);
        String oldSortedValue = null;
        while (tupleCursor.hasNext()) {
            RedisZSetCommands.Tuple tuple = tupleCursor.next();
            oldSortedValue = new String(tuple.getValue());
        }

        String newSortedValue = String.format("lessonuid:%s:status:%d", uid, status);

        conn.multi();
        conn.zRem(SORTEDSET_KEY.getBytes(), oldSortedValue.getBytes());
        conn.zAdd(SORTEDSET_KEY.getBytes(), Long.valueOf(System.currentTimeMillis()).doubleValue(), newSortedValue.getBytes());
        conn.exec();
        close(conn);
        return true;
    }

    @Override
    public boolean deleteByUid(String uid) {
        RedisConnection conn = connFactory.getConnection();
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(String.format("lessonuid:%s:status:*", uid)).build();
        Cursor<RedisZSetCommands.Tuple> tupleCursor =
                conn.zScan(SORTEDSET_KEY.getBytes(), scanOptions);
        String curDeletedSortedKey = null;
        while (tupleCursor.hasNext()) {
            RedisZSetCommands.Tuple tuple = tupleCursor.next();
            curDeletedSortedKey = new String(tuple.getValue());
        }

        scanOptions = ScanOptions.scanOptions()
                .match(String.format("lessonUid:%s:*", uid)).build();
        Cursor<Map.Entry<byte[], byte[]>> entryCursor =
                conn.hScan(QUERYLIST_KET.getBytes(), scanOptions);
        List<byte[]> deletedQueryKeyList = Lists.newArrayList();
        while (entryCursor.hasNext()) {
            Map.Entry<byte[], byte[]> entry = entryCursor.next();
            System.out.println("key: " + new String(entry.getKey())
                    + " ------ value: " + new String(entry.getValue()));
            deletedQueryKeyList.add(entry.getKey());
        }

        conn.multi();
        conn.zRem(SORTEDSET_KEY.getBytes(), curDeletedSortedKey.getBytes());
        conn.hDel(QUERYLIST_KET.getBytes(), toArray(deletedQueryKeyList));
        conn.exec();
        close(conn);
        return true;
    }

    @Override
    public String getRandomUid() {
        RedisConnection conn = connFactory.getConnection();
        Set<byte[]> byteValues = conn.zRange(SORTEDSET_KEY.getBytes(), 0, -1);
        close(conn);
        String uid = null;
        if(byteValues.size() < 1) {
            return uid;
        }
        int indexPos = getRandomIndex(byteValues.size());
        Iterator<byte[]> byteValueIterator = byteValues.iterator();
        int startPos = 0;
        while (byteValueIterator.hasNext()) {
            byte[] byteValue = byteValueIterator.next();
            if(startPos == indexPos) {
                uid = new String(byteValue);
                break;
            }
            startPos ++;
        }
        return uid;
    }

    private int getRandomIndex(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    private byte[][] toArray(List<byte[]> sourceList) {
        byte[][] result = new byte[sourceList.size()][];
        if(sourceList.size() > 0) {
            for (int i = 0; i < sourceList.size(); i++) {
                result[i] = sourceList.get(i);
            }
        }
        return result;
    }

    private void close(RedisConnection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }
}
