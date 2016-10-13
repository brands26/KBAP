package kurir.beliautopart.com.kurir.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import kurir.beliautopart.com.kurir.R;
import kurir.beliautopart.com.kurir.helper.SessionManager;

public class PolicyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SessionManager session;
    private ImageButton btnback;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        session = new SessionManager(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnback = (ImageButton) findViewById(R.id.btnBack);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnback.setVisibility(View.VISIBLE);
        txtTitle.setText("Terms, Conditions and Policy");
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/UbuntuCondensed-Regular.ttf");
        txtTitle.setTypeface(tf);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        WebView webView = (WebView) findViewById(R.id.webView);

        webView.loadDataWithBaseURL(null, getString(R.string.policy), "text/html", "utf-8", null);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
