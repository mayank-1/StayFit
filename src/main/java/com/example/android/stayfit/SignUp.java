package com.example.android.stayfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void onSignUp(View view){
        if(view.getId() == R.id.btsignup){
            EditText name = (EditText)findViewById(R.id.etname);
            EditText email = (EditText)findViewById(R.id.etemail);
            EditText username = (EditText)findViewById(R.id.etusername);
            EditText pass1 = (EditText)findViewById(R.id.etpassword);
            EditText pass2 = (EditText)findViewById(R.id.etpassword2);

            String name1 = name.getText().toString();
            String uname = username.getText().toString();
            String emailid = email.getText().toString();
            String password1 = pass1.getText().toString();
            String password2 = pass2.getText().toString();

            if(!password1.equals(password2)){
                //popup message
                Toast pass = Toast.makeText(SignUp.this, "Password don't match!",Toast.LENGTH_SHORT);
                pass.show();
            }
            else
            {
                //insert the details in database
                Contact c = new Contact();
                c.setName(name1);
                c.setUsername(uname);
                c.setEmail(emailid);
                c.setPass(password1);

                helper.insertContact(c);
            }

        }
    }

    public void LoginPage(View view){
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
}
