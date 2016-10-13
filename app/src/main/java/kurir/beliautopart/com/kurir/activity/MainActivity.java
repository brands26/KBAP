package kurir.beliautopart.com.kurir.activity;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.MatchResult;

import kurir.beliautopart.com.kurir.R;
import kurir.beliautopart.com.kurir.app.AppController;
import kurir.beliautopart.com.kurir.helper.LocationUser;
import kurir.beliautopart.com.kurir.helper.SendDataHelper;
import kurir.beliautopart.com.kurir.helper.SessionManager;
import kurir.beliautopart.com.kurir.model.ItemProduk;
import kurir.beliautopart.com.kurir.webservice.UserService;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SessionManager session;
    private Intent i;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Toolbar toolbar;
    private LocationUser locationUser;
    private ArrayList<ItemProduk> itemlist;
    private TextView txtNama;
    private TextView txtKode;
    private TextView txtHp;
    private Handler handler;
    private UserService user;
    private LinearLayout listItem;
    private LinearLayout viewJob;
    private TextView txtNomor;
    private TextView txtKodeKurir;
    private int noJob=0;
    private CardView viewNoJob;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Marker marker;
    private LatLng lokasi;
    private String customer_nama;
    private MediaPlayer notif;
    private Dialog dialogReject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(this);
        user = new UserService(this);
        locationUser = new LocationUser(this);
        handler = new Handler();
        itemlist = new ArrayList<>();

        if(!session.isLoggedIn()){
            i = new Intent(this, AuthenticationActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView logo = (ImageView) findViewById(R.id.imgLogo);
        logo.setVisibility(View.VISIBLE);
        NetworkImageView imgKurir = (NetworkImageView) findViewById(R.id.imgKurir);
        listItem = (LinearLayout) findViewById(R.id.listItemProduk);
        viewJob = (LinearLayout) findViewById(R.id.viewJob);
        viewNoJob = (CardView) findViewById(R.id.card_view_tidak_ada_job);
        txtNomor = (TextView) findViewById(R.id.txtNomor);
        txtKodeKurir = (TextView) findViewById(R.id.textView5);
        txtNama = (TextView) findViewById(R.id.txtNama);
        txtKode = (TextView) findViewById(R.id.txtKode);
        txtHp = (TextView) findViewById(R.id.txtHp);
        txtNama.setText(""+session.getUserNama());
        txtKode.setText(""+session.getBengkelUserId());
        txtHp.setText(""+session.getUserHandphone());
        txtHp.setText(""+session.getUserHandphone());

        mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        notif = MediaPlayer.create(this, R.raw.notif);

        dialogReject = new Dialog(this);
        dialogReject.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogReject.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogReject.setContentView(R.layout.dialog_alert_one);



        imgKurir.setImageUrl(session.getUserFoto(),imageLoader);


    }

    public void addVewItemList(final ItemProduk item, LinearLayout layout ){
        final View child =  getLayoutInflater().inflate(R.layout.row_order_shipment_item_list, null);
        TextView txtNama = (TextView) child.findViewById(R.id.textView28);
        TextView txtKode = (TextView) child.findViewById(R.id.textView116);
        final LinearLayout listLayout = (LinearLayout) child.findViewById(R.id.listLayout);
        txtNama.setText(item.getNamaItem()+" ("+item.getJumlah()+")");
        txtKode.setText(""+item.getKode());
        txtNama.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_light_default));
        txtKode.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_light_default));

        listLayout.setBackgroundResource(0);
        layout.addView(child);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
    public void onAccountClick(View v) {
        i = new Intent(this, AccountActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(!session.isLoggedIn()){
            i = new Intent(this, AuthenticationActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        locationUser.start();
        startSetPosisi();
        startGetTime();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        stopSetPosisi();
        stopGetTime();
        session.setOrderAktif(false);
    }
    @Override
    public void onPause(){
        super.onPause();
        stopSetPosisi();
        stopGetTime();
        session.setOrderAktif(false);
    }


    public Runnable getTimer = new Runnable() {
        @Override
        public void run() {
            user.getJob(session.getUserId(), new SendDataHelper.VolleyCallback() {
                @Override
                public String onSuccess(String result) {
                    try {
                        JSONObject res = new JSONObject(result);
                        if(!res.getBoolean("error")){
                            if(!session.getOrderAktif() || viewNoJob.getVisibility() == View.VISIBLE){
                                notif.start();
                                JSONObject data = new JSONObject(res.getString("content"));
                                txtNomor.setText("Anda terkoneksi dengan "+ data.getString("nama_customer")+" Order No. "+data.getString("nomor"));
                                txtKodeKurir.setText(data.getString("kurir_kode"));
                                String[] items = data.getString("item").split(",");
                                String[] id_items = data.getString("id_item").split(",");
                                String[] kode_items = data.getString("kode_item").split(",");
                                String[] qty = data.getString("qty").split(",");
                                itemlist.clear();
                                listItem.removeAllViews();
                                for(int a=0; a<items.length;a++){
                                    ItemProduk itemProduk = new ItemProduk();
                                    itemProduk.setIdItem(Integer.parseInt(id_items[a]));
                                    itemProduk.setKode(kode_items[a]);
                                    itemProduk.setJumlah(Integer.parseInt(qty[a]));
                                    itemProduk.setNamaItem(items[a]);
                                    itemlist.add(itemProduk);
                                    addVewItemList(itemProduk,listItem );
                                }
                                viewNoJob.setVisibility(View.GONE);
                                viewJob.setVisibility(View.VISIBLE);
                                if(mMap!=null){
                                    mMap.clear();
                                    LatLng lokasiSekarang = new LatLng(session.getUserlat(),session.getUserlng());
                                    lokasi = new LatLng(data.getDouble("lat"),data.getDouble("lng"));
                                    marker = mMap.addMarker(new MarkerOptions().position(lokasiSekarang).title("Lokasi Anda").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_self)));
                                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                    builder.include(lokasiSekarang);
                                    marker = mMap.addMarker(new MarkerOptions().position(lokasi).title(data.getString("nama_customer")).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_car)));
                                    builder.include(lokasi);
                                    LatLngBounds bounds = builder.build();
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,200,200, 80));
                                    customer_nama = data.getString("nama_customer");
                                }
                                session.setOrderAktif(true);
                            }
                        }
                        else{
                            if(noJob>0){
                                if(session.getOrderAktif())
                                    dialogShow(false);
                                session.setOrderAktif(false);
                                viewJob.setVisibility(View.INVISIBLE);
                                noJob=0;
                            }
                            noJob+=1;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public String onError(VolleyError result) {
                    return null;
                }
            });
            handler.postDelayed(getTimer,2000);
        }

    };
    public void startGetTime(){
        handler.post(getTimer);

    }

    public void stopGetTime(){
        handler.removeCallbacks(getTimer);
    }

    public Runnable getPosisi = new Runnable() {
        @Override
        public void run() {
            user.setPosisi(session.getUserId(),session.getUserlat(),session.getUserlng(), new SendDataHelper.VolleyCallback() {
                @Override
                public String onSuccess(String result) {

                    if(mMap!=null && lokasi!=null){
                        mMap.clear();
                        LatLng lokasiSekarang = new LatLng(session.getUserlat(),session.getUserlng());
                        marker = mMap.addMarker(new MarkerOptions().position(lokasiSekarang).title("Lokasi Anda").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_kurir)));
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(lokasiSekarang);
                        marker = mMap.addMarker(new MarkerOptions().position(lokasi).title(customer_nama).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_self)));
                        builder.include(lokasi);
                        LatLngBounds bounds = builder.build();
                        if(viewJob.getVisibility()==View.VISIBLE)
                            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 80));
                    }
                    return null;
                }

                @Override
                public String onError(VolleyError result) {
                    return null;
                }
            });
            handler.postDelayed(getPosisi,3000);
        }

    };
    public void startSetPosisi(){
        handler.post(getPosisi);

    }

    public void stopSetPosisi(){
        handler.removeCallbacks(getPosisi);
    }

    public void  onJobClick(View v){
        Toast.makeText(this,"Sedang Dalam Pengembangan",Toast.LENGTH_SHORT).show();
    }
    public void  onMyOrderClick(View v){
        Toast.makeText(this,"Sedang Dalam Pengembangan",Toast.LENGTH_SHORT).show();
    }
    public void onPolicyClick(View v) {
        Intent i = new Intent(this, PolicyActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void dialogShow(boolean done){
        if(!dialogReject.isShowing())
            dialogReject.show();
        TextView txtmessage = (TextView) dialogReject.findViewById(R.id.textView27);
        if(done)
            txtmessage.setText("Ada Job untuk Anda");
        else
            txtmessage.setText("Job telah selesai");
        Button btnOk = (Button) dialogReject.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogReject.dismiss();
            }
        });

    }
}
