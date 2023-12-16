package com.daclink.gymlog_v_sp22.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.daclink.gymlog_v_sp22.GymLog;
import com.daclink.gymlog_v_sp22.db.typeConverters.DateTypeConverter;

@Database(entities = {GymLog.class}, version = 1)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDataBase  extends RoomDatabase{
    public static final String DB_NAME = "GYMLOG DATABASE";
    public static final String GYMLOG_TABLE = "GYMLOG TABLE";

    public abstract GymLogDAO getGymLogDAO();
}
