package com.vertical.app.module.work.bizanalysis.salesquery;

import java.io.Serializable;

/**
 * Created by ls on 10/11/17.
 */

public class SaleRecordBean implements Serializable {
    public long user_id;
    public long member_id;
    public String name;
    public int gender;
    public String birthday;
    public String phone;
    public String card_no;
    public int card_type_id;
    public long recommend_by;
    public String create_date;
    public String comments;
    public int avatar_picture_id;
    public long create_by;
}
