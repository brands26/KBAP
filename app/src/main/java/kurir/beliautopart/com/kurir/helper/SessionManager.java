package kurir.beliautopart.com.kurir.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import kurir.beliautopart.com.kurir.model.ItemProduk;

/**
 * Created by brandon on 12/05/16.
 */
public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "beliautopart";

    private static final String KEY_IS_CART = "cart";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_IS_USERID = "userId";
    private static final String KEY_IS_USERALAMAT = "alamat";
    private static final String KEY_IS_USERNAMA = "nama";
    private static final String KEY_IS_USERNAMADEPAN = "namaDepan";
    private static final String KEY_IS_USERNAMABELAKANG = "namaBelakang";
    private static final String KEY_IS_HANDPHONE = "handPhone";
    private static final String KEY_IS_PASSWORD = "password";
    private static final String KEY_IS_EMAIL = "email";
    private static final String KEY_IS_LAT = "lat";
    private static final String KEY_IS_LNG = "lng";
    private static final String KEY_IS_FOTO = "foto";

    private static final String KEY_IS_ORDER_AKTIF = "orderAktif";
    private static final String KEY_IS_ORDER_ID = "orderId";
    private static final String KEY_IS_ORDER_STATUS = "orderStatus";

    private static final String KEY_IS_BENGKEL_AKTIF = "bengkelAktif";
    private static final String KEY_IS_BENGKEL_ID = "bengkelId";
    private static final String KEY_IS_BENGKEL_ID_ORDER = "bengkelIdOrder";
    private static final String KEY_IS_BENGKEL_USER_ID = "bengkelUserId";
    private static final String KEY_IS_BENGKEL_CHAT = "bengkelChat";

    private static final String KEY_IS_SETTING_MAX_PEMBELIAN = "maxBeli";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
        Log.d(TAG, "User session berhasil diubah");
    }
    public boolean isLoggedIn() {
        Log.d(TAG, "user Login in");
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
    public void setUserId(String id) {
        editor.putString(KEY_IS_USERID, id);
        editor.commit();
        Log.d(TAG, "User id berhasil diubah");
    }
    public String getUserId() {
        return pref.getString(KEY_IS_USERID, null);
    }
    public void setUserNama(String nama) {
        editor.putString(KEY_IS_USERNAMA, nama);
        editor.commit();
        Log.d(TAG, "User nama berhasil diubah");
    }
    public String getUserNama() {
        return pref.getString(KEY_IS_USERNAMA, "");
    }
    public void setUserNamaDepan(String nama) {
        editor.putString(KEY_IS_USERNAMADEPAN, nama);
        editor.commit();
        Log.d(TAG, "User nama berhasil diubah");
    }
    public String getUserNamaDepan() {
        return pref.getString(KEY_IS_USERNAMADEPAN, "");
    }
    public void setUserNamaBelakang(String nama) {
        editor.putString(KEY_IS_USERNAMABELAKANG, nama);
        editor.commit();
        Log.d(TAG, "User nama berhasil diubah");
    }
    public String getUserNamaBelakang() {
        return pref.getString(KEY_IS_USERNAMABELAKANG, "");
    }
    public void setUserAlamat(String nama) {
        editor.putString(KEY_IS_USERALAMAT, nama);
        editor.commit();
        Log.d(TAG, "User nama berhasil diubah");
    }
    public String getUserAlamat() {
        return pref.getString(KEY_IS_USERALAMAT, "");
    }
    public void setUserPassword(String nama) {
        editor.putString(KEY_IS_PASSWORD, nama);
        editor.commit();
        Log.d(TAG, "User nama berhasil diubah");
    }
    public String getUserPassword() {
        return pref.getString(KEY_IS_PASSWORD, "");
    }
    public void setUserHandphone(String nama) {
        editor.putString(KEY_IS_HANDPHONE, nama);
        editor.commit();
        Log.d(TAG, "User nama berhasil diubah");
    }
    public String getUserHandphone() {
        return pref.getString(KEY_IS_HANDPHONE, "");
    }
    public void setUserEmail(String nama) {
        editor.putString(KEY_IS_EMAIL, nama);
        editor.commit();
        Log.d(TAG, "User nama berhasil diubah");
    }
    public String getUserEmail() {
        return pref.getString(KEY_IS_EMAIL, "");
    }
    public void setUserFoto(String nama) {
        editor.putString(KEY_IS_FOTO, nama);
        editor.commit();
        Log.d(TAG, "User nama berhasil diubah");
    }
    public String getUserFoto() {
        return pref.getString(KEY_IS_FOTO, "");
    }
    public void setUserlat(double lat) {
        editor.putFloat(KEY_IS_LAT, Float.parseFloat(String.valueOf(lat)));
        editor.commit();
        Log.d(TAG, "User lat berhasil diubah :"+lat);
    }
    public double getUserlat() {
        return pref.getFloat(KEY_IS_LAT, 0);
    }
    public void setUserlang(double lng) {
        editor.putFloat(KEY_IS_LNG, Float.parseFloat(String.valueOf(lng)));
        editor.commit();
        Log.d(TAG, "User lan berhasil diubah :"+lng);
    }
    public double getUserlng() {
        return pref.getFloat(KEY_IS_LNG, 0);
    }



    public List<ItemProduk> getCart(){
        Gson gson = new Gson();
        List<ItemProduk> productFromShared = new ArrayList<>();
        String jsonPreferences = pref.getString(KEY_IS_CART, "");
        Type type = new TypeToken<List<ItemProduk>>() {}.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);
        return productFromShared;
    }
    public void setCart(ItemProduk item){

        Gson gson = new Gson();
        final List<ItemProduk> productFromShared;

        String jsonSaved = pref.getString(KEY_IS_CART, null);
        Type type = new TypeToken<List<ItemProduk>>() {}.getType();

        if(jsonSaved==null){
            productFromShared= new ArrayList<>();
            productFromShared.add(item);
        }
        else{
            productFromShared = gson.fromJson(jsonSaved, type);
            int jumlah = productFromShared.size();
            int sama = 100000;
            for(int a=0;a<jumlah;a++){
                ItemProduk itemProduk = productFromShared.get(a);
                if(item.getKode().equals(itemProduk.getKode())){
                    sama=a;
                }
            }
            if(sama==100000){
                productFromShared.add(item);
            }
            else{
                ItemProduk itemProduk = productFromShared.get(sama);
                item.setJumlah(itemProduk.getJumlah()+1);
                productFromShared.remove(sama);
                productFromShared.add(item);
            }
        }
        String json = gson.toJson(productFromShared);
        //SAVE NEW ARRAY
        editor.remove(KEY_IS_CART);
        editor.putString(KEY_IS_CART, json);
        editor.commit();

        Log.d(TAG, "Cart berhasil diubah");
    }
    public void addNewCartList(List<ItemProduk> produkList){
        Gson gson = new Gson();
        String json = gson.toJson(produkList);
        editor.remove(KEY_IS_CART);
        editor.putString(KEY_IS_CART, json);
        editor.commit();
    }
    public void emptyCart(){
        editor.remove(KEY_IS_CART);
        editor.commit();
    }
    public void setOrderAktif(boolean order) {
        editor.putBoolean(KEY_IS_ORDER_AKTIF, order);
        editor.commit();
        Log.d(TAG, "User session berhasil diubah");
    }
    public boolean getOrderAktif() {
        Log.d(TAG, "user Login in");
        return pref.getBoolean(KEY_IS_ORDER_AKTIF, false);
    }
    public void setOrderId(String id) {
        editor.putString(KEY_IS_ORDER_ID, id);
        editor.commit();
        Log.d(TAG, "order id berhasil diubah");
    }
    public String getOrderId() {
        return pref.getString(KEY_IS_ORDER_ID, "");
    }
    public void setOrderStatus(String id) {
        editor.putString(KEY_IS_ORDER_STATUS, id);
        editor.commit();
        Log.d(TAG, "order Status berhasil diubah");
    }
    public String getOrderStatus() {
        return pref.getString(KEY_IS_ORDER_STATUS, null);
    }
    public int getCountOrder(){
        Gson gson = new Gson();
        List<ItemProduk> productFromShared = new ArrayList<>();
        String jsonPreferences = pref.getString(KEY_IS_CART, "");
        Type type = new TypeToken<List<ItemProduk>>() {}.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);
        int total;
        if(productFromShared!=null)
            total  =productFromShared.size();
        else
            total = 0;
        if(total>0)
            return total;
        else
            return 0;
    }


    public void setBengkelAktif(boolean order) {
        editor.putBoolean(KEY_IS_BENGKEL_AKTIF, order);
        editor.commit();
        Log.d(TAG, "User session berhasil diubah");
    }
    public boolean getbengkelAktif() {
        Log.d(TAG, "user Login in");
        return pref.getBoolean(KEY_IS_BENGKEL_AKTIF, false);
    }
    public void setBengkelId(String id) {
        editor.putString(KEY_IS_BENGKEL_ID, id);
        editor.commit();
        Log.d(TAG, "order id berhasil diubah");
    }
    public String getBengkelId() {
        return pref.getString(KEY_IS_BENGKEL_ID, "");
    }
    public void setBengkelIdOrder(String id) {
        editor.putString(KEY_IS_BENGKEL_ID_ORDER, id);
        editor.commit();
        Log.d(TAG, "order id berhasil diubah");
    }
    public String getBengkelIdOrder() {
        return pref.getString(KEY_IS_BENGKEL_ID_ORDER, "");
    }
    public void setBengkelUserId(String id) {
        editor.putString(KEY_IS_BENGKEL_USER_ID, id);
        editor.commit();
        Log.d(TAG, "order user id berhasil diubah");
    }
    public String getBengkelUserId() {
        return pref.getString(KEY_IS_BENGKEL_USER_ID, null);
    }

    public void setSettingMaxBeli(int id) {
        editor.putInt(KEY_IS_SETTING_MAX_PEMBELIAN, id);
        editor.commit();
        Log.d(TAG, "setting max pembelian berhasil diubah");
    }
    public int getSettingMaxBeli() {
        return pref.getInt(KEY_IS_SETTING_MAX_PEMBELIAN, 0);
    }
    public void setBengkelChat(int id) {
        editor.putInt(KEY_IS_BENGKEL_CHAT, id);
        editor.commit();
        Log.d(TAG, "setting max pembelian berhasil diubah");
    }
    public int getBengkelChat() {
        return pref.getInt(KEY_IS_BENGKEL_CHAT, 0);
    }


}
