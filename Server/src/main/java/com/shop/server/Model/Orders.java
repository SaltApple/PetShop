package com.shop.server.Model;

import java.math.BigDecimal;
import java.util.Date;

public class Orders extends OrdersKey {
    private Date orderDate;

    private BigDecimal totalPrice;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}