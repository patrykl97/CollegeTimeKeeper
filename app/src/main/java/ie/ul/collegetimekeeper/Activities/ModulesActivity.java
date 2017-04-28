package ie.ul.collegetimekeeper.Activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ie.ul.collegetimekeeper.Functions.AddModuleRequest;
import ie.ul.collegetimekeeper.Functions.AllModulesRequest;
import ie.ul.collegetimekeeper.Functions.ModuleRequest;
import ie.ul.collegetimekeeper.Objects.User;
import ie.ul.collegetimekeeper.R;

import static java.util.logging.Logger.global;

public class ModulesActivity extends AppCompatActivity {

    User user;
    Toolbar toolbar;
    String moduleCode;
    String moduleName;
    String tag = "ie.ul.collegetimekeeper";
    String stringOfModules;
    private String [] allModules;
    int numModulesSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);
        user = (User)getIntent().getSerializableExtra("user");
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getResponse();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean moduleAdded;
        int res_id = item.getItemId();
        if(res_id==R.id.action_add){
            //Toast.makeText(getApplicationContext(), "You selected add module", Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            //Toast.makeText(getApplicationContext(), "You selected add module", Toast.LENGTH_SHORT).show();
            View mView = getLayoutInflater().inflate(R.layout.dialog_add_module, null);
            final AutoCompleteTextView module_code = (AutoCompleteTextView) mView.findViewById(R.id.modulecode);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, allModules);
            module_code.setAdapter(adapter);
            final EditText module_name = (EditText) mView.findViewById(R.id.modulename);
            Button bAddModule = (Button)mView.findViewById(R.id.btnaddmodule);

            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            bAddModule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moduleCode = module_code.getText().toString();
                    moduleName = module_name.getText().toString();
                    if(!moduleCode.isEmpty() && !moduleName.isEmpty()){
                        user.addModule(moduleCode, moduleName);

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if(success){
                                        if(user.getUserType().equals("Student")){
                                          //  user.getModulesList().get(numModulesSelected).setDbModuleID(jsonResponse.getInt("id"));
                                           // Response.Listener<String> resListener
                                            Toast.makeText(getApplicationContext(), "You are a user", Toast.LENGTH_SHORT);
                                        }
                                        else{

                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        AddModuleRequest addModuleRequest = new AddModuleRequest(user, numModulesSelected, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ModulesActivity.this);
                        queue.add(addModuleRequest);

                        //Toast.makeText(getApplicationContext(), "You added a module", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

            if(user.getUserType().equals("Student")){
                Toast.makeText(this, "You are a student", Toast.LENGTH_LONG);
            }
            else
            {
                Toast.makeText(this, "You are a lecturer", Toast.LENGTH_LONG);
            }
        }
       return true;
    }



    public void getResponse(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject jsonResponse = new JSONObject(response);
                    JSONArray array = jsonResponse.getJSONArray("module_code");
                    allModules = new String[array.length()];
                    for(int i = 0; i < allModules.length; i++){
                        allModules[i] = array.getString(i);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AllModulesRequest allModulesRequest = new AllModulesRequest(responseListener);
        try {
            RequestQueue queue = Volley.newRequestQueue(ModulesActivity.this);
            queue.add(allModulesRequest);
        } catch (NullPointerException e){

        }
    }
}


