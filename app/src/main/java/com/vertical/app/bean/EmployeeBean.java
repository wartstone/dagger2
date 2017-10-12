package com.vertical.app.bean;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by ls on 10/11/17.
 */

public class EmployeeBean implements Serializable {
    public long id;
    public long user_id;
    public long shop_id;
    public Date from_date;
    public Date thru_date;
    public Timestamp create_date;
}
