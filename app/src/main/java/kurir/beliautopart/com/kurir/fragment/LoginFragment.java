package kurir.beliautopart.com.kurir.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import kurir.beliautopart.com.kurir.R;
import kurir.beliautopart.com.kurir.activity.MainActivity;
import kurir.beliautopart.com.kurir.helper.SendDataHelper;
import kurir.beliautopart.com.kurir.helper.SessionManager;
import kurir.beliautopart.com.kurir.model.UserModel;
import kurir.beliautopart.com.kurir.webservice.UserService;

/**
 * Created by brandon on 12/05/16.
 */
public class LoginFragment extends Fragment {
    private EditText inputEmail;
    private EditText inputPassword;
    private RelativeLayout btnLogin;
    private UserService userService;
    private SessionManager session;
    private RelativeLayout txtRegister;
    private RelativeLayout lbtnForgot;
    private Dialog dialogStatus;
    private Button btnOK;
    private EditText inputEmailDialog;
    private Activity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.login_fragment, container, false);
        inputEmail = (EditText) v.findViewById(R.id.inputEmail);
        inputPassword = (EditText) v.findViewById(R.id.inputKomplain);
        btnLogin = (RelativeLayout ) v.findViewById(R.id.btnLogin);
        userService = new UserService(getContext());
        session = new SessionManager(getContext());
        lbtnForgot = (RelativeLayout) v.findViewById(R.id.lbtnForgot);
        context = (Activity)getContext();
        if(session.isLoggedIn()){
            getActivity().finish();
            Toast.makeText(getContext(),"Anda Sudah Login",Toast.LENGTH_LONG).show();
        }
        inputPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*
                if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE){
                    btnLogin.performClick();
                }
                */
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputEmail.equals(null))
                    Toast.makeText(getContext(), "Email belum diisi", Toast.LENGTH_SHORT).show();
                else if (inputPassword.equals(null))
                    Toast.makeText(getContext(), "Password belum diisi", Toast.LENGTH_SHORT).show();
                else
                    onLogin();
            }
        });
        lbtnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogShow();
            }
        });
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return v;
    }

    private void onLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString().trim();
        UserModel user = new UserModel(email, password);
        userService.LoginUser(user, new SendDataHelper.VolleyCallback() {
            @Override
            public String onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Log.d("R",result);

                    // check for error
                    if (!obj.getBoolean("error")) {
                        JSONObject dataUser = new JSONObject(obj.getString("content"));
                        session.setLogin(true);
                        session.setUserId(dataUser.getString("id"));
                        session.setUserNama(dataUser.getString("nama"));
                        session.setBengkelUserId(dataUser.getString("kode"));
                        session.setUserHandphone(dataUser.getString("hp"));
                        if(dataUser.getString("foto").equalsIgnoreCase("null"))
                            session.setUserFoto("http://simkurir.beliautopart.com/fotokurir/unknown.jpg");
                        else
                            session.setUserFoto("http://simkurir.beliautopart.com/fotokurir/"+dataUser.getString("foto"));

                        Log.d("foto",session.getUserFoto());

                        Intent i = new Intent(context, MainActivity.class);
                        startActivity(i);
                        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), obj.getString("content"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                }
                return result;
            }

            @Override
            public String onError(VolleyError result) {
                return null;
            }
        });
    }

    public void DialogShow(){
        dialogStatus = new Dialog(getContext());
        dialogStatus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogStatus.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogStatus.setContentView(R.layout.dialog_lupa_pass);
        dialogStatus.setCancelable(true);
        inputEmailDialog = (EditText) dialogStatus.findViewById(R.id.inputEmail);
        btnOK = (Button) dialogStatus.findViewById(R.id.btnOk);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputEmailDialog.getText().toString().equals(""))
                    Toast.makeText(getContext(), "Email belum diisi", Toast.LENGTH_SHORT).show();
                else
                    userService.ForgotPasswordUser(inputEmailDialog.getText().toString(), new SendDataHelper.VolleyCallback() {
                        @Override
                        public String onSuccess(String result) {
                            try {
                                JSONObject obj = new JSONObject(result);

                                // check for error
                                if (!obj.getBoolean("error")) {
                                    Toast.makeText(getContext(), obj.getString("content"), Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getContext(), obj.getString("content"), Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                            }

                            return null;
                        }

                        @Override
                        public String onError(VolleyError result) {
                            return null;
                        }
                    });
            }
        });
        dialogStatus.show();
    }





}
