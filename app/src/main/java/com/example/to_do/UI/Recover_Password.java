package com.example.to_do.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.to_do.R;
import com.example.to_do.Database.ToDoDBHelper;

public class Recover_Password extends AppCompatActivity implements View.OnClickListener {

    EditText Email, NewPassword;
    String email, passowrd;
    Button CheckMail, CreatePassword;
    ToDoDBHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover__password);

        linkViewsToCode();
    }

    private void linkViewsToCode()
    {
        Email = findViewById(R.id.recoveryemailtxt);
        NewPassword = findViewById(R.id.newPasswordtxt);
        CheckMail = findViewById(R.id.checkmailbtn);
        CreatePassword = findViewById(R.id.createpasswordbtn);

        CheckMail.setOnClickListener(this);
        CreatePassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkmailbtn: {
                database = new ToDoDBHelper(this);
                email = Email.getText().toString();
                if (isEmail(email)) {
                    //check if the email is found in database
                    if (database.isEmailFound(email)) {
                        CheckMail.setVisibility(View.INVISIBLE);
                        Email.setVisibility(View.INVISIBLE);

                        NewPassword.setVisibility(View.VISIBLE);
                        CreatePassword.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getApplicationContext(), "Unregistered email address", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Email.setError("Enter a valid email!");
                }
                break;
            }
            case R.id.createpasswordbtn: {
                database = new ToDoDBHelper(this);
                passowrd = NewPassword.getText().toString();
                if (!passowrd.isEmpty()) {
                    //update the email and password info in the data base
                    database.RecoverPassword(email, passowrd);

                    Toast.makeText(getApplicationContext(), "new password has been set", Toast.LENGTH_LONG).show();
                    Intent login = new Intent(getApplicationContext(), Login.class);
                    startActivity(login);
                } else {
                    NewPassword.setError("Password is required!");
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    private boolean isEmail(String mail) {
        return !mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }


}