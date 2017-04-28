package ie.ul.collegetimekeeper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ie.ul.collegetimekeeper.Functions.RegisterRequest;
import ie.ul.collegetimekeeper.Objects.User;
import ie.ul.collegetimekeeper.R;

import static android.R.attr.duration;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private User user;
    EditText etId;
    EditText etName;
    EditText etSurname;
    EditText etEmail;
    EditText etPassword;
    EditText etPassword2;
    EditText etCollegeName;
    Spinner spinner;
    String tag = "ie.ul.collegetimekeeper";

    /*final EditText etId = (EditText) findViewById(R.id.id);
    final EditText etName = (EditText) findViewById(R.id.name);
    final EditText etSurname = (EditText) findViewById(R.id.surname);
    final EditText etEmail = (EditText) findViewById(R.id.email);
    final EditText etPassword = (EditText) findViewById(R.id.pass);
    final EditText etPassword2 = (EditText) findViewById(R.id.pass2);
    final EditText etCollegeName = (EditText) findViewById(R.id.collegeName);
    Spinner spinner = (Spinner) findViewById(R.id.user_types_spinner);*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.user = new User();
        this.etId = (EditText) findViewById(R.id.id);
        this.etName = (EditText) findViewById(R.id.name);
        this.etSurname = (EditText) findViewById(R.id.surname);
        this.etEmail = (EditText) findViewById(R.id.email);
        this.etPassword = (EditText) findViewById(R.id.pass);
        this.etPassword2 = (EditText) findViewById(R.id.pass2);
        this.etCollegeName = (EditText) findViewById(R.id.collegeName);
        this.spinner = (Spinner) findViewById(R.id.user_types_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        this.spinner.setAdapter(adapter);

    }

    protected void onClickRegister(View v){
        if(!etPassword.getText().toString().equals(etPassword2.getText().toString())){
            etPassword2.setError("Passwords do not match");
            etPassword2.requestFocus();
           // Log.i(tag, "passwords do not match");
        }
        else{
            user.setId(Integer.parseInt(etId.getText().toString()));
            user.setName(etName.getText().toString());
            user.setSurname(etSurname.getText().toString());
            user.setEmail(etEmail.getText().toString());
            user.setPassword(etPassword.getText().toString());
            user.setCollegeName(etCollegeName.getText().toString());
            user.setUserType(spinner.getSelectedItem().toString());

            Response.Listener<String> response = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterActivity.this, LogInActivity.class);
                            RegisterActivity.this.startActivity(i);
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("Register failed")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };

            RegisterRequest registerRequest = new RegisterRequest(user, response);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);


            queue.add(registerRequest);
        }

    }

    protected void onClickValidator(View v){
        EditText et = (EditText)v;
        if(et.getText().toString().length() == 0){
            et.setError(et.getText().toString() + " not entered!");
            et.requestFocus();
        }

    }

    protected void validate(){

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String userType = parent.getItemAtPosition(position).toString();
        user.setUserType(userType);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
