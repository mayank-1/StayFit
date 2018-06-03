package com.stayfit.app.stayfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.stayfit.app.stayfit.R;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity{

    private TextView tv;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* Stetho */
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        /* Database */
        DBAdapter db = new DBAdapter(this);
        db.open();

        /* Setup for food */
        // Count rows in food
        int numberRows = db.count("food");

        if(numberRows < 1){
            // Run setup
            // Toast.makeText(this, "Loading setup...", Toast.LENGTH_LONG).show();
            DBSetupInsert setupInsert = new DBSetupInsert(this);
            setupInsert.insertAllCategories();
            setupInsert.insertAllFood();
            // Toast.makeText(this, "Setup completed!", Toast.LENGTH_LONG).show();

        }

        /* Check if there is user in the user table */
        // Count rows in user table
        numberRows = db.count("users");

        /* Close database */
        db.close();

        if(numberRows < 1){
            // Sign up
            // Toast.makeText(this, "You are only few fields away from signing up...", Toast.LENGTH_LONG).show();

            tv = (TextView) findViewById(R.id.tv);
            iv = (ImageView) findViewById(R.id.iv);
            Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
            tv.startAnimation(myAnim);
            iv.startAnimation(myAnim);
            final Intent i = new Intent(this,SignUp.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        startActivity(i);
                        finish();
                    }
                }
            };
            timer.start();
        }
        else{
            Intent i = new Intent(MainActivity.this, FragmentActivity.class);
            startActivity(i);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
