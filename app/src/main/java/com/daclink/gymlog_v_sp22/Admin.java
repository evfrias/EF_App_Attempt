package com.daclink.gymlog_v_sp22;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Admin {
    @Entity(tableName = AppDatabase.ADMIN_TABLE)
    public class Admin {
        @PrimaryKey(autoGenerate = true)
        private int ADMIN_TABLE;
    }
}
