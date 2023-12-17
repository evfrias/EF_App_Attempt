package com.daclink.gymlog_v_sp22;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mUserId;
    private String mUserName;
    private String mPassword;

    public User(String mUserName, String mPassword) {
        this.mUserName = mUserName;
        this.mPassword = mPassword;
    }

    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
