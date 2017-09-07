package com.vertical.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by katedshan on 17/9/7.
 */

public class UserBean implements Serializable {
    public List<Info> result;

    public class Info {
        String id;
        String name;
        String age;
    }
}
