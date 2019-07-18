package com.shop.server.MyService;

import com.shop.server.Model.Item;
import com.shop.server.Model.Pets;
import com.shop.server.Model.Products;
import com.shop.server.RedisMapper.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 创建者:Lethe   日期: 2019/2/23
 */
@Service
public class PetsService {
    @Autowired
    private RedisDao redisDao;

    public List queryPet(){
        List list=redisDao.getHashTableByName("pets");
        return list;
    }

    public List<Products> proQuery(String petid){
        //要考虑petid是多位的情况若只写"products:"+petid+"*" 一旦出现两位数就会出错
        Set set=redisDao.getSetKeys("product:"+petid+":*");

        Set set1=redisDao.getSetValue(set);
        List<Products> list=new ArrayList<>(set1);
        return list;
    }

    public List<Item> iteQuery(String productid){
        //要考虑petid是多位的情况若只写"products:"+petid+"*" 一旦出现两位数就会出错
        Set set=redisDao.getSetKeys("item:"+productid+":*");
        Set set1=redisDao.getSetValue(set);
        List<Item> list=new ArrayList<>(set1);
        return list;
    }

    public List<Item> detailQuery(String productid,String itemid){
        Set set=redisDao.getSetKeys("item:"+productid+":"+itemid);
        Set set1=redisDao.getSetValue(set);
        return new ArrayList<>(set1);
    }
}
