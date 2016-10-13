package kurir.beliautopart.com.kurir.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import kurir.beliautopart.com.kurir.R;
import kurir.beliautopart.com.kurir.app.AppController;
import kurir.beliautopart.com.kurir.fragment.LoginFragment;

public class AuthenticationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView logo = (ImageView) findViewById(R.id.imgLogo);
        logo.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle("");
        String page;

        loginFragment = new LoginFragment();
        changeFragment(loginFragment);
    }


    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.frameAuth, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
    public void onBackClick(View v){
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        AppController.getInstance().cancelPendingRequests("volley");
    }


}
