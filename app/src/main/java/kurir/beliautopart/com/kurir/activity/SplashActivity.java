package kurir.beliautopart.com.kurir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import kurir.beliautopart.com.kurir.R;
import kurir.beliautopart.com.kurir.helper.SendDataHelper;
import kurir.beliautopart.com.kurir.helper.SessionManager;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        session = new SessionManager(this);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
