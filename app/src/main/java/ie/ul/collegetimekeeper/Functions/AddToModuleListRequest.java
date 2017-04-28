package ie.ul.collegetimekeeper.Functions;

import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import ie.ul.collegetimekeeper.Objects.User;

/**
 * Created by Patryk on 28/04/2017.
 */

public class AddToModuleListRequest extends StringRequest{

    String tag = "ie.ul.collegetimekeeper";
    private static final String LOGIN_REQUEST_URL  = "https://collegetimekeeper.000webhostapp.com/insertModule.php";
    private Map<String, String> params;

    public AddToModuleListRequest(User user, int index, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener , null);
        params = new HashMap<>();
        params.put("userID", user.getId() + "");
        params.put("moduleID", user.getModulesList().get(index).getDbModuleID() + "");
    }


    public Map<String, String> getParams(){
        return params;
    }

}
