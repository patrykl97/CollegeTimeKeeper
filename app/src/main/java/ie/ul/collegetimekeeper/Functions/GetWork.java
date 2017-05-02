package ie.ul.collegetimekeeper.Functions;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import ie.ul.collegetimekeeper.Activities.LogInActivity;
import ie.ul.collegetimekeeper.Activities.MenuActivity;
import ie.ul.collegetimekeeper.Objects.User;
import ie.ul.collegetimekeeper.Objects.Work;

import static java.util.logging.Logger.global;

/**
 * Created by Patryk on 02/05/2017.
 */

public class GetWork {

    private User user;
    ArrayList<Work> workList= new ArrayList<>();
    private Context context;

    public GetWork(User user, Context context){
        this.user = user;
        this.context = context;
        getResponse();
    }

    public void getResponse(){
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
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        WorkRequest workRequest = new WorkRequest(user, getWorkListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(workRequest);

    }

    public ArrayList<Work> getWorkList(){
        Log.i("ie.ul.collegetimekeeper", "Size of workList ------------------:" +workList.size());
        return workList;
    }


}
