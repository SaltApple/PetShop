package com.shop.server.RedisMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 创建者:Lethe   日期: 2019/2/20
 */
@Repository
public class RedisDao {

    @Autowired
    public RedisTemplate<Serializable,Serializable> redisTemplate;
    @Autowired
    //               必须写redisTemplate1（即一定要与RedisConfig下定义的名字相同）否则会出错
    public RedisTemplate<Serializable,Serializable> redisTemplate1;//推荐字符串的类型的存储

    //                         为lua脚本送的值  执行的lua脚本名称
    public List executeRedisByLua(List args, String scriptName)
    {
        //1、生成执行脚本生成器
        DefaultRedisScript<List> script=new DefaultRedisScript();
        //2、找到源程序
        //                                                                     lua脚本的名字
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource(scriptName)));
        //3、设置参数列表
        script.setResultType(List.class);
        return redisTemplate1.execute(script,args);//强转也对 尽量不要强转嘛
    }

    public void setTTL(String key)
    {
        //                 哪一个键  多长时间       单位
        redisTemplate.expire(key,30, TimeUnit.MINUTES);
    }

    public void setHashTable(String tableName,String key,Object value){
        HashOperations hash=redisTemplate.opsForHash();
        hash.put(tableName,key,value);
    }

    public Object getHashTable(String tableName,String key){
        HashOperations hash=redisTemplate.opsForHash();
        Object o=hash.get(tableName,key);
        return o;
    }

    public List getHashTableByName(String tableName){
        HashOperations hash=redisTemplate.opsForHash();
        return hash.values(tableName);
    }

    public void setSetTable(String key,Object value){
        SetOperations set=redisTemplate.opsForSet();
        set.add(key,value);
    }

    public Set getSetKeys(String name){
        return redisTemplate.keys(name);
    }
    public Set getSetValue(Set keys){
        SetOperations set=redisTemplate.opsForSet();
        return set.union("",keys);
    }

    public void setStringTable(String key,Object value){
        ValueOperations vs=redisTemplate1.opsForValue();
        vs.set(key,value);
    }

    public String getStringTable(String key){
        ValueOperations vs=redisTemplate1.opsForValue();
        return vs.get(key)==null?null:vs.get(key).toString();
    }

    public Set getSetTable(String keys){
        SetOperations set=redisTemplate.opsForSet();
        return set.members(keys);
    }

    public void deleteStringTable(String key){
        redisTemplate.delete(key);
    }
}
