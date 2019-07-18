package com.shop.server.MyService;

import com.shop.server.Mapper.AccountMapper;
import com.shop.server.Mapper.MyAccountMapper;
import com.shop.server.Mapper.ProfileMapper;
import com.shop.server.Model.Account;
import com.shop.server.Model.Profile;
import com.shop.server.RedisMapper.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.shop.server.MyConfig.MyConst.USERCOOKIE;

/**
 * 创建者:Lethe   日期: 2019/2/20
 */
@Service
public class LoginService {
    @Autowired
    private MyAccountMapper loginDao;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private AccountMapper accountDao;

    @Autowired
    private ProfileMapper profileDao;

    public Account login(Map<String,String> mes){
        Object object=redisDao.getHashTable("account",mes.get("userName"));
        if(object!=null){
            Account account=(Account)object;
            if(mes.get("password").equals(account.getPassword())){
                return account;
            }
            else{
                return null;
            }
        }
        return null;
//        return loginDao.login(mes);
    }

    //只是确定的用户
    public void addCookie(Account account,String sessionId){
        List list=new ArrayList();
        list.add(sessionId);
        list.add(account.getUserId()+":"+account.getUserName());
        redisDao.executeRedisByLua(list,"addCookie.lua");
        redisDao.setTTL(sessionId);
    }

    public String verifyCookie(String sessionId){
        List list=new ArrayList();
        list.add(sessionId);
        return redisDao.executeRedisByLua(list,"verifyCookie.lua").get(0).toString();
    }

    public void delCookie(String sessionId){
        List list=new ArrayList();
        list.add(sessionId);
        redisDao.executeRedisByLua(list,"deleteCookie.lua");
    }

    public void register(Account account){
        accountDao.insert(account);
        account.getProfile().setUserId(account.getUserId());
        profileDao.insert(account.getProfile());

        redisDao.setHashTable("account",account.getUserName(),account);
        redisDao.setHashTable("profile",account.getProfile().getUserId().toString(),account.getProfile());
    }
}
