package com.shop.server.MyService;

import com.shop.server.Mapper.CartMapper;
import com.shop.server.Mapper.OrdersMapper;
import com.shop.server.Model.Cart;
import com.shop.server.Model.CartKey;
import com.shop.server.Model.Item;
import com.shop.server.Model.Orders;
import com.shop.server.RedisMapper.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 创建者:Lethe   日期: 2019/3/2
 */
@Service
public class CartService {
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private CartMapper cartMapper;

    public void addCart(Cart cart){
        List list=new ArrayList();
        list.add(cart.getUserId()+"");
        List maxid=redisDao.executeRedisByLua(list,"getMaxOrderId.lua");

        boolean ifNew=false;
        Orders orders=new Orders();
        if (maxid.get(0)==null){
            ifNew=true;
            maxid.clear();
            maxid.add("01");

            orders.setUserId(cart.getUserId());
            orders.setOrderId(1);
            ordersMapper.insert(orders);
        }

        list.clear();
        String ifSubmit=maxid.get(0).toString().substring(0,1);
        String orderid=maxid.get(0).toString().substring(1);
        int newid=Integer.parseInt(orderid);
        boolean InsertOrUpdate=true;//true 代表insert    false代表update

        if(ifSubmit.equals("1")){//代表已经结算 应新开订单
            newid++;

            orders.setUserId(cart.getUserId());
            orders.setOrderId(newid);
            ordersMapper.insert(orders);

            cart.setOrderId(newid);
        }
        else {//为 0 代表未结算订单号不变
            cart.setOrderId(newid);
            list.clear();
            list.add(cart.getUserId()+"");
            list.add(cart.getOrderId()+"");
            list.add(cart.getItemId()+"");
            List num=redisDao.executeRedisByLua(list,"getQuantity.lua");
            if(num.get(0)!=null) {//因为新用户也会走这个方法
                InsertOrUpdate=false;
                cart.setQuantity(cart.getQuantity() + Integer.parseInt(num.get(0) + ""));
            }
        }
        if (InsertOrUpdate){//已经结算插入购物车
            cartMapper.insert(cart);
        }
        else{//未结算所以更新购物车
            cartMapper.updateByPrimaryKey(cart);
        }


        //更新redis
        //1、cart:userid:orderid:itemid    quantity
        //2、maxid:userid    是否提交+orderid
        if (ifSubmit.equals("1")||ifNew){//已经结算或是从未买过商品的新用户
            list.clear();
            list.add(cart.getUserId()+"");
            list.add("0"+cart.getOrderId());
            redisDao.executeRedisByLua(list,"addOrders.lua");
        }

        list.clear();
        //存储redis cart
        list.add(cart.getUserId()+"");//1userid
        list.add(cart.getOrderId()+"");//2orderid
        list.add(cart.getItemId()+"");//3itemid
        list.add(cart.getQuantity()+"");//4quantity 已经叠加完了

        redisDao.executeRedisByLua(list,"addCart.lua");
    }

    public String getMaxOrderId(String userid){
        return redisDao.getStringTable("maxid:"+userid);
    }
    public List<Cart> showCart(String userid,String orderid){
        Set<String> set=redisDao.getSetKeys("cart:"+userid+":"+orderid+":*");
        //Set set1=redisDao.getSetValue(set);
        List<Cart> list=new ArrayList();
        set.forEach(c->{
            String quantity=redisDao.getStringTable(c);
            Cart cart=new Cart();
            String itemid=c.split(":")[3];
            cart.setUserId(Integer.parseInt(userid));
            cart.setOrderId(Integer.parseInt(orderid));
            cart.setItemId(Integer.parseInt(itemid));
            cart.setQuantity(Integer.parseInt(quantity));

            Set set1=redisDao.getSetKeys("item:*:"+itemid);
            List list1=new ArrayList(set1);
            Set linked=redisDao.getSetTable(list1.get(0).toString());
            Iterator iterator=linked.iterator();
            while (iterator.hasNext()){
                cart.setItem((Item)iterator.next());
                list.add(cart);
            }
        });

        return list;
    }

    public void del(Cart cart){
        redisDao.deleteStringTable("cart:"+cart.getUserId()+":"+cart.getOrderId()+":"+cart.getItemId());
        CartKey cartKey=new CartKey();
        cartKey.setUserId(cart.getUserId());
        cartKey.setOrderId(cart.getOrderId());
        cartKey.setItemId(cart.getItemId());
        cartMapper.deleteByPrimaryKey(cartKey);
    }

    public void update(Cart cart){
        redisDao.setStringTable("cart:"+cart.getUserId()+":"+cart.getOrderId()+":"+cart.getItemId(),cart.getQuantity());
        cartMapper.updateByPrimaryKey(cart);
    }

    public void submit(Orders orders){
        orders.setOrderDate(new Date());
        ordersMapper.updateByPrimaryKey(orders);


        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String date=dateFormat.format(orders.getOrderDate());
        redisDao.setStringTable("orders:"+orders.getUserId()+":"+orders.getOrderId(),
                date+":"+orders.getTotalPrice());
        redisDao.setStringTable("maxid:"+orders.getUserId(),"1"+orders.getOrderId());
    }
}
