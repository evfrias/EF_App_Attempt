package com.daclink.gymlog_v_sp22.db.typeConverters;

import androidx.room.TypeConverter;

import java.util.Date;


public class DateTypeConverters {
    @TypeConverter
    public long convertDateToLong(Date date){
        return date.getTime();
    }

    @TypeConverter
    public Date convertLongToDate(long time) {
        return new Date(time);
    }
}



