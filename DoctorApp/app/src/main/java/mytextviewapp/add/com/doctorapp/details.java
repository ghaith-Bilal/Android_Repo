package mytextviewapp.add.com.doctorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class details extends AppCompatActivity {
    TextView name, specialization, country, phone, reserve, backtohome;
    ImageView imagedoctor;
    Gloabalvar gv;
    String nameFromData;
    String patientPhone;
    String url2 = "http://192.168.1.104/onlinetest3/getinformation.php";
    String url3 = "http://192.168.1.104/onlinetest3/getphone.php";
    String url4 = "http://192.168.1.104/onlinetest3/reserve.php";
    String nameofdoctor, imgdat, SpecializationOfDoctor, cityofDoctor, p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        name = findViewById(R.id.doc_name);
        specialization = findViewById(R.id.doc_spec);
        country = findViewById(R.id.doc_country);
        //phone = findViewById(R.id.doc_phone);
        reserve = findViewById(R.id.reserve_id);
        backtohome = findViewById(R.id.back_id);
        imagedoctor = findViewById(R.id.imag_doctor);
        gv = (Gloabalvar) getApplication();
        Intent i = getIntent();
        nameofdoctor = i.getExtras().getString("name");
        name.setText(nameofdoctor.toString());
        imgdat = i.getExtras().getString("image");
        Picasso.with(getApplicationContext()).load("http://192.168.1.104/onlinetest3/images/" + imgdat).into(imagedoctor);
        SpecializationOfDoctor = i.getExtras().getString("specialization");
        specialization.setText(SpecializationOfDoctor.toString());
        cityofDoctor = i.getExtras().getString("country");
        country.setText(cityofDoctor.toString());
        //p = i.getExtras().getString("phone");
        //phone.setText(p.toString());
        final String nameofpatient = gv.getName().toString();
        StringRequest ds = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject j = new JSONObject(s);
                    JSONObject ds = j.getJSONObject("dd");

                    nameFromData = ds.getString("name").toString();
                    //Toast.makeText(details.this, nameFromData, Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> ss = new HashMap<>();
                ss.put("uuemail", nameofpatient);

                return ss;
            }
        };
        RequestQueue rr = Volley.newRequestQueue(this);
        rr.add(ds);
        StringRequest ps = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject js = new JSONObject(s);
                    JSONObject ps = js.getJSONObject("d");
                    patientPhone = ps.getString("phone").toString();
                    //Toast.makeText(details.this, patientPhone, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> ps = new HashMap<>();
                ps.put("uemail", nameofpatient);
                return ps;
            }
        };
        RequestQueue rs = Volley.newRequestQueue(this);
        rs.add(ps);


    }
    public void makereserve(View v){
        StringRequest reservep = new StringRequest(Request.Method.POST, url4, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject rsd = new JSONObject(s);
                    Boolean comingdata = rsd.getBoolean("rd");
                    if (comingdata == true) {
                        Toast.makeText(details.this, "تم حجز مقابلة سيتم إخطارك بالموعد قريباً", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(details.this, "خطأ في الإدخال يرجى إعادة المحاولة", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> dd = new HashMap<>();
                dd.put("nameofpatient", nameFromData);
                dd.put("address", cityofDoctor);
                dd.put("doctorname",nameofdoctor );
                dd.put("patientphone", patientPhone);
                return dd;

            }
        };
        RequestQueue resd = Volley.newRequestQueue(details.this);
        resd.add(reservep);

    }
    public void BacktoDoctorList(View v){
        startActivity(new Intent(details.this,Homedoctors.class));
    }
}



