package ie.ul.collegetimekeeper.Functions;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Patryk on 24/04/2017.
 */

public class AllModules {

    private String stringOfModules;
    private String [] allModules;
    private Context context;
    String tag = "ie.ul.collegetimekeeper";

    public AllModules(Context context){
        this.context = context;
        Log.i(tag, "Constructor of allModules object");
        getResponse();
        Log.i(tag, "After get response");
    }

    public void getResponse(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i(tag, "Tried onResponse");
                    final JSONObject jsonResponse = new JSONObject(response);
                    Log.i(tag, "Declared json object");
                    boolean success = jsonResponse.getBoolean("success");
                    if(success) {
                        Log.i(tag, "Accesed the php file");
                        stringOfModules = jsonResponse.getString("module_code");
                    }
                    else{
                        Log.i(tag, "Not successful!");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AllModulesRequest allModulesRequest = new AllModulesRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        queue.add(allModulesRequest);
    }


    public String [] getAllModules(){
        return allModules;
    }

    public String toString(){
        return stringOfModules;
    }



}
