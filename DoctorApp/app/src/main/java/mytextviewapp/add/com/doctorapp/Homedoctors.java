package mytextviewapp.add.com.doctorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Homedoctors extends AppCompatActivity {
    Gloabalvar g;
    TextView tx;
    ListView listview1;
    String nameFromData;
    String url="http://192.168.1.104/onlinetest3/doclist.php";
    String url1="http://192.168.1.104/onlinetest3/getinformation.php";
   ArrayList<Listclass> arrayclass =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homedoctors);
        g = (Gloabalvar) getApplication();
        tx = findViewById(R.id.txtid);
        listview1 = findViewById(R.id.listid);
        final String nameofpatient = g.getName().toString();
        StringRequest dh = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject j = new JSONObject(s);
                    JSONObject ds = j.getJSONObject("dd");

                    nameFromData = ds.getString("name").toString();
                    tx.setText("welcome " + nameFromData);
                    //Toast.makeText(Homedoctors.this, nameFromData, Toast.LENGTH_LONG).show();


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
        rr.add(dh);
        //get Doctors Data
        StringRequest st=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject js=new JSONObject(s);
                    //get all doctors data from data set
                    JSONArray da=js.getJSONArray("doclist");
                    for(int i=0;i<da.length();i++)
                    {
                        JSONObject ss=da.getJSONObject(i);
                        String id=ss.getString("id");
                        String name=ss.getString("name");
                        String password=ss.getString("password");
                        String image=ss.getString("image");
                        String specialization=ss.getString("specialization");
                        String country=ss.getString("country");
                        String phone=ss.getString("phone");
                       arrayclass.add(new Listclass(name,password,image,specialization,country,phone));
                       myadapter settingAdapter=new myadapter(arrayclass);
                       listview1.setAdapter(settingAdapter);

                         }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        myadapter settingAdapter=new myadapter(arrayclass);
        listview1.setAdapter(settingAdapter);
        RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
        rq.add(st);
    }

    class myadapter extends BaseAdapter {
        ArrayList<Listclass> myarray=new ArrayList<>();

        public myadapter(ArrayList<Listclass> myarray) {

            this.myarray = myarray;
        }

        @Override
        public int getCount() {

            return myarray.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View v;
            LayoutInflater inf=getLayoutInflater();
            v=inf.inflate(R.layout.listviewitem,null);
            //combination process with listviewItem
            TextView tx=v.findViewById(R.id.Doctor_name);
            TextView ctx=v.findViewById(R.id.doctor_id);
            ImageView img=v.findViewById(R.id.imageView);
            String tostring=myarray.get(i).image;
            Picasso.with(getApplicationContext()).load("http://192.168.1.104/onlinetest3/images/"+ tostring).into(img);
            tx.setText(myarray.get(i).name);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  Intent trans = new Intent(Homedoctors.this,details.class);
                  trans.putExtra("name",myarray.get(i).name);
                    trans.putExtra("image",myarray.get(i).image);
                    trans.putExtra("specialization",myarray.get(i).specialization);
                    trans.putExtra("country",myarray.get(i).country);
                    trans.putExtra("phone",myarray.get(i).phone);
                    startActivity(trans);
                }
            });

            return v;
        }

    }
}
