package com.vertical.app.bean;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ls on 10/11/17.
 */

public class OrganizationBean implements Serializable {
    public long id;
    public long user_id;
    public String party_name;
    public int parent_id;
    public boolean is_root;
    public boolean status;
    public int create_by;
    public Timestamp create_date;
}
