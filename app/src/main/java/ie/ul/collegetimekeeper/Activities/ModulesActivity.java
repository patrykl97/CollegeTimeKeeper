package ie.ul.collegetimekeeper.Activities;

import android.content.Intent;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import ie.ul.collegetimekeeper.Functions.AddModuleRequest;
import ie.ul.collegetimekeeper.Functions.AddToModuleListRequest;
import ie.ul.collegetimekeeper.Functions.AllModulesRequest;
import ie.ul.collegetimekeeper.Functions.ModuleRequest;
import ie.ul.collegetimekeeper.Functions.SetLecturerRequest;
import ie.ul.collegetimekeeper.Objects.MyCustomAdapter;
import ie.ul.collegetimekeeper.Objects.User;
import ie.ul.collegetimekeeper.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static java.util.logging.Logger.global;

public class ModulesActivity extends AppCompatActivity {

    static User user;
    Toolbar toolbar;
    String moduleCode;
    String moduleName;
    String tag = "ie.ul.collegetimekeeper";
    String stringOfModules;
    private String [] allModules;
   // static String [] pickedModules;
    static ArrayList<String> pickedModules = new ArrayList<>();
    MyCustomAdapter adapterListView;
    static int numModulesSelected = 0;
    ListView listView;
    //static ArrayAdapter<String> adapterListView;
    Button saveModules;
    Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);
        //adapterListView = new ArrayAdapter<String>(this, R.layout.module_activity_listview, R.id.label, pickedModules);
        adapterListView = new MyCustomAdapter(pickedModules, this);
        listView = (ListView)findViewById(R.id.pickedmodulelist);
        listView.setAdapter(adapterListView);
        saveModules = (Button)findViewById(R.id.btnSaveModules);
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
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, allModules);
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
                                    Log.i(tag, "Response ran---------------- No: " + numModulesSelected );
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if(success){
                                        Log.i(tag, user.getUserType());
                                        if(user.getUserType().equals("Student")){
                                           user.getModulesList().get(numModulesSelected).setDbModuleID(jsonResponse.getInt("id"));


                                           Response.Listener<String> resListener = new Response.Listener<String>() {
                                               @Override
                                               public void onResponse(String response) {
                                                   try {
                                                       JSONObject json = new JSONObject(response);
                                                       boolean success = json.getBoolean("success");
                                                       if(success){
                                                            pickedModules.add(user.getModulesList().get(numModulesSelected).getModuleID());

                                                            Log.i(tag, Integer.toString(numModulesSelected));
                                                            //adapter.notifyDataSetChanged();
                                                           numModulesSelected++;
                                                           dialog.dismiss();
                                                           listView.invalidate();
                                                       }
                                                   } catch (JSONException e) {
                                                       e.printStackTrace();
                                                   }
                                               }
                                           };

                                            AddToModuleListRequest addToModuleListRequest = new AddToModuleListRequest(user, numModulesSelected, resListener);
                                            RequestQueue queue = Volley.newRequestQueue(ModulesActivity.this);
                                            queue.add(addToModuleListRequest);


                                        }
                                        else {
                                            //isLecturer
                                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        boolean success = jsonObject.getBoolean("success");
                                                        if(success)
                                                        {
                                                            user.getModulesList().get(numModulesSelected).setLecturerID(user.getId());
                                                            pickedModules.add(user.getModulesList().get(numModulesSelected).getModuleID());

                                                            Log.i(tag, Integer.toString(numModulesSelected));
                                                            //adapter.notifyDataSetChanged();
                                                            numModulesSelected++;
                                                            dialog.dismiss();
                                                            listView.invalidate();
                                                            numModulesSelected++;
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            };

                                            SetLecturerRequest setLecturerRequest = new SetLecturerRequest(user, numModulesSelected, responseListener);
                                            RequestQueue queue = Volley.newRequestQueue(ModulesActivity.this);
                                            queue.add(setLecturerRequest);
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
                    }
                }
            });

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

    public void saveModules(View v){
        if(numModulesSelected >= 1 ){
            Intent i = new Intent(ModulesActivity.this, MenuActivity.class);
            //Log.i(tag, user.getName());
            i.putExtra("user", (Serializable) user);
            startActivity(i);
        }
        else {
            Toast.makeText(getApplicationContext(), "You haven't picked enough modules", Toast.LENGTH_SHORT).show();
        }
    }

}


