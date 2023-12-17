package com.daclink.gymlog_v_sp22;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.daclink.gymlog_v_sp22.db.GymLogDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mButton;
    private GymLogDAO mGymLogDAO;
    private String mUsernameString;
    private String mPasswordString;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireupDisplay();
        getDataBase();
    }


    private void wireupDisplay() {
        mUsername = findViewById(R.id.editTextLoginUsername);
        mPassword = findViewById(R.id.editTextLoginPassword);
        mButton = findViewById(R.id.buttonLogin);

        mButton.setOnLongClickListener(new View.OnClickListener() {
            @Override
            public void onClick() {
                onClick(null);
            }

            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if (checkForUserInDatabase()) ;
                if (!validatePassword()){
                    Toast.makeText(LoginActivity.this,"Invalid Password", Toast.LENGTH_SHORT).show();

                }else{
                    Intent intent = MainActivity.intentFactory(getApplicationContext(),mUser.getmUserId());
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validatePassword(){
       return mUser.getmPassword().equals(mPassword);
    }
    private void getValuesFromDisplay(){
    mUsernameString = mUsername.getText().toString();
    mPasswordString = mPassword.getText().toString();
    }

    private boolean checkForUserInDatabase(){
        mUser = mGymLogDAO.getUserByUsername(mUsername);
        if(mUser == null) {
            Toast.makeText(this, "no user " + mUsername + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);

        return intent;
    }

    private void getDatabase() {
        mGymLogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGymLogDAO();
    }

    public Object getDataBase() {
        return dataBase;
    }

    public void setDataBase(Object dataBase) {
        this.dataBase = dataBase;
    }
}


