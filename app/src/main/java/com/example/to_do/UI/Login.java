package com.example.to_do.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.to_do.Database.ToDoDBHelper;
import com.example.to_do.R;

public class Login extends AppCompatActivity implements View.OnClickListener {
    public static String mainEmail;
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
            case R.id.sign_in_button:
            {
                getDataFromEditTexts();
                if(isDataValid() && database.isEmailFound(Email) && database.isPasswordFound(Password))
                {
                    mainEmail=Email;
                    Intent Main = new Intent(Login.this,MainActivity.class);
                    startActivity(Main);
                }
                else
                {
                    if(database.isEmailFound(Email) && !database.isPasswordFound(Password))
                    {
                        PasswordField.setError("Wrong Password");
                    }
                }
                break;

            }
            case R.id.forget_password_text_view:
            {
                Intent recover = new Intent(getApplicationContext(), com.example.to_do.UI.Recover_Password.class);
                startActivity(recover);
                break;
            }
            case R.id.sign_up_text_view:
            {
                Intent SignUp = new Intent(getApplicationContext(), com.example.to_do.UI.SignUp.class);
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
        EmailField = findViewById(R.id.login_email_edit_text);
        PasswordField = findViewById(R.id.login_password_edit_text);
        SignUp = findViewById(R.id.sign_up_text_view);
        ForgetPassword = findViewById(R.id.forget_password_text_view);
        LoginBtn = findViewById(R.id.sign_in_button);


        SignUp.setOnClickListener(this);
        ForgetPassword.setOnClickListener(this);
        LoginBtn.setOnClickListener(this);
    }

    private boolean isDataValid()
    {
        boolean isValid = true;

        if (!isEmail(Email)) {
            EmailField.setError("Valid email is required!");
            isValid = false;
        }

        if (Password.isEmpty()) {
            PasswordField.setError("Password is required!");
            isValid = false;
        }

        return isValid;
    }

    private boolean isEmail(String email)
    {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void getDataFromEditTexts()
    {
        Email = EmailField.getText().toString();
        Password = PasswordField.getText().toString();
    }
}