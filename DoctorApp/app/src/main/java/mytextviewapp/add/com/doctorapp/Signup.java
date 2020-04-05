package mytextviewapp.add.com.doctorapp;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5;
    String url="http://192.168.1.104/onlinetest3/create.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        e1=findViewById(R.id.unameid);
        e2=findViewById(R.id.passid);
        e3=findViewById(R.id.emailid);
        e4=findViewById(R.id.addressid);
        e5=findViewById(R.id.phoneid);
    }
    public void Sign(View v){
        final String name=e1.getText().toString();
        final String password=e2.getText().toString();
        final String email=e3.getText().toString();
        final String address=e4.getText().toString();
        final String phone=e5.getText().toString();
        if(name.isEmpty() || password.isEmpty()|| email.isEmpty() || address.isEmpty() || phone.isEmpty()){
            Toast.makeText(this,"all fields Required",Toast.LENGTH_SHORT).show();
            }
            else{
            StringRequest sr=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    try {
                        JSONObject js=new JSONObject(s);
                        boolean x=js.getBoolean("fb");
                        if(x==true)
                        {
                        Toast.makeText(Signup.this,"Successful create..!",Toast.LENGTH_SHORT).show();
                        e1.setText("");
                        e2.setText("");
                        e3.setText("");
                        e4.setText("");
                        e5.setText("");
                        }
                        else
                        {
                            Toast.makeText(Signup.this,"Failed create",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> pm=new HashMap<>();
                    pm.put("uname",name);
                    pm.put("upass",password);
                    pm.put("uemail",email);
                    pm.put("uaddress",address);
                    pm.put("uphone",phone);

                    return pm;
                }
            };
            RequestQueue rq= Volley.newRequestQueue(this);
            rq.add(sr);

        }

    }
    public void backtomain(View v){
        startActivity(new Intent(Signup.this,MainActivity.class));

    }
}
