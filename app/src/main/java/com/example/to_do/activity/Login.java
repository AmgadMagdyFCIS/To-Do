package com.example.to_do.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText EmailField, PasswordField;
    TextView SignUp, ForgetPassword;
    Button LoginBtn;
    String Email, Password;
    ToDoDBHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linkViews();
        database = new ToDoDBHelper(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id._sign_up_btn:
            {
                if(isDataValid() && database.ValidateUserData(Email,Password))
                {
                    Intent Main = new Intent(Login.this,MainActivity.class);
                    startActivity(Main);
                }
                else
                {
                    if(database.isEmailFound(Email) && !database.isPasswordFound(Password) && !Password.isEmpty())
                    {
                        Toast.makeText(Login.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            }
            case R.id.ForgetPasswordTxt:
            {
                break;
            }
            case R.id.SignUpTxt:
            {
                Intent SignUp = new Intent(getApplicationContext(), com.example.to_do.activity.SignUp.class);
                startActivity(SignUp);
                break;
            }
            default:
            {
                break;
            }
        }
    }

    private void linkViews()
    {
        EmailField = findViewById(R.id._email_field_login);
        PasswordField = findViewById(R.id._password_field_login);
        SignUp = findViewById(R.id.SignUpTxt);
        ForgetPassword = findViewById(R.id.ForgetPasswordTxt);
        LoginBtn = findViewById(R.id._sign_up_btn);

        Email = EmailField.getText().toString();
        Password = PasswordField.getText().toString();

        SignUp.setOnClickListener(this);
        ForgetPassword.setOnClickListener(this);
        LoginBtn.setOnClickListener(this);
    }

    private boolean isDataValid()
    {
        boolean isValid = true;
        String email = EmailField.getText().toString();
        String password = PasswordField.getText().toString();

        if (!isEmail(email)) {
            EmailField.setError("Valid email is required!");
            isValid = false;
        }

        if (password.isEmpty()) {
            PasswordField.setError("Password is required!");
            isValid = false;
        }

        return isValid;
    }

    private boolean isEmail(String email)
    {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}