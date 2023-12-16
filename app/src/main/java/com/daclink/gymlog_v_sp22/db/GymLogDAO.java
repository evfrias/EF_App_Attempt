package com.daclink.gymlog_v_sp22.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.daclink.gymlog_v_sp22.GymLog;

import java.util.List;

@Dao
public interface GymLogDAO {
    @Insert
    void insert(GymLog...gymLogs);

    @Update
    void update(GymLog...gymLogs);

    @Delete
    void delete(GymLog gymLog);

    @Query("SELECT * FROM" + AppDataBase.GYMLOG_TABLE + "ORDER BY mDate DESC")
    List<GymLog> getGymLogs();

    @Query("SELECT * FROM" + AppDataBase.GYMLOG_TABLE + "WHERE mLogId = :logId")
    GymLog getGymLogsById(int logId);

}
