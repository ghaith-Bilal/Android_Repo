package mytextviewapp.add.com.doctorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {
    TextView tx,rg;
    EditText usname,pass;
    Gloabalvar gv;
    String url="http://192.168.1.104/onlinetest3/login.php";
  // String url2="http://192.168.1.105/onlinetest3/namedata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx=findViewById(R.id.signid);
        usname=findViewById(R.id.usid);
        pass=findViewById(R.id.passid);
        rg=findViewById(R.id.rgid);
    }
    public void Login(View v){
        final String name=usname.getText().toString();
        final String password=pass.getText().toString();
        StringRequest strequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject j=new JSONObject(s);
                    boolean x=j.getBoolean("f");
                    if(x==true){
                        gv=(Gloabalvar)getApplication();
                        gv.setName(name);
                        startActivity(new Intent(MainActivity.this,Homedoctors.class));

                    }
                    else{
                        Toast.makeText(MainActivity.this,"wrong pass or username",Toast.LENGTH_SHORT).show();
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
                Map<String,String> lg=new HashMap<>();
                lg.put("uemail",name);
                lg.put("upass",password);
                return lg;
            }
        };
        RequestQueue rs=Volley.newRequestQueue(this);
        rs.add(strequest);
    }
    public void Signup(View v)
    {
        startActivity(new Intent(this,Signup.class));
    }

}
