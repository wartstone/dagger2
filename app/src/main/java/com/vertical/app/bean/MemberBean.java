package com.vertical.app.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ls on 10/11/17.
 */

public class MemberBean implements Serializable {
    public long user_id;
    public long member_id;
    public String name;
    public boolean gender;
    public String birthdate;
    public String phone;
    public String card_no;
    public int card_type_id;
    public int recommend_by;
    public String avatarUrl;
    public int create_by;
    public String comments;
    public String create_date;
}
