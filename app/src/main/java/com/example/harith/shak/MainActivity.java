package com.example.harith.shak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.harith.shak.Fragment.FragmentOne;
import com.example.harith.shak.Fragment.PagerAdapter;
import com.example.harith.shak.db.DataBaseHelper;
import com.example.harith.shak.service.myservice;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper helper;
    public static String locat,topic,sh_id,time,st,id;
    public static double v =0, h = 0;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://www.mocky.io/v2/597c41390f0000d002f4dbd1";
    String name;
    public static int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add Default Data

//       helper.insertUserLec("الشباب امل الامه وفخرها","الاثنين زو القعدة 1440ه الموافق 2018/12/12 السادسه مساء",12331,1);

        startService(new Intent(this, myservice.class));
        volleyreguest();
        LoadFragment(new FragmentOne());

        TabLayout tabLayout =  findViewById(R.id.tab);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager =  findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
              viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    volleyreguest();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    protected void onStart() {
        helper = new DataBaseHelper(this);
     //   jsonParse1();
        super.onStart();
    }

    protected void onDestroy() {

        Intent restartService = new Intent("RestartService");
        sendBroadcast(restartService);
        SharedPreferences sharedPref = getSharedPreferences("mySettings", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("mySetting",myservice.s1);
        editor.apply();

        super.onDestroy();
    }

    private void LoadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag, fragment)
                .commit();
    }

    public void volleyreguest(){

       mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject root = new JSONObject(response);

                    JSONArray jsonArray = root.getJSONArray("users");

                    for (int i =0; i<jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(0);

                        topic =  object.getString("name");
                        status = object.getInt("id");
                        helper.insertUser();
                       // helper.insertUser(name,password,password,password);
                        helper.insertUserLec("الشباب امل الامه وفخرها","الاثنين زو القعدة 1440ه الموافق 2018/12/12 السادسه مساء",12331,1);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
               // Toast.makeText(getApplicationContext(), "Name: " + locat, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        mRequestQueue.add(mStringRequest);
    }


}






//    //taba217 work::::::
////=====================================================================================
//    public void jsonParse1() {
//        String url1 = "http://zad.epizy.com/getlec.php";
//        String url0 = "http://192.168.43.128/zad/getlec.php";
//        //String url="http://www.mocky.io/v2/597c41390f0000d002f4dbd1";
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("جاري التحديث...");
//        progressDialog.show();
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("emp");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject lectures = jsonArray.getJSONObject(i);
//
//                                locat = lectures.getString("name");
//                                v = lectures.getDouble("v");
//                                h = lectures.getDouble("h");
//                                sh_id = lectures.getString("sh_name");
//                                topic = lectures.getString("topic");
//                                locat = lectures.getString("locat");
//                                time = lectures.getString("time");
//                                status = lectures.getInt("id");
//                                helper.insertUser();
//                                Toast.makeText(MainActivity.this,"name" + locat ,Toast.LENGTH_LONG).show();
//                            }
//                            progressDialog.dismiss();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            progressDialog.dismiss();
//                        }
//                    }
//
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                progressDialog.dismiss();
//
//
//
//
//            }
//        });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }