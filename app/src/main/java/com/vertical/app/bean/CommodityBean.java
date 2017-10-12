package com.vertical.app.bean;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by ls on 10/11/17.
 */

public class CommodityBean implements Serializable {
    public long id;
    public long user_id;
    public long member_id;
    public double sale_price;
    public double input_price;
    public String name;
    public String category;
    public Date product_date;
    public Date validation_date;
    public String imgurl;
    public String descrip;
    public String comment;
}