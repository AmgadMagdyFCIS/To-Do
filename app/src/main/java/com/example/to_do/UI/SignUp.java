package com.example.to_do.UI;

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
            case R.id.sign_up_button:
            {
                if(isDataValid())
                {
                    getDataFromEditTexts();
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
        FirstNameField = findViewById(R.id.first_name_edit_text);
        LastNameField = findViewById(R.id.last_name_edit_text);
        EmailField = findViewById(R.id.signup_email_edit_text);
        PasswordField = findViewById(R.id.signup_password_edit_text);
        SignUpBtn = findViewById(R.id.sign_up_button);

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

    private void getDataFromEditTexts()
    {
        FirstName = FirstNameField.getText().toString();
        LastName = LastNameField.getText().toString();
        Email = EmailField.getText().toString();
        Password = PasswordField.getText().toString();
    }

}