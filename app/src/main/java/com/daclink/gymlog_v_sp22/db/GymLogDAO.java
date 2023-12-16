package com.daclink.gymlog_v_sp22.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.daclink.gymlog_v_sp22.GymLog;
import com.daclink.gymlog_v_sp22.User;

import java.util.List;

@Dao
public interface GymLogDAO {
    @Insert
    void insert(GymLog...gymLogs);

    @Update
    void update(GymLog...gymLogs);

    @Delete
    void delete(GymLog gymLog);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);

    @Query("SELECT * FROM" + AppDataBase.GYMLOG_TABLE + "ORDER BY mDate DESC")
    List<GymLog> getAllGymLogs();

    @Query("SELECT * FROM" + AppDataBase.GYMLOG_TABLE + "WHERE mLogId = :logId")
    List<GymLog> getGymLogsById(int logId);

    @Query("SELECT * FROM" + AppDataBase.GYMLOG_TABLE + "WHERE mLogId = :userId" ORDER BY mDate DESC )
    List<GymLog> getGymLogsByUserId(int userId);

    @Query("SELECT * FROM" + AppDataBase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM" + AppDataBase.USER_TABLE + "WHERE mUsername = :userId")
     User getUserByUsername(String username);

}
