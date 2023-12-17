package com.daclink.gymlog_v_sp22;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.daclink.gymlog_v_sp22.db.GymLogDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.daclink.gymlog_v_sp22.db.userIdKey";
    //All fields have to be private
    private TextView mMainDisplay;
    private EditText mExercise;
    private EditText mWeight;
    private EditText mReps;

    Button mSubmitButton;

    private List<GymLog> mGymLogs;

    private int mUserId = -1;

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
    private void clearUserFromPref() {
        addUserToPreference(-1);
    }

    private GymLog getValuesFromDisplay() {
        String exercise = "No record found";
        double weight = 0.0;
        int reps = 0;

        exercise = mExercise.getText().toString();

        try {
            weight = Double.parseDouble(mWeight.getText().toString());
        } catch (NumberFormatException e){
            Log.d("GYMLOG", "Couldn't convert weight");
        }

        try {
            reps = Integer.parseInt(mReps.getText().toString());
        } catch (NumberFormatException e){
            Log.d("GYMLOG", "Couldn't convert reps");
        }

        return new GymLog(exercise, reps, weight, mUserId);
    }

    private void clearUserFromPref(){
        Toast.makeText(this, "clear useres not implemented", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainDisplay = findViewById(R.id.mainGymLogDisplay);
        mMainDisplay.setMovementMethod(new ScrollingMovementMethod());

        mExercise = findViewById(R.id.mainExerciseEditText);
        mWeight = findViewById(R.id.mainWeightEditText);
        mReps = findViewById(R.id.mainRepsEditText);

        mSubmitButton = findViewById(R.id.mainSubmitButton);

        refreshDisplay();

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

    private void refreshDisplay {
                mGymLogs = mGymLogDAO.getGymLogsByUserId(mUserId);

                if (mGymLogs.size() >= 0) ;
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
    @Override
        public boolean onCreateOptionsMenu(Menu menu){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.user_menu, menu);
            return true;
        }

    @Override
       public boolean  onOptionsItemSelected(@NonNull MenuItem item){
       switch (item.getItemId()) {
           case R.id.userMenuLogout:
               logoutUser();
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }
    }
        public static Intent intentFactory(Context context, int userId) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(USER_ID_KEY, userId);

            return intent;