package ie.ul.collegetimekeeper.Functions;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import ie.ul.collegetimekeeper.Objects.User;

/**
 * Created by Patryk on 03/04/2017.
 */

public class LoginRequest extends StringRequest{

    String tag = "ie.ul.collegetimekeeper";
   // private static final String LOGIN_REQUEST_URL  = "https://collegetimekeeper.000webhostapp.com/login.php";
    private static final String LOGIN_REQUEST_URL  = "http://collegetimekeeper.x10host.com/login.php";
    private Map<String, String> params;

    public LoginRequest(User user, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", user.getId() + "");
        params.put("password", user.getPassword());
    }


    public Map<String, String> getParams(){
        return params;
    }

}
