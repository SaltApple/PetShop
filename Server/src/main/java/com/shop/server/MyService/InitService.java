package com.shop.server.MyService;

import com.shop.server.Mapper.*;
import com.shop.server.Model.*;
import com.shop.server.RedisMapper.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 创建者:Lethe   日期: 2019/2/23
 */
@Service
public class InitService {
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private AccountMapper accountDao;
    @Autowired
    private PetsMapper petsDao;
    @Autowired
    private ProfileMapper profileDao;
    @Autowired
    private ProductsMapper productDao;
    @Autowired
    private ItemMapper itemDao;
    @Autowired
    private CartMapper cartDao;
    @Autowired
    private OrdersMapper orderDao;
    public void init(){
        this.flush();
        this.initAccount();
        this.initPets();
        this.initProfile();
        this.initItem();
        this.initProducts();
        this.initCart();
        this.initOrders();
    }

    private void flush(){
        List list=new ArrayList();
        redisDao.executeRedisByLua(list,"flushDB.lua");
    }
    private void initAccount(){
        AccountExample example=new AccountExample();
        example.createCriteria().andUserIdIsNotNull();
        List<Account> list=accountDao.selectByExample(example);
        list.forEach(c->redisDao.setHashTable("account",c.getUserName(),c));
    }

    private void initPets(){
        PetsExample example=new PetsExample();
        example.createCriteria().andPetIdIsNotNull();
        List<Pets> list=petsDao.selectByExample(example);
        list.forEach(c->redisDao.setHashTable("pets",c.getPetId().toString(),c));
    }

    private void initProfile(){
        ProfileExample example=new ProfileExample();
        example.createCriteria().andUserIdIsNotNull();
        List<Profile> list=profileDao.selectByExample(example);
        list.forEach(c->redisDao.setHashTable("profile",c.getUserId().toString(),c));
    }

    private void initProducts(){
        ProductsExample example=new ProductsExample();
        example.createCriteria().andProductIdIsNotNull();
        List<Products> list=productDao.selectByExample(example);
        list.forEach(c->redisDao.setSetTable("product:"+c.getPetId()+":"+c.getProductId(),c));
    }

    private void initItem(){
        ItemExample example=new ItemExample();
        example.createCriteria().andItemIdIsNotNull();
        List<Item> list=itemDao.selectByExample(example);
        list.forEach(c->redisDao.setSetTable("item:"+c.getProductId()+":"+c.getItemId(),c));
    }

    private void initCart(){
        CartExample example=new CartExample();
        example.createCriteria().andItemIdIsNotNull();
        List<Cart> list=cartDao.selectByExample(example);
        list.forEach(s->redisDao.setStringTable("cart:"+s.getUserId()+":"+s.getOrderId()+":"+s.getItemId(),s.getQuantity().toString()));
    }

    private void initOrders(){
        OrdersExample example=new OrdersExample();
        example.createCriteria().andOrderIdIsNotNull();
        List<Orders> list=orderDao.selectByExample(example);
        list.forEach(c->{
            SimpleDateFormat date1=new SimpleDateFormat("yyyy-MM-dd");
            if (c.getOrderDate()!=null){
                String time1=date1.format(c.getOrderDate());
                redisDao.setStringTable("orders:"+c.getUserId()+":"+c.getOrderId(),
                        time1+":"+c.getTotalPrice());

            }
        });

        AccountExample example1=new AccountExample();
        example1.createCriteria().andUserIdIsNotNull();
        List<Account> list1=accountDao.selectByExample(example1);
        list1.forEach(l1->{
            Optional optional=list.stream()
                    .filter(l->l.getUserId()==l1.getUserId())
                    .max((o1, o2)->o1.getOrderId()-o2.getOrderId());
            if(optional.isPresent()){
                Orders orders=(Orders) optional.get();
                redisDao.setStringTable("maxid:"+orders.getUserId(),
                        // (orders.getOrderDate()==null?"0":"1")+orders.getOrderId());
                        (orders.getOrderDate()==null?0:1)+new Integer(orders.getOrderId()).toString());
            }
//            //代表这个用户从未买过商品设置其为未结算状态 最大orderid为 1
//            else{
//                redisDao.setStringTable("maxid:"+l1.getUserId(),"01");
//            }
        });

    }
}
