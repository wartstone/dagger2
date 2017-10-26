package com.vertical.app.bean;

import java.io.Serializable;

/**
 * Created by ls on 10/11/17.
 */

public class OrderBean implements Serializable {
    public long id;
    public long order_id;
    public String order_date;
    public String product_name;
    public int quantity;
    public double amount;
    public String comments;
    public int order_picture_id;
    public int create_by;
    public int status;
    public String create_date;
}
