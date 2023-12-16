package com.daclink.gymlog_v_sp22;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.daclink.gymlog_v_sp22.db.GymLogDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mMainDisplay;
    private EditText mExercise;
    private EditText mWeight;
    private EditText mReps;

    Button mSubmitButton;

    private List<GymLog> mGymLogs;
    private void logoutUser() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearUserFromIntent();
                        clearUserFromPref();
                        mUserId = -1;
                        checkForUser();
                    }
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //we don't really need to do anything here.
                    }
                });
    }

    private void clearUserFromPref(){
        Toast.makeText(this, "clear useres not implemented", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainDisplay = findViewById(R.id.mainGymLogDisplay);
        mExercise = findViewById(R.id.mainExerciseEditText);
        mWeight = findViewById(R.id.mainWeightEditText);
        mReps = findViewById(R.id.mainRepsEditText);

        mSubmitButton = findViewById(R.id.mainSubmitButton);
        mMainDisplay.setMovementMethod(new ScrollingMovementMethod());


        GymLogDAO mGymLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGymLogDAO();

        mSubmitButton.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                GymLog log = getValuesFromDisplay();

                mGymLogDAO.insert(log);
                refreshDisplay();
            }
        }

        private GymLog getValuesFromDisplay() {
        String exercise = "No record found";
        double weight = 0.0;
        int reps = 0;

        exercise = mExercise.getText().toString();

        try {
            weight = Double.parseDouble(mWeight.getText().toString());
        } catch (NumberFormatException e)
        Log.d("GYMLOG", "Couldn't convert weight");


        try{
            reps = Integer.parseInt(mReps.getText().toString());
        } catch(NumberFormatException e)
        Log.d("GYMLOG","Couldn't convert reps")

        GymLog log = new GymLog(exercise, reps, weight);

        return log;

    private void refreshDisplay{
        mGymLogs = mGymLogDAO.getGymLogsByUserId(mUserId);

        if (mGymLogs.size() >=0);
        {
            mMainDisplay.setText(R.string.no_records_time_to_go_to_gym);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (GymLog log : mGymLogs) {
            sb.append(log);
            sb.append("\n");
            sb.append("something else");
            sb.append("\n");
        }
        mMainDisplay.setText(sb.toString());
    }
}