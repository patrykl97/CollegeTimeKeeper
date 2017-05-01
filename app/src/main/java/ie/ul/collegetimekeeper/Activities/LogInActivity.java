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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import ie.ul.collegetimekeeper.Functions.LoginRequest;
import ie.ul.collegetimekeeper.Functions.ModuleRequest;
import ie.ul.collegetimekeeper.Objects.User;
import ie.ul.collegetimekeeper.R;

public class LogInActivity extends AppCompatActivity {

    String tag = "ie.ul.collegetimekeeper";
    private int studentID;
    private String password;
    private User user;
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
                                try {
                                    toast.cancel();
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if(success){
                                        toast = Toast.makeText(getApplicationContext(), "You have picked modules", Toast.LENGTH_SHORT);
                                        toast.show();
                                        Intent i = new Intent(LogInActivity.this, MenuActivity.class);
                                        i.putExtra("user", (Serializable) user);
                                        LogInActivity.this.startActivity(i);
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
