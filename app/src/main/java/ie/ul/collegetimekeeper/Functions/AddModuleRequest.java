package ie.ul.collegetimekeeper.Functions;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import ie.ul.collegetimekeeper.Objects.User;

/**
 * Created by Patryk on 28/04/2017.
 */

public class AddModuleRequest extends StringRequest {

    String tag = "ie.ul.collegetimekeeper";
   // private static final String ADD_MODULE_REQUEST_URL  = "https://collegetimekeeper.000webhostapp.com/insertModule.php";
    private static final String ADD_MODULE_REQUEST_URL  = "http://collegetimekeeper.x10host.com/insertModule.php";
    private Map<String, String> params;

    public AddModuleRequest(User user, int index, Response.Listener<String> listener){
        super(Request.Method.POST, ADD_MODULE_REQUEST_URL, listener , null);
        params = new HashMap<>();
        Log.i(tag, user.getModulesList().get(index).getModuleID());
//        Log.i(tag, user.getModulesList().get(index).getModuleTitle());
        params.put("moduleCode", user.getModulesList().get(index).getModuleID());
        params.put("moduleTitle", "");
    }


    public Map<String, String> getParams(){
        return params;
    }

}
