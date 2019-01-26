package com.william.currencyop;

import java.util.List;

/**
 * Created by william on 17-8-2.
 */
public interface RedisTransService {

    public List<String> searchBy(String name);

    public boolean addNormalByUidName(String uid, String name);

    public boolean updateByUid(String uid, Integer status);

    public boolean deleteByUid(String uid);

    public String getRandomUid();
}
