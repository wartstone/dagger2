package com.vertical.app.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by ls on 10/11/17.
 */

public class ShopBean implements Serializable {
    public long id;
    public String shop_no;
    public String shop_name;
    public int root_originization_id;
    public Timestamp create_date;
}
