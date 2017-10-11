package com.vertical.app.bean;

import java.io.Serializable;
import java.util.List;

/**
<<<<<<< ff1158b4e963a7efbbcc08d49d047a8e08a2fb72
 * Created by ls on 9/5/17.
 */

public class UserBean implements Serializable {
    private int status;
    private List<UserInfo> result;

    public List<UserInfo> getResult() {
        return result;
    }

    public int getResultSize() {
        return result == null ? 0 : result.size();
    }


    public class UserInfo implements Serializable {
        public long id;
        public String name;
        public int age;
    }
}
