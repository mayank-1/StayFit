package com.example.android.stayfit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void OnLogin(View view){
        EditText usernameEt = (EditText) findViewById(R.id.etusername);
        EditText passwordEt = (EditText) findViewById(R.id.etpassword);
        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();

        String pass = helper.searchPass(username);
        if(pass.equals(password))
        {
            Intent intent = new Intent(this,Homepage.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }
        else
        {
            Toast temp = Toast.makeText(Login.this, "Username and Password don't match!",Toast.LENGTH_SHORT);
            temp.show();
        }
    }

    public void SignUpPage(View view){
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }
}
