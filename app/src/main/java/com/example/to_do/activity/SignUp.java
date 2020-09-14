package com.example.to_do.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    EditText EmailField, PasswordField, FirstNameField, LastNameField;
    String FirstName, LastName, Email, Password;
    Button SignUpBtn;
    ToDoDBHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        linkViews();
        database = new ToDoDBHelper(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id._sign_up_btn:
            {
                if(isDataValid())
                {
                    database.SignUp(FirstName,LastName,Email,Password);
                    clearFields();
                    Intent login = new Intent(getApplicationContext(),Login.class);
                    startActivity(login);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter valid data",Toast.LENGTH_LONG).show();
                }
                break;
            }
            default:
                break;
        }

    }


    private void linkViews()
    {
        FirstNameField = findViewById(R.id._first_name_field);
        LastNameField = findViewById(R.id._last_name_field);
        EmailField = findViewById(R.id._email_field_signup);
        PasswordField = findViewById(R.id._password_field_signup);
        SignUpBtn = findViewById(R.id._sign_up_btn);

        FirstName = FirstNameField.getText().toString();
        LastName = LastNameField.getText().toString();
        Email = EmailField.getText().toString();
        Password = PasswordField.getText().toString();

        SignUpBtn.setOnClickListener(this);

    }

    private boolean isDataValid()
    {
        boolean isValid = true;
        String email = EmailField.getText().toString();
        String password = PasswordField.getText().toString();
        String firstName = FirstNameField.getText().toString();
        String lastName = LastNameField.getText().toString();

        if (!isEmail(email)) {
            EmailField.setError("Please enter a valid email!");
            isValid = false;
        }

        if (password.isEmpty()) {
            PasswordField.setError("Password is required!");
            isValid = false;
        }

        if (firstName.isEmpty()) {
            FirstNameField.setError("First Name is required!");
            isValid = false;
        }

        if (lastName.isEmpty()) {
            LastNameField.setError("Last Name is required!");
            isValid = false;
        }

        return isValid;
    }

    private void clearFields()
    {
        FirstNameField.setText("");
        LastNameField.setText("");
        EmailField.setText("");
        PasswordField.setText("");
    }


    private boolean isEmail(String email)
    {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}