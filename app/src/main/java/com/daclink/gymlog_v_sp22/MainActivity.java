package com.daclink.gymlog_v_sp22;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.daclink.gymlog_v_sp22.db.GymLogDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.daclink.gymlog_v_sp22.db.userIdKey";
    private static final String PREFENCES_KEY ="com.daclink.gymlog_v_sp22.db.PREFENCES KEY";
    //All fields have to be private
    private TextView mMainDisplay;
    private EditText mExercise;
    private EditText mWeight;
    private EditText mReps;

    Button mSubmitButton;

    private GymLogDAO mGymLogDAO;

    private List<GymLog> mGymLogs;

    private int mUserId = -1;

    private User mUser;

    private SharedPreferences mPreferences = null;

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFENCES_KEY, Context.MODE_PRIVATE);
    }

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
                    }
                });
    }
    private GymLog checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        if (mUserId != -1) {
            return;
        }
        if (mPreferences ==null){
            getPreferences();
        }
        SharedPreferences preferences = this.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE);
        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if (mUserId != -1) {
            return;
        }
        List<User> users = mGymLogDAO.getAllUsers();
        if (users.size() <= 0) {
            User defaultUser = new User("EFF116", "EF117")
            mGymLogDAO.insert(defaultUser);
        }
        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    public  void getDataBase(){
        GymLogDAO mGymLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGymLogDAO();
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
}
        private void addUserToPreference(int userId) {
            if(mPreferences == null) {
                getPrefs();
            }
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putInt(USER_ID_KEY, userId);
        }

        private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY,-1);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataBase();
        checkForUser();
        addUserToPreference();
        loginUser(mUserId);

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


                try {
                    reps = Integer.parseInt(mReps.getText().toString());
                } catch (NumberFormatException e)
                Log.d("GYMLOG", "Couldn't convert reps")

                GymLog log = new GymLog(exercise, reps, weight, mUserId);

                return log;
            }

    private void refreshDisplay{
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

    private void loginUser(int mUserId) {
        private void loginUser(int userId) {
            mUser = mGymLogDAO.getUserByUserId(userId);
            invalidateOptionsMenu();
        }
    }


    @Override
        public boolean onPrepareOptionsMenu(Menu menu){
          if(mUser != null){
              int userMenuLogout = R.id.userMenuLogout;
              MenuItem item = (MenuItem) menu,findItem (R.id.userMenuLogout);
            item.setTitle(mUser.getmUserName());
          }
          return super.onPrepareOptionsMenu();
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
