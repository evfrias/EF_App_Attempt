package com.daclink.gymlog_v_sp22;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.gymlog_v_sp22.db.AppDataBase;

import java.util.Date;
@Entity(tableName = AppDataBase.GYMLOG_TABLE)
public class GymLog {

    @PrimaryKey(autoGenerate = true)
    private int mLogId;
    private String mExercise;
    private int mReps;
    private double mWeight;
    private Date mDate;
    private int mUserId;

    public GymLog(String mExercise, int mReps, double mWeight, int mUserId) {
        this.mExercise = mExercise;
        this.mReps = mReps;
        this.mWeight = mWeight;

        mDate = new Date();
        mUserId = mUserId;

    }

    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getLogId() {
        return mLogId;
    }

    public void setLogId(int mLogId) {
        this.mLogId = mLogId;
    }

    public String getExercise() {
        return mExercise;
    }

    public void setExercise(String mExercise) {
        this.mExercise = mExercise;
    }

    public int getReps() {
        return mReps;
    }

    public void setReps(int mReps) {
        this.mReps = mReps;
    }

    public double getWeight() {
        return mWeight;
    }

    public void setWeight(double mWeight) {
        this.mWeight = mWeight;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    @Override
    public String toString() {
        String output;
        output = mExercise;
        output += "\n";
        output += mWeight + ":" + mReps;
        output += getDate();
        output += "userId == " + mUserId;

        return output;
    }

