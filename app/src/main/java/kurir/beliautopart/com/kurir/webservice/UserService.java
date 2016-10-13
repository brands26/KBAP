package kurir.beliautopart.com.kurir.webservice;

import android.content.Context;


import java.util.HashMap;
import java.util.Map;

import kurir.beliautopart.com.kurir.app.AppConfig;
import kurir.beliautopart.com.kurir.helper.SendDataHelper;
import kurir.beliautopart.com.kurir.model.UserModel;

/**
 * Created by brandon on 12/05/16.
 */
public class UserService {
    private final Context context;
    private SendDataHelper sendData;
    private String response;

    public UserService(Context context) {
        this.context = context;
        sendData = new SendDataHelper(context);
    }


    public void RegisterUser(final UserModel user, SendDataHelper.VolleyCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("namadpn", user.getNamaDepan());
        params.put("namablk", user.getNamaBelakang());
        params.put("email", user.getEmail());
        params.put("hp", "" + user.getHp());
        params.put("password", user.getPassword());
        sendData.SendData(params, AppConfig.URL_USER_REGISTER, 1, callback);
    }

    public void LoginUser(final UserModel user, SendDataHelper.VolleyCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("kode", user.getEmail());
        params.put("password", user.getPassword());
        sendData.SendData(params, AppConfig.URL_USER_LOGIN, 1, callback);
    }
    public void ForgotPasswordUser(String email, SendDataHelper.VolleyCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        sendData.SendData(params, AppConfig.URL_USER_GET_PASSWORD, 1, callback);
    }
    public void profileUser(String userId, SendDataHelper.VolleyCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", userId);
        sendData.SendData(params, AppConfig.URL_USER_PROFILE, 0, callback);
    }
    public void getKab(String id, SendDataHelper.VolleyCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        sendData.SendData(params, AppConfig.URL_USER_KAB, 1, callback);
    }
    public void UpdateUser(final UserModel user, SendDataHelper.VolleyCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", ""+user.getId());
        params.put("password", user.getPassword());
        sendData.SendData(params, AppConfig.URL_USER_UPDATE, 1, callback);
    }
    public void setPosisi(String id,double lat,double lng, SendDataHelper.VolleyCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", id);
        params.put("lat",""+ lat);
        params.put("lng",""+ lng);
        sendData.SendData(params, AppConfig.URL_USER_POSISI,0, callback);
    }
    public void getJob(String id, SendDataHelper.VolleyCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", id);
        sendData.SendData(params, AppConfig.URL_USER_JOB, 0, callback);
    }
}
