package ie.ul.collegetimekeeper.Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import ie.ul.collegetimekeeper.Functions.AllModulesRequest;
import ie.ul.collegetimekeeper.Functions.LoginRequest;
import ie.ul.collegetimekeeper.Functions.ModuleRequest;
import ie.ul.collegetimekeeper.Functions.UserModulesRequest;
import ie.ul.collegetimekeeper.Functions.WorkRequest;
import ie.ul.collegetimekeeper.Objects.User;
import ie.ul.collegetimekeeper.Objects.Work;
import ie.ul.collegetimekeeper.R;

public class LogInActivity extends AppCompatActivity {

    String tag = "ie.ul.collegetimekeeper";
    private int studentID;
    private String password;
    private User user;
    private ArrayList<Work> workList = new ArrayList<>();
    Toast toast;
    //private Student student;
    //private Staff staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        this.user = new User();
    }

    protected void onClickLogIn(View v) {
        Button button = (Button) v;
        EditText id = (EditText) findViewById(R.id.id);
        EditText pass = (EditText) findViewById(R.id.pass);
        user.setId(Integer.parseInt(id.getText().toString()));
        user.setPassword(pass.getText().toString());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(tag, "-------------------------------------------:" + response);
                try {

                    final JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success) {
                        Log.i(tag, "Login succesful");

                        user.setName(jsonResponse.getString("name"));
                        user.setSurname(jsonResponse.getString("surname"));
                        user.setEmail(jsonResponse.getString("email"));
                       // String collegeName = jsonResponse.getString("college_name");
                        user.setCollegeName(jsonResponse.getString("college_name"));
                      //  user.setCollegeName(collegeName);
                        user.setUserType(jsonResponse.getString("user_type"));
                        Log.i(tag, "Succesfully saved data to object type User");
                        toast = Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT);
                        toast.show();
                        Response.Listener<String> resListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Log.i(tag, "-----------------------:" + response);
                                try {
                                    toast.cancel();
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if(success){


                                        Response.Listener<String> userModuleListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.i(tag, "-----------------------------------: UserModuleListener tried");
                                                    final JSONObject jsonResponse = new JSONObject(response);
                                                    JSONArray array = jsonResponse.getJSONArray("module_code");
                                                    for(int i = 0; i < array.length(); i++){
                                                        user.addModule(array.getString(i));
                                                        Log.i(tag, "---------------: " + user.getModulesList().get(i).getModuleID());


                                                    Response.Listener<String> getWorkListener = new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            try {
                                                                JSONObject js = new JSONObject(response);

                                                                JSONArray array = js.getJSONArray("array");
                                                                for(int i = 0; i < array.length(); i++){
                                                                    // for(int j = 0; j < array.getJSONArray(i).length(); j++){
                                                                    String a = array.getJSONArray(i).getString(0);
                                                                    String b = array.getJSONArray(i).getString(1);
                                                                    int c = array.getJSONArray(i).getInt(2);

                                                                    workList.add(new Work(a, b, c));
                                                                    // }

                                                                    toast = Toast.makeText(getApplicationContext(), "You have picked modules", Toast.LENGTH_SHORT);
                                                                    toast.show();
                                                                    Intent in = new Intent(LogInActivity.this, MenuActivity.class);
                                                                    in.putExtra("user", (Serializable) user);
                                                                    in.putExtra("work", (Serializable) workList);
                                                                    LogInActivity.this.startActivity(in);

                                                                }


                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }

                                                        }
                                                    };

                                                    WorkRequest workRequest = new WorkRequest(user, getWorkListener);
                                                    RequestQueue queue = Volley.newRequestQueue(LogInActivity.this);
                                                    queue.add(workRequest);

                                                }


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        };
                                        UserModulesRequest userModulesRequest = new UserModulesRequest(user, userModuleListener);
                                        RequestQueue queue = Volley.newRequestQueue(LogInActivity.this);
                                        queue.add(userModulesRequest);

                                    }
                                    else {
                                        toast = Toast.makeText(getApplicationContext(), "You need to pick modules", Toast.LENGTH_SHORT);
                                        toast.show();
                                        Intent i = new Intent(LogInActivity.this, ModulesActivity.class);
                                        i.putExtra("user", (Serializable) user);
                                        LogInActivity.this.startActivity(i);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        ModuleRequest moduleRequest = new ModuleRequest(user, resListener);
                        RequestQueue queue = Volley.newRequestQueue(LogInActivity.this);
                        queue.add(moduleRequest);


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
                        builder.setMessage("Login failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        LoginRequest loginRequest = new LoginRequest(user, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LogInActivity.this);
        queue.add(loginRequest);
    }

}
